package com.example.androidapp.Data.ProductData;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.androidapp.Data.AppDatabase;


import java.util.List;

public class ProductRepository {
    private ProductDao productDao;
    private LiveData<List<Product>> allProduct;

    public ProductRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        productDao = database.productDao();
        allProduct = productDao.getAllProduct();
    }
    public void insert(Product Product){
        new ProductRepository.InsertNoteAsyncTask(productDao).execute(Product);
    }
    public void update(Product Product){
        new ProductRepository.UpdateNoteAsyncTask(productDao).execute(Product);
    }
    public void delete(Product Product){new ProductRepository.DeleteNoteAsyncTask(productDao).execute(Product); }


    public LiveData<List<Product>> getAllProduct(){
        return allProduct;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;
        private InsertNoteAsyncTask(ProductDao productDao){
            this.productDao = productDao;
        }
        @Override
        protected Void doInBackground(Product... productEntities){
            productDao.insert(productEntities[0]);
            return null;
        }
    }
    private static class UpdateNoteAsyncTask extends AsyncTask<Product, Void, Void>{
        private ProductDao productDao;
        private UpdateNoteAsyncTask(ProductDao productDao){
            this.productDao = productDao;
        }
        @Override
        protected Void doInBackground(Product... productEntities){
            productDao.update(productEntities[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;
        private DeleteNoteAsyncTask(ProductDao productDao){
            this.productDao = productDao;
        }
        @Override
        protected Void doInBackground(Product... productEntities){
            productDao.delete(productEntities[0]);
            return null;
        }
    }
}