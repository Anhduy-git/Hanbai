package com.example.androidapp.Data.MonthRevenueData;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MonthRevenueViewModel extends AndroidViewModel {
    private MonthRevenueRepository repository;
    private LiveData<List<MonthRevenue>> allMonthRevenuesLive;
    private List<MonthRevenue> allMonthRevenues;

    public MonthRevenueViewModel(@NonNull Application application) {
        super(application);
        repository = new MonthRevenueRepository(application);
        allMonthRevenuesLive = repository.getAllMonthRevenuesLive();
        allMonthRevenues = repository.getAllMonthRevenues();
    }

    public void insertMonthRevenue(MonthRevenue monthRevenue) {
        repository.insertMonthRevenue(monthRevenue);
    }

    public void updateMonthRevenue(MonthRevenue monthRevenue) {
        repository.updateMonthRevenue(monthRevenue);
    }

    public void deleteMonthRevenue(MonthRevenue monthRevenue) {
        repository.deleteMonthRevenue(monthRevenue);
    }

    public LiveData<List<MonthRevenue>> getAllMonthRevenuesLive() {
        return allMonthRevenuesLive;
    }

    public List<MonthRevenue> getAllMonthRevenues() { return allMonthRevenues;}
}
