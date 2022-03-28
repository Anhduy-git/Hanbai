package com.example.androidapp.Data.MonthRevenueData;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "month_revenue_table")
public class MonthRevenue {
    @PrimaryKey
    private Date currentDate;
    private double monthRevenue;
    private int numberOfOrders;

    public MonthRevenue(Date currentDate, double monthRevenue, int numberOfOrders){
        this.currentDate = currentDate;
        this.monthRevenue = monthRevenue;
        this.numberOfOrders = numberOfOrders;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
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
