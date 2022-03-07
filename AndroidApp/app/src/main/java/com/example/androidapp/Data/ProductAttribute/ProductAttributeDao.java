package com.example.androidapp.Data.ProductAttribute;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.androidapp.Data.OrderData.OrderTodayData.Order;

import java.util.List;

@Dao
public interface ProductAttributeDao {
    @Insert
    void insert(ProductAttribute productAttribute);

    @Update
    void update(ProductAttribute productAttribute);

    @Delete
    void delete(ProductAttribute productAttribute);

    @Query("SELECT * FROM productAttribute_table ORDER BY NAME ASC")
    List<ProductAttribute> getAllProductAttribute();

//    @Query("DELETE FROM order_table")
//    void deleteAllOrder();

}