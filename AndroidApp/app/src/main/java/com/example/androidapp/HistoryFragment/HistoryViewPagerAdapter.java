package com.example.androidapp.HistoryFragment;

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
                return new HistoryAllFragment();
            case 1:
                return new HistoryCompletedFragment();
            case 2:
                return new HistoryCanceledFragment();
            default:
                return new HistoryAllFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
