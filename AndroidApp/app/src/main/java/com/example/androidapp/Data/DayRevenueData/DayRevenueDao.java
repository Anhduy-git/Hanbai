package com.example.androidapp.Data.DayRevenueData;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidapp.Data.DayRevenueData.DayRevenue;

import java.util.List;

@Dao
public interface DayRevenueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDayRevenue(DayRevenue dayRevenue);

    @Update
    void updateDayRevenue(DayRevenue dayRevenue);
    
    @Delete
    void deleteDayRevenue(DayRevenue dayRevenue);

    @Query("SELECT * FROM day_revenue_table")
    LiveData<List<DayRevenue>> getAllDayRevenuesLive();

    @Query("SELECT * FROM day_revenue_table")
    List<DayRevenue> getAllDayRevenues();
}
