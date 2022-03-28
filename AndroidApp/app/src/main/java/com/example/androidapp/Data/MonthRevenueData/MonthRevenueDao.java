package com.example.androidapp.Data.MonthRevenueData;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MonthRevenueDao {
    @Insert
    void insertMonthRevenue(MonthRevenue monthRevenue);

    @Update
    void updateMonthRevenue(MonthRevenue monthRevenue);

    @Delete
    void deleteMonthRevenue(MonthRevenue monthRevenue);

    @Query("SELECT * FROM month_revenue_table ORDER BY currentDate")
    LiveData<List<MonthRevenue>> getAllMonthRevenues();
}
