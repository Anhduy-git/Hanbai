package com.example.androidapp.Data.MonthRevenueData;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "month_revenue_table")
public class MonthRevenue {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "month_id")
    int id;

    @NonNull
    private String currentDate; //"MM/yyyy"

    private double monthRevenue;

    @ColumnInfo(name="number_order")
    private int numberOfOrders;

    public MonthRevenue(String currentDate, double monthRevenue, int numberOfOrders){
        this.currentDate = currentDate;
        this.monthRevenue = monthRevenue;
        this.numberOfOrders = numberOfOrders;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public double getMonthRevenue() {
        return monthRevenue;
    }

    public void setMonthRevenue(double monthRevenue) {
        this.monthRevenue = monthRevenue;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }
}
