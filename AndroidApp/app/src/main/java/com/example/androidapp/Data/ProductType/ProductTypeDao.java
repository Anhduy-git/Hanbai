package com.example.androidapp.Data.ProductType;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductTypeDao {
    @Insert
    void insert(ProductType productType);

    @Update
    void update(ProductType productType);

    @Delete
    void delete(ProductType productType);

    @Query("SELECT * FROM productType_table")
    List<ProductType> getAllProductType();

    @Query("SELECT * FROM productType_table")
    LiveData<List<ProductType>> getAllProductTypeLive();

//    @Query("DELETE FROM order_table")
//    void deleteAllOrder();

}