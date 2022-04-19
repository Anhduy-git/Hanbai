package com.example.androidapp.Data.HistoryOrder;

import androidx.room.Entity;

import com.example.androidapp.Data.ClientData.Client;
import com.example.androidapp.Data.OrderData.OrderTodayData.Order;
import com.example.androidapp.Data.ProductDetailData.ProductDetail;

import java.util.List;

@Entity(tableName = "history_table")
public class HistoryOrder extends Order {
    public HistoryOrder(Client client, String date, String time, int price, boolean ship, boolean paid, List<ProductDetail> orderListProduct){
        super(client, date,  time, price, ship, paid, orderListProduct);
    }
}
