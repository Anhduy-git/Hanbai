package com.example.androidapp.Fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.androidapp.OrderFragment.OrderTodayFragment;
import com.example.androidapp.OrderFragment.UnpaidOrderFragment;
import com.example.androidapp.OrderFragment.UpcomingOrderFragment;

public class HistoryViewPagerAdapter extends FragmentStatePagerAdapter {
    public HistoryViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HisAllFragment();
            case 1:
                return new HisCompletedFragment();
            case 2:
                return new HisCanceledFragment();
            default:
                return new HisAllFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
