package com.example.androidapp.OrderFragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapp.Activities.InfoClientActivity;
import com.example.androidapp.Activities.NewOrderActivity;
import com.example.androidapp.Activities.OrderInfoTodayActivity;
import com.example.androidapp.Activities.SubProductActivity;
import com.example.androidapp.Data.AppDatabase;
import com.example.androidapp.Data.DayRevenueData.DayRevenue;
import com.example.androidapp.Data.DayRevenueData.DayRevenueViewModel;
import com.example.androidapp.Data.HistoryOrder.HistoryOrder;
import com.example.androidapp.Data.HistoryOrder.HistoryOrderViewModel;
import com.example.androidapp.Data.MonthRevenueData.MonthRevenue;
import com.example.androidapp.Data.MonthRevenueData.MonthRevenueDao;
import com.example.androidapp.Data.MonthRevenueData.MonthRevenueViewModel;
import com.example.androidapp.Data.OrderData.OrderTodayData.Order;
import com.example.androidapp.Data.OrderData.OrderTodayData.OrderAdapter;
import com.example.androidapp.Data.OrderData.OrderTodayData.OrderViewModel;
import com.example.androidapp.Data.ClientData.Client;
import com.example.androidapp.Data.ProductDetailData.ProductDetail;
import com.example.androidapp.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTimeComparator;

public class OrderTodayFragment extends Fragment {
    private Button btnAddNewOrder;
    private TextView numOrderToday;
    public static final int ADD_ORDER_REQUEST = 1;
    public static final int CONFIRM_ORDER_REQUEST = 2;
    //View model
    private OrderViewModel orderViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_today,
                container, false);

        RecyclerView rcvData = (RecyclerView) view.findViewById(R.id.order_recycler);
        numOrderToday = view.findViewById(R.id.order_num);
        rcvData.setLayoutManager(new LinearLayoutManager(view.getContext()));

        final OrderAdapter orderAdapter = new OrderAdapter();
        rcvData.setAdapter(orderAdapter);
        //Set up view model
        orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
        orderViewModel.getAllOrder().observe(getActivity(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                //Update Recycle View
                orderAdapter.submitList(orders);
//                //Display number of order today
                numOrderToday.setText(String.format("%d", orders.size()));
            }
        });


        //Send data to Order Info when click order
        orderAdapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Order order) {
                Intent intent = new Intent(getActivity(), OrderInfoTodayActivity.class);
                intent.putExtra(OrderInfoTodayActivity.EXTRA_ORDER_ID, order.getId());
                intent.putExtra(OrderInfoTodayActivity.EXTRA_ORDER_CLIENT, order.getClient());
                intent.putExtra(OrderInfoTodayActivity.EXTRA_ORDER_TIME, order.getTime());
                intent.putExtra(OrderInfoTodayActivity.EXTRA_ORDER_DATE, order.getDate());
                intent.putExtra(OrderInfoTodayActivity.EXTRA_CHECK_PAID, order.getPaid());
                intent.putExtra(OrderInfoTodayActivity.EXTRA_CHECK_SHIP, order.getShip());
                intent.putExtra(OrderInfoTodayActivity.EXTRA_ORDER_PRICE, order.getPrice());
                intent.putParcelableArrayListExtra(OrderInfoTodayActivity.EXTRA_ORDER_PRODUCT_LIST, (ArrayList<? extends Parcelable>) order.getOrderListProduct());
                startActivityForResult(intent, CONFIRM_ORDER_REQUEST);
            }
        });

        //Delete item
        orderAdapter.setOnItemClickDelListener(new OrderAdapter.OnItemClickDelListener() {
            @Override
            public void onItemClickDel(Order order) {
                confirmDelDialog(order);
            }
        });

