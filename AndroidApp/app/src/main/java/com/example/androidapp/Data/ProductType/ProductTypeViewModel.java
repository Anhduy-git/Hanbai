package com.example.androidapp.Data.ProductType;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductTypeViewModel extends AndroidViewModel {
    private ProductTypeRepository repository;
    private List<ProductType> allProductType;
    private LiveData<List<ProductType>> allProductTypeLive;

    public ProductTypeViewModel(@NonNull Application application){
        super(application);
        repository = new ProductTypeRepository(application);
        allProductType = repository.getAllProductType();
        allProductTypeLive = repository.getAllProductTypeLive();
    }
    public void insert(ProductType productType){
        repository.insert(productType);
    }
    public void update(ProductType productType){
        repository.update(productType);
    }
    public void delete(ProductType productType){
        repository.delete(productType);
    }
    public List<ProductType> getAllProductType(){
        return allProductType;
    }
    public LiveData<List<ProductType>> getAllProductTypeLive(){
        return allProductTypeLive;
    }
}