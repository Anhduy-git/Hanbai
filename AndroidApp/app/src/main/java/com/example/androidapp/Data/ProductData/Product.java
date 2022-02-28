package com.example.androidapp.Data.ProductData;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_table")
public class Product implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int ProductID;
    private String name;
    private int price;
    private int quantity;
    private String imageDir;

    public Product(String name, int price, String imageDir) {
        this.name = name;
        this.price = price;
        this.quantity = 0;
        this.imageDir = imageDir;
    }

    protected Product(Parcel in) {
        ProductID = in.readInt();
        name = in.readString();
        price = in.readInt();
        quantity = in.readInt();
        imageDir = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    //Setters and Getters
    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int ProductID) {
        this.ProductID = ProductID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setImageDir(String imageDir) {
        this.imageDir = imageDir;
    }

    public String getImageDir() {
        return imageDir;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(ProductID);
        dest.writeString(name);
        dest.writeInt(price);
        dest.writeInt(quantity);
        dest.writeString(imageDir);
    }
}