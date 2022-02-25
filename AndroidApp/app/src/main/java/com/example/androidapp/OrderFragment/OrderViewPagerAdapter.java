package com.example.androidapp.OrderFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class OrderViewPagerAdapter extends FragmentStatePagerAdapter {
    public OrderViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new OrderTodayFragment();
            case 1:
                return new UpcomingOrderFragment();
            case 2:
                return new UnpaidOrderFragment();
            default:
                return new OrderTodayFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
