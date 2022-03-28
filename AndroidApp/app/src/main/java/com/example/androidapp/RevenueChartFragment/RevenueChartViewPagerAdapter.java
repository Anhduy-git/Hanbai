package com.example.androidapp.RevenueChartFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.androidapp.Fragments.ViewPagerAdapter;
import com.example.androidapp.OrderFragment.OrderTodayFragment;
import com.example.androidapp.OrderFragment.UnpaidOrderFragment;
import com.example.androidapp.OrderFragment.UpcomingOrderFragment;

public class RevenueChartViewPagerAdapter extends FragmentStatePagerAdapter {
    public RevenueChartViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ChartDayFragment();
            case 1:
                return new ChartMonthFragment();
            default:
                return new OrderTodayFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
