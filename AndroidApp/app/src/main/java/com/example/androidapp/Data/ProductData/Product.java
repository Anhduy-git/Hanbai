package com.example.androidapp.Data.ProductData;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "product_table")
public class Product {
    @PrimaryKey(autoGenerate = true)
    private int ProductID;
    private String name;
    private String imageDir;
    private String attribute1;
    private String attribute2;
    private String type;

    public Product(String name, String imageDir, String attribute1, String attribute2, String type) {
        this.name = name;
        this.imageDir = imageDir;
        this.attribute1 = attribute1;
        this.attribute2 = attribute2;
        this.type = type;

    }

//    protected Product(Parcel in) {
//        ProductID = in.readInt();
//        name = in.readString();
//        price = in.readInt();
//        quantity = in.readInt();
//        imageDir = in.readString();
//    }

//    public static final Creator<Product> CREATOR = new Creator<Product>() {
//        @Override
//        public Product createFromParcel(Parcel in) {
//            return new Product(in);
//        }
//
//        @Override
//        public Product[] newArray(int size) {
//            return new Product[size];
//        }
//    };

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageDir() {
        return imageDir;
    }

    public void setImageDir(String imageDir) {
        this.imageDir = imageDir;
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


//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeInt(ProductID);
//        dest.writeString(name);
//        dest.writeInt(price);
//        dest.writeInt(quantity);
//        dest.writeString(imageDir);
//    }
}