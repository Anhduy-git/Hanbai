package com.example.androidapp.Data.ProductType;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.androidapp.Data.AppDatabase;


import java.util.List;

public class ProductTypeRepository {
    private ProductTypeDao productTypeDao;
    private List<ProductType> allProductType;
    private LiveData<List<ProductType>> allProductTypeLive;
    public ProductTypeRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        productTypeDao = database.productTypeDao();
        allProductType = productTypeDao.getAllProductType();
        allProductTypeLive = productTypeDao.getAllProductTypeLive();
    }
    public void insert(ProductType productType){
        new InsertNoteAsyncTask(productTypeDao).execute(productType);
    }
    public void update(ProductType productType){
        new UpdateNoteAsyncTask(productTypeDao).execute(productType);
    }
    public void delete(ProductType productType){new DeleteNoteAsyncTask(productTypeDao).execute(productType); }


    public List<ProductType> getAllProductType(){
        return allProductType;
    }
    public LiveData<List<ProductType>> getAllProductTypeLive(){
        return allProductTypeLive;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<ProductType, Void, Void> {
        private ProductTypeDao productTypeDao;
        private InsertNoteAsyncTask(ProductTypeDao productTypeDao){
            this.productTypeDao = productTypeDao;
        }
        @Override
        protected Void doInBackground(ProductType... productTypes){
            productTypeDao.insert(productTypes[0]);
            return null;
        }
    }
    private static class UpdateNoteAsyncTask extends AsyncTask<ProductType, Void, Void>{
        private ProductTypeDao productTypeDao;
        private UpdateNoteAsyncTask(ProductTypeDao productTypeDao){
            this.productTypeDao = productTypeDao;
        }
        @Override
        protected Void doInBackground(ProductType... productTypes){
            productTypeDao.update(productTypes[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<ProductType, Void, Void> {
        private ProductTypeDao productTypeDao;
        private DeleteNoteAsyncTask(ProductTypeDao productTypeDao){
            this.productTypeDao = productTypeDao;
        }
        @Override
        protected Void doInBackground(ProductType... productTypes){
            productTypeDao.delete(productTypes[0]);
            return null;
        }
    }
}
