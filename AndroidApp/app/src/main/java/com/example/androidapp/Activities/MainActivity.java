package com.example.androidapp.Activities;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.androidapp.Data.AppDatabase;
import com.example.androidapp.Data.ClientData.Client;
import com.example.androidapp.Data.DayRevenueData.DayRevenue;
import com.example.androidapp.Data.DayRevenueData.DayRevenueViewModel;
import com.example.androidapp.Data.HistoryOrder.HistoryOrder;
import com.example.androidapp.Data.HistoryOrder.HistoryOrderViewModel;
import com.example.androidapp.Data.MonthRevenueData.MonthRevenue;
import com.example.androidapp.Data.MonthRevenueData.MonthRevenueViewModel;
import com.example.androidapp.Data.OrderData.OrderTodayData.Order;
import com.example.androidapp.Data.OrderData.OrderTodayData.OrderViewModel;
import com.example.androidapp.Data.OrderData.OrderUnpaidData.UnpaidOrder;
import com.example.androidapp.Data.OrderData.OrderUnpaidData.UnpaidOrderViewModel;
import com.example.androidapp.Data.OrderData.OrderUpcomingData.UpcomingOrder;
import com.example.androidapp.Data.OrderData.OrderUpcomingData.UpcomingOrderViewModel;
import com.example.androidapp.Data.ProductType.ProductType;
import com.example.androidapp.Data.ProductType.ProductTypeViewModel;
import com.example.androidapp.Fragments.ViewPagerAdapter;
import com.example.androidapp.HelperClass.NotificationReceiver;
import com.example.androidapp.PickTypeActivity;
import com.example.androidapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.joda.time.DateTimeComparator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;
    private ProductTypeViewModel productTypeViewModel;

    private MonthRevenueViewModel monthRevenueViewModel;
    private DayRevenueViewModel dayRevenueViewModel;
    private DayRevenue dayRevenue;
    private MonthRevenue monthRevenue;
    private Date nowDate;
    private OrderViewModel orderViewModel;
    private UnpaidOrderViewModel unpaidOrderViewModel;
    private UpcomingOrderViewModel upcomingOrderViewModel;
    private HistoryOrderViewModel historyOrderViewModel;
    private SimpleDateFormat simpleDateFormat;
    private DateTimeComparator dateTimeComparator;
    private String tomorrow;
    private Calendar calendarTomorrow;
    public static final String CHANNEL_ID = "CHANNEL 1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup for order update
        //-------------------------------------------------
        //Setup View Model
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        unpaidOrderViewModel = new ViewModelProvider(this).get(UnpaidOrderViewModel.class);
        upcomingOrderViewModel = new ViewModelProvider(this).get(UpcomingOrderViewModel.class);
        historyOrderViewModel = new ViewModelProvider(this).get(HistoryOrderViewModel.class);


        //Date format
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //Only compare the date
        dateTimeComparator = DateTimeComparator.getDateOnlyInstance();

        //Get tomorrow's date
        calendarTomorrow = Calendar.getInstance();
        calendarTomorrow.add(Calendar.DAY_OF_YEAR, 1);
        //get string of tomorrow date
        tomorrow = simpleDateFormat.format(calendarTomorrow.getTime());

        //set notify
        updateNumTomorrowOrderAndNotify();

        //Update Upcoming Order to Order Today
        updateUpcomingOrder();

        //Update item from Today Order to Unpaid Order and History
        updateUnpaidOrderAndHistory();


        //Prepopulate entries
        prepopulateMonthEntries();
        prepopulateDayEntries();

        //view model
        productTypeViewModel = new ViewModelProvider(this).get(ProductTypeViewModel.class);
        //Check if has data in attribute ?
        List<ProductType> typeLst = productTypeViewModel.getAllProductType();

        if (typeLst == null || typeLst.size() == 0) {
            Intent intent = new Intent(MainActivity.this, PickTypeActivity.class);
            startActivity(intent);
        }


        viewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_order).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_client).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menu_product).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.menu_history).setChecked(true);
                        break;
                    case 4:
                        bottomNavigationView.getMenu().findItem(R.id.menu_chart).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_order:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.menu_client:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.menu_product:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.menu_history:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.menu_chart:
                        viewPager.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });


    }


    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    private void prepopulateDayEntries() {
        nowDate = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(nowDate);
        dayRevenueViewModel = new ViewModelProvider(this).get(DayRevenueViewModel.class);

        //If the list is empty then add a new entry
        List<DayRevenue> dayRevenueList = AppDatabase.getInstance(this).dayRevenueDao().getAllDayRevenues();
        if (dayRevenueList.isEmpty()) {
            dayRevenue = new DayRevenue(strDate, 0, 0);
            dayRevenueViewModel.insertDayRevenue(dayRevenue);
        } else {
            int size = dayRevenueList.size();
            //If the latest day in database is not equal to the current month then add
            if (!dayRevenueList.get(size - 1).getCurrentDate().equals(strDate)) {
                dayRevenue = new DayRevenue(strDate, 0, 0);
                dayRevenueViewModel.insertDayRevenue(dayRevenue);
            }
        }

        //TODO: add a mechanism to clear the database after a certain amount of entries
    }


    private void prepopulateMonthEntries(){
        nowDate = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat("MM/yyyy");
        String strDate = formatter.format(nowDate);
        monthRevenueViewModel = new ViewModelProvider(this).get(MonthRevenueViewModel.class);

        //If the list is empty then create a new entry
        List<MonthRevenue> monthRevenueList = AppDatabase.getInstance(this).monthRevenueDao().getAllMonthRevenues();
        if (monthRevenueList.isEmpty()) {
            monthRevenue = new MonthRevenue(strDate, 0, 0);
            monthRevenueViewModel.insertMonthRevenue(monthRevenue);
        } else {
            int size = monthRevenueList.size();
            //If the latest month in database is not equal to the current month then add
            if (!monthRevenueList.get(size - 1).getCurrentDate().equals(strDate)) {
                monthRevenue = new MonthRevenue(strDate, 0, 0);
                monthRevenueViewModel.insertMonthRevenue(monthRevenue);
            }
        }



        //TODO: add a mechanism to clear the database after a certain amount of entries
    }

    private void checkDate(){
        //implement later
    }
    private void updateUnpaidOrderAndHistory() {
        orderViewModel.getAllOrder().observe(MainActivity.this, new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                Date today = Calendar.getInstance().getTime();
                for (Order order : orders) {
                    try {
                        Date orderDate = simpleDateFormat.parse(order.getDate());
                        int ret = dateTimeComparator.compare(orderDate, today);
                        if (ret < 0) {
                            Client client = new Client(order.getClient().getClientName(), order.getClient().getClientNumber(),
                                    order.getClient().getClientAddress(), order.getClient().getClientEmail(), order.getClient().getClientBank(), order.getClient().getImageDir());
                            //if shipped
                            if (order.getShip()) {
                                //move to unpaid order
                                if (!order.getPaid()) {
                                    UnpaidOrder unpaidOrder = new UnpaidOrder(client, order.getDate(), order.getTime(), order.getPrice(), false, order.getOrderListProduct());
                                    unpaidOrderViewModel.insert(unpaidOrder);
                                }
                            } else {
                                //Move to history all cancel order
                                HistoryOrder historyOrder = new HistoryOrder(client, order.getDate(), order.getTime(), order.getPrice(), order.getShip(), order.getPaid(), order.getOrderListProduct());
                                historyOrderViewModel.insert(historyOrder);
                            }

                            //Remove all old order
                            orderViewModel.delete(order);
                        }
                    } catch (ParseException ex) {

                    }
                }
            }
        });
    }
    private void updateUpcomingOrder() {
        Date today = Calendar.getInstance().getTime();
        upcomingOrderViewModel.getAllUpcomingOrder().observe(MainActivity.this, new Observer<List<UpcomingOrder>>() {
            @Override
            public void onChanged(List<UpcomingOrder> upcomingOrders) {
                for (UpcomingOrder upcomingOrder : upcomingOrders) {
                    //Get the day of upcomingOrder
                    try {
                        Date upcomingOrderDate = simpleDateFormat.parse(upcomingOrder.getDate());
                        //Check if the upcomingOrderDay is today
                        int ret = dateTimeComparator.compare(upcomingOrderDate, today);
                        if (ret == 0) {
                            Client client = new Client(upcomingOrder.getClient().getClientName(), upcomingOrder.getClient().getClientNumber(),
                                    upcomingOrder.getClient().getClientAddress(), upcomingOrder.getClient().getClientEmail(),
                                    upcomingOrder.getClient().getClientBank(), upcomingOrder.getClient().getImageDir());
                            Order order = new Order(client, upcomingOrder.getDate(), upcomingOrder.getTime(),
                                    upcomingOrder.getPrice(), false, upcomingOrder.getPaid(), upcomingOrder.getOrderListProduct());
                            //add upcomingOrder to today's Order
                            orderViewModel.insert(order);
                            //remove that upcomingOrder
                            upcomingOrderViewModel.delete(upcomingOrder);
                        }

                    } catch (ParseException ex) {

                    }
                }
            }
        });
    }
    private void updateNumTomorrowOrderAndNotify() {
        upcomingOrderViewModel.getAllUpcomingOrder().observe(MainActivity.this, new Observer<List<UpcomingOrder>>() {
            @Override
            public void onChanged(List<UpcomingOrder> upcomingOrders) {
                //get the number of tomorrow order and notify
                int numTomorrowOrder = getNumTomorrowOrder();
                //Notify
                if (numTomorrowOrder > 0) {
                    setNotification(numTomorrowOrder);
                }
            }
        });

    }
    private int getNumTomorrowOrder() {

        List<UpcomingOrder> list = AppDatabase.getInstance(this).upcomingOrderDao().getNumOrderTomorrow(tomorrow);
        return list.size();
    }
    private void setNotification(int numTomorrowOrder) {
        //Notification
        createNotificationChannel();

        //set time daily for notification
        Calendar calendarNotification = Calendar.getInstance();
        calendarNotification.set(Calendar.HOUR_OF_DAY, 20);
        calendarNotification.set(Calendar.MINUTE, 0);
        calendarNotification.set(Calendar.SECOND, 0);

        //set notify only 1 time in day
        if (Calendar.getInstance().after(calendarNotification)) {
            calendarNotification.add(Calendar.DAY_OF_MONTH, 1);
        }
        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        intent.putExtra("numOrderTomorrow", numTomorrowOrder);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //set notify daily
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendarNotification.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "NotificationChannel";
            String description = "Channel for notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}