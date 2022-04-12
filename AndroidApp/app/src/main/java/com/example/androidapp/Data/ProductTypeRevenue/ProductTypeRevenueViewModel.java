package com.example.androidapp.Data.ProductTypeRevenue;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductTypeRevenueViewModel extends AndroidViewModel {
    private ProductTypeRevenueRepository repository;
    private List<ProductTypeRevenue> allProductTypeRevenues;
    private LiveData<List<ProductTypeRevenue>> allProductTypeRevenuesLive;

    public ProductTypeRevenueViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductTypeRevenueRepository(application);
        allProductTypeRevenues = repository.getAllProductTypeRevenue();
        allProductTypeRevenuesLive = repository.getAllProductTypeRevenueLive();
    }

    public void insert(ProductTypeRevenue productTypeRevenue) { repository.insert(productTypeRevenue);}
    public void update(ProductTypeRevenue productTypeRevenue) { repository.update(productTypeRevenue);}
    public void delete(ProductTypeRevenue productTypeRevenue) { repository.delete(productTypeRevenue);}
    public List<ProductTypeRevenue> getAllProductTypeRevenues() { return allProductTypeRevenues;}
    public LiveData<List<ProductTypeRevenue>> getAllProductTypeRevenuesLive() { return allProductTypeRevenuesLive;}
}
