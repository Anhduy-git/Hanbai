package com.example.androidapp.Data.DayRevenueData;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "day_revenue_table")
public class DayRevenue {
    @PrimaryKey
    @ColumnInfo(name = "day_id")
    @NonNull
    private String currentDate; //"MM/yyyy"
    private double dayRevenue;
    private int numberOfOrders;

    public DayRevenue(String currentDate, double dayRevenue, int numberOfOrders) {
        this.currentDate = currentDate;
        this.dayRevenue = dayRevenue;
        this.numberOfOrders = numberOfOrders;
    }

    @NonNull
    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(@NonNull String currentDate) {
        this.currentDate = currentDate;
    }

    public double getDayRevenue() {
        return dayRevenue;
    }

    public void setDayRevenue(double dayRevenue) {
        this.dayRevenue = dayRevenue;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }
}
