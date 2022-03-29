package com.example.androidapp.Data.OrderData.OrderTodayData;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.androidapp.Data.ClientData.Client;
import com.example.androidapp.Data.ProductData.Product;
import com.example.androidapp.Data.ProductDetailData.ProductDetail;

import java.util.List;

@Entity(tableName = "order_table")
public class Order {

    //Attribute
    @PrimaryKey(autoGenerate = true)
    private int id;

    @Embedded
    private Client client;

    private String date;
    private String time;
    private int price;
    private boolean ship;
    private boolean paid;

    @ColumnInfo(name = "Product_list")
    private List<ProductDetail> orderListProduct;

    //Constructor
    public Order(Client client, String date, String time,
                 int price, boolean ship, boolean paid, List<ProductDetail> orderListProduct) {
        this.client = client;
        this.date = date;
        this.time = time;
        this.price = price;
        this.ship = ship;
        this.paid = paid;
        this.orderListProduct = orderListProduct;
    }

    //Getter
    public int getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getPrice() {
        return price;
    }

    public boolean getShip() {
        return ship;
    }

    public boolean getPaid() {
        return paid;
    }

    public List<ProductDetail> getOrderListProduct() {
        return orderListProduct;
    }

    //Setter
    public void setId(int id) {
        this.id = id;
    }
    public void setShip(boolean ship) {
        this.ship = ship;
    }
    public void setPaid(boolean paid) {
        this.ship = paid;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
