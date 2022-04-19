package com.example.androidapp.Data.ProductDetailData;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;




import java.util.List;

@Dao
public interface ProductDetailDao {
    @Insert
    void insert(ProductDetail productDetail);

    @Update
    void update(ProductDetail productDetail);

    @Delete
    void delete(ProductDetail productDetail);

    @Query("SELECT * FROM product_detail_table WHERE name =:name_s")
    List<ProductDetail> getAllProductDetail(String name_s);

    @Query("UPDATE product_detail_table SET quantity =quantity - :newQuantity WHERE ProductID =:id")
    void updateQuantityProductDetail(int id, int newQuantity);

    @Query("DELETE FROM product_detail_table")
    void deleteAllProductDetail();

    @Query("DELETE FROM product_detail_table WHERE name =:name_s")
    void deleteAllProductDetailWithName(String name_s);

}