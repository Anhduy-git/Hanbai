package com.example.androidapp.Data.ProductData;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductRepository repository;
    private LiveData<List<Product>> allProduct;

    public ProductViewModel(@NonNull Application application){
        super(application);
        repository = new ProductRepository(application);
        allProduct = repository.getAllProduct();
    }
    public void insert(Product product){
        repository.insert(product);
    }
    public void update(Product product){
        repository.update(product);
    }
    public void delete(Product product){
        repository.delete(product);
    }
    public LiveData<List<Product>> getAllProduct(){
        return allProduct;
    }
}