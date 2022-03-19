package com.example.androidapp.Data.ProductDetailData;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_detail_table")
public class ProductDetail {
    @PrimaryKey(autoGenerate = true)
    private int ProductID;
    private String name;
    private String attribute1;
    private String attribute2;
    private String imgDir;
    int price;
    int quantity;

    public ProductDetail(String name, String imgDir, String attribute1, String attribute2, int price, int quantity) {
        this.name = name;
        this.imgDir = imgDir;
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        this.price = price;
        this.quantity = quantity;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public String getName() {
        return name;
    }

    public String getImgDir() {
        return imgDir;
    }

    public void setImgDir(String imgDir) {
        this.imgDir = imgDir;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
