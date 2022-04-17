package com.example.androidapp.Data.DayRevenueData;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DayRevenueViewModel extends AndroidViewModel {
    private DayRevenueRepository repository;
    private LiveData<List<DayRevenue>> allDayRevenuesLive;
    private List<DayRevenue> allDayRevenues;

    public DayRevenueViewModel(@NonNull Application application) {
        super(application);
        repository = new DayRevenueRepository(application);
        allDayRevenuesLive = repository.getAllDayRevenuesLive();
        allDayRevenues = repository.getAllDayRevenues();
    }

    public void insertDayRevenue(DayRevenue dayRevenue) {
        repository.insertDayRevenue(dayRevenue);
    }

    public void updateDayRevenue(DayRevenue dayRevenue) {
        repository.updateDayRevenue(dayRevenue);
    }

    public void deleteDayRevenue(DayRevenue dayRevenue) {
        repository.deleteDayRevenue(dayRevenue);
    }

    public LiveData<List<DayRevenue>> getAllDayRevenuesLive() { return allDayRevenuesLive; }
    public List<DayRevenue> getAllDayRevenues() { return allDayRevenues; }
}
