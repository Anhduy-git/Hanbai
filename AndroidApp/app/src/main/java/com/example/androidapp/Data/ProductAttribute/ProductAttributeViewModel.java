package com.example.androidapp.Data.ProductAttribute;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductAttributeViewModel extends AndroidViewModel {
    private ProductAttributeRepository repository;
    private List<ProductAttribute> allProductAttribute;

    public ProductAttributeViewModel(@NonNull Application application){
        super(application);
        repository = new ProductAttributeRepository(application);
        allProductAttribute = repository.getAllProductAttribute();
    }
    public void insert(ProductAttribute productAttribute){
        repository.insert(productAttribute);
    }
    public void update(ProductAttribute productAttribute){
        repository.update(productAttribute);
    }
    public void delete(ProductAttribute productAttribute){
        repository.delete(productAttribute);
    }
    public List<ProductAttribute> getAllProductAttribute(){
        return allProductAttribute;
    }
}