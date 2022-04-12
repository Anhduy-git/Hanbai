package com.example.androidapp.Activities;

import android.content.Intent;
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
import com.example.androidapp.Data.DayRevenueData.DayRevenue;
import com.example.androidapp.Data.DayRevenueData.DayRevenueViewModel;
import com.example.androidapp.Data.MonthRevenueData.MonthRevenue;
import com.example.androidapp.Data.MonthRevenueData.MonthRevenueViewModel;
import com.example.androidapp.Data.ProductType.ProductType;
import com.example.androidapp.Data.ProductType.ProductTypeViewModel;
import com.example.androidapp.Fragments.ViewPagerAdapter;
import com.example.androidapp.PickTypeActivity;
import com.example.androidapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}