//
//Button to launch New Today Order Activity
        btnAddNewOrder = (Button) view.findViewById(R.id.add_order_button);
        btnAddNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewOrderActivity.class);
                startActivityForResult(intent, ADD_ORDER_REQUEST);
            }
        });
        return view;
    }

    //Get data return from Intent to update order
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Add new order
        if (requestCode == ADD_ORDER_REQUEST && resultCode == RESULT_OK) {

            String time = data.getStringExtra(NewOrderActivity.EXTRA_ORDER_TIME);
            String date = data.getStringExtra(NewOrderActivity.EXTRA_ORDER_DATE);
            Client client = data.getParcelableExtra(NewOrderActivity.EXTRA_ORDER_CLIENT);
            List<ProductDetail> mOrderListProduct = data.getParcelableArrayListExtra(NewOrderActivity.EXTRA_ORDER_PRODUCT_LIST);
            int price = calculateOrderPrice(mOrderListProduct);
            Order order = new Order(client, date, time, price, false, false, mOrderListProduct);
            orderViewModel.insert(order);


//
            //Only compare the date
//            DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();
//            Calendar calendar = Calendar.getInstance();
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            //Get the current date
//            Date today = calendar.getTime();

//            try {
//                Date orderDate = simpleDateFormat.parse(date);
//                int ret = dateTimeComparator.compare(orderDate, today);
//                if (ret > 0) {
//                    //Move order to upcoming order if order's day > today.
//                    UpcomingOrder upcomingOrder = new UpcomingOrder(client, date, time, price, false, mOrderListDish);
//                    upcomingOrderViewModel.insert(upcomingOrder);
//                } else { //else add to new order's today
//                    Order order = new Order(client, date, time, price, false, false, mOrderListDish);
//                    orderViewModel.insert(order);
//                }
////
//            } catch (ParseException ex) {
//                Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
//            }
//
//
//            //Update order (paid, ship)
        } else if (requestCode == CONFIRM_ORDER_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(OrderInfoTodayActivity.EXTRA_ORDER_ID, -1);

            String time = data.getStringExtra(OrderInfoTodayActivity.EXTRA_ORDER_TIME);
            String date = data.getStringExtra(OrderInfoTodayActivity.EXTRA_ORDER_DATE);
            Client client = data.getParcelableExtra(OrderInfoTodayActivity.EXTRA_ORDER_CLIENT);
            boolean paid = data.getBooleanExtra(OrderInfoTodayActivity.EXTRA_CHECK_PAID, false);
            boolean ship = data.getBooleanExtra(OrderInfoTodayActivity.EXTRA_CHECK_SHIP, false);
            boolean confirmShip = data.getBooleanExtra(OrderInfoTodayActivity.EXTRA_CHECK_CONFIRM_SHIP, false);

            if (id == -1){
                Toast.makeText(getActivity(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                return;
            }
            List<ProductDetail> mOrderListProduct = data.getParcelableArrayListExtra(NewOrderActivity.EXTRA_ORDER_PRODUCT_LIST);
            int price = calculateOrderPrice(mOrderListProduct);

            Order order = new Order(client, date, time, price, ship, paid, mOrderListProduct);
            order.setId(id);
            orderViewModel.update(order);
//            //if shipped, then move to history
            if(confirmShip) {
                HistoryOrderViewModel historyOrderViewModel;
                historyOrderViewModel = new ViewModelProvider(this).get(HistoryOrderViewModel.class);
                //Move to history all success order
                HistoryOrder historyOrder = new HistoryOrder(client, order.getDate(), order.getTime(), order.getPrice(), order.getShip(), order.getPaid(), order.getOrderListProduct());
                historyOrderViewModel.insert(historyOrder);

                for (ProductDetail productDetail : mOrderListProduct) {
                    AppDatabase.getInstance(getActivity()).
                            productDetailDao().updateQuantityProductDetail(productDetail.getName(), productDetail.getAttribute1(), productDetail.getAttribute2(), productDetail.getQuantity());
                }
            }

            //Update month number of orders
            //Get current date
            Date nowDate = Calendar.getInstance().getTime();
            DateFormat formatter = new SimpleDateFormat("MM/yyyy");
            String strCurrentMonth = formatter.format(nowDate);
            formatter = new SimpleDateFormat("dd/MM/yyyy");
            String strCurrentDay = formatter.format(nowDate);

            if (confirmShip) {
                List<MonthRevenue> monthRevenueList = AppDatabase.getInstance(getActivity()).monthRevenueDao().getAllMonthRevenues();
                List<DayRevenue> dayRevenueList = AppDatabase.getInstance(getActivity()).dayRevenueDao().getAllDayRevenues();

                //Update the entry in database
                updateDayRevenue(dayRevenueList, strCurrentDay, order);
                updateMonthRevenue(monthRevenueList, strCurrentMonth, order);
            }
        }
    }

    private void updateDayRevenue(List<DayRevenue> dayRevenueList, String strCurrentDay, Order order) {
        DayRevenueViewModel dayRevenueViewModel = new ViewModelProvider(getActivity())
                .get(DayRevenueViewModel.class);

        DayRevenue currentDay = getDay(dayRevenueList, strCurrentDay);
        int totalDayOrderNumber = currentDay.getNumberOfOrders();
        double totalDayRevenue = currentDay.getDayRevenue();

        DayRevenue newDayRevenue = new DayRevenue(strCurrentDay,
                order.getPrice() + totalDayRevenue,
                1 + totalDayOrderNumber);
        dayRevenueViewModel.updateDayRevenue(newDayRevenue);
    }

    private void updateMonthRevenue(List<MonthRevenue> monthRevenueList, String strCurrentMonth, Order order) {
        MonthRevenueViewModel monthRevenueViewModel = new ViewModelProvider(getActivity())
                .get(MonthRevenueViewModel.class);

        MonthRevenue currentMonth = getMonth(monthRevenueList, strCurrentMonth);
        int totalMonthOrderNumber = currentMonth.getNumberOfOrders();
        double totalMonthRevenue = currentMonth.getMonthRevenue();

        MonthRevenue newMonthRevenue = new MonthRevenue(strCurrentMonth,
                order.getPrice() + totalMonthRevenue,
                1 + totalMonthOrderNumber);
        monthRevenueViewModel.updateMonthRevenue(newMonthRevenue);
    }

    private MonthRevenue getMonth(List<MonthRevenue> monthRevenueList, String currentDate) {
        MonthRevenue temp = new MonthRevenue("", 0, 0);
        for (MonthRevenue monthRevenue : monthRevenueList) {
            if (monthRevenue.getCurrentDate().equals(currentDate)) {
                temp = monthRevenue;
            }
        }

        return temp;
    }

    private DayRevenue getDay(List<DayRevenue> dayRevenueList, String currentDate) {
        DayRevenue temp = new DayRevenue("", 0, 0);
        for (DayRevenue dayRevenue : dayRevenueList) {
            if (dayRevenue.getCurrentDate().equals(currentDate)) {
                temp = dayRevenue;
            }
        }
        return temp;
    }

    private void updateProductTypeRevenue() {

    }

    int calculateOrderPrice(List<ProductDetail> productDetailList) {
        int price = 0;
        for (ProductDetail productDetail : productDetailList) {
            price += productDetail.getPrice() * productDetail.getQuantity();
        }
        return price;
    }

    private void confirmDelDialog(Order order) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.alert_dialog_delete, (RelativeLayout) getView().findViewById(R.id.layout_dialog)
        );
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        //confirm paid btn
        view.findViewById(R.id.confirm_dialog_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
//                sound.start();
                orderViewModel.delete(order);
            }
        });
        //cancel btn
        view.findViewById(R.id.cancel_dialog_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }
}
