package com.example.androidapp.Data.OrderData.OrderUpcomingData;
import com.example.androidapp.Data.OrderData.OrderTodayData.Order;
import com.example.androidapp.Data.ClientData.Client;
import com.example.androidapp.Data.ProductData.Product;
import androidx.room.Entity;

import java.util.List;

@Entity(tableName = "upcoming_order_table")
public class UpcomingOrder extends Order {
    public UpcomingOrder(Client client, String date, String time, int price, boolean paid, List<Product> orderListProduct){
        super(client, date,  time, price, false, paid, orderListProduct);
    }
}
