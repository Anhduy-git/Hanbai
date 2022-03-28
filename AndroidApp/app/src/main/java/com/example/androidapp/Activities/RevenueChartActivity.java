package com.example.androidapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.androidapp.R;
import com.example.androidapp.RevenueChartFragment.ChartDayFragment;
import com.example.androidapp.RevenueChartFragment.RevenueChartViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RevenueChartActivity extends AppCompatActivity {
    private Button btnBack;
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_chart);
        initUI();

        setUpViewPager();
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.chart_day:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.chart_month:
                    viewPager.setCurrentItem(1);
                    break;
            }

            return true;
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initUI(){
        btnBack = findViewById(R.id.back_btn);
        viewPager = findViewById(R.id.revenue_chart_viewpager);
        bottomNavigationView = findViewById(R.id.bottom_nav_revenue_chart);
    }

    private void setUpViewPager(){
        RevenueChartViewPagerAdapter revenueChartViewPagerAdapter = new RevenueChartViewPagerAdapter(
                getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPager.setAdapter(revenueChartViewPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.chart_day).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.chart_month).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}