package com.example.androidapp.Data.ProductDetailData;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.androidapp.Data.ProductData.Product;
import com.example.androidapp.Data.ProductData.ProductRepository;

import java.util.List;

public class ProductDetailViewModel extends AndroidViewModel {
    private ProductDetailRepository repository;
    private LiveData<List<ProductDetail>> allProduct;

    public ProductDetailViewModel(@NonNull Application application){
        super(application);
        repository = new ProductDetailRepository(application);
//        allProduct = repository.getAllProductDetail();
    }
    public void insert(ProductDetail productDetail){
        repository.insert(productDetail);
    }
    public void update(ProductDetail productDetail){
        repository.update(productDetail);
    }
    public void delete(ProductDetail productDetail){
        repository.delete(productDetail);
    }
//    public LiveData<List<ProductDetail>> getAllProduct(){
//        return allProduct;
//    }
}