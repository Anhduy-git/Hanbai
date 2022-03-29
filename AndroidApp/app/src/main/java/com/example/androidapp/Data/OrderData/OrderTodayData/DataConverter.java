package com.example.androidapp.Data.OrderData.OrderTodayData;//package com.example.androidapp.data.order.ordertoday;

import androidx.room.TypeConverter;

import com.example.androidapp.Data.ProductData.Product;
import com.example.androidapp.Data.ProductDetailData.ProductDetail;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

public class DataConverter implements Serializable {

    //Convert list of Product into string
    @TypeConverter
    public static String fromProductList(List<ProductDetail> mListProduct) {
        if (mListProduct == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductDetail>>() {
        }.getType();
        String json = gson.toJson(mListProduct, type);
        return json;
    }

    //Vice-versa of the method above
    @TypeConverter
    public static List<ProductDetail> toProductList(String ProductString) {
        if (ProductString == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductDetail>>(){
        }.getType();
        List<ProductDetail> orderProductList = gson.fromJson(ProductString, type);
        return orderProductList;
    }
}
