package com.example.androidapp.Data.OrderData.OrderUnpaidData;
//
import androidx.room.Entity;

import com.example.androidapp.Data.ClientData.Client;
import com.example.androidapp.Data.OrderData.OrderTodayData.Order;
import com.example.androidapp.Data.ProductDetailData.ProductDetail;

import java.util.List;

@Entity(tableName = "unpaid_order_table")
public class UnpaidOrder extends Order {
    public UnpaidOrder(Client client, String date, String time, int price, boolean paid, List<ProductDetail> orderListProduct){
        super(client,  date,  time, price, true, paid, orderListProduct);
    }
}
//
