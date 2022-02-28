package com.example.androidapp.Data.OrderData.OrderTodayData;//package com.example.androidapp.data.order.ordertoday;

import androidx.room.TypeConverter;

import com.example.androidapp.Data.ProductData.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class DataConverter implements Serializable {

    //Convert list of Product into string
    @TypeConverter
    public static String fromProductList(List<Product> mListProduct) {
        if (mListProduct == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Product>>() {
        }.getType();
        String json = gson.toJson(mListProduct, type);
        return json;
    }

    //Vice-versa of the method above
    @TypeConverter
    public static List<Product> toProductList(String ProductString) {
        if (ProductString == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Product>>(){
        }.getType();
        List<Product> orderProductList = gson.fromJson(ProductString, type);
        return orderProductList;
    }
}
