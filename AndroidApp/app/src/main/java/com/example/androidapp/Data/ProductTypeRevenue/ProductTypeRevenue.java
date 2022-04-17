package com.example.androidapp.Data.ProductTypeRevenue;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "product_type_revenue_table",
        primaryKeys = {"name", "currentDate"})
public class ProductTypeRevenue {
    @NonNull
    private String name;
    @NonNull
    private String currentDate; //MM/yyyy
    private int numberOfOrders;

    public ProductTypeRevenue(String name, String currentDate, int numberOfOrders) {
        this.name = name;
        this.currentDate = currentDate;
        this.numberOfOrders = numberOfOrders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }
}
