package com.example.androidapp.Data.ProductType;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "productType_table")
public class ProductType {

    public ProductType(String name) {
        this.name = name;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
