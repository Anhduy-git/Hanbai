package com.example.androidapp.Data.ProductTypeRevenue;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductTypeRevenueDao {
    @Insert
    void insert(ProductTypeRevenue productTypeRevenue);

    @Update
    void update(ProductTypeRevenue productTypeRevenue);

    @Delete
    void delete(ProductTypeRevenue productTypeRevenue);

    @Query("SELECT * FROM product_type_revenue_table")
    List<ProductTypeRevenue> getAllProductTypeRevenues();

    @Query("SELECT * FROM product_type_revenue_table")
    LiveData<List<ProductTypeRevenue>> getAllProductRevenuesLive();
}
