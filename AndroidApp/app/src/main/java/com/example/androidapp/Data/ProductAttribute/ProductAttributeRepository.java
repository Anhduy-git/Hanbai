package com.example.androidapp.Data.ProductAttribute;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.androidapp.Data.AppDatabase;


import java.util.List;

public class ProductAttributeRepository {
    private ProductAttributeDao productAttributeDao;
    private List<ProductAttribute> allProductAttribute;
    public ProductAttributeRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        productAttributeDao = database.productAttributeDao();
        allProductAttribute = productAttributeDao.getAllProductAttribute();
    }
    public void insert(ProductAttribute productAttribute){
        new InsertNoteAsyncTask(productAttributeDao).execute(productAttribute);
    }
    public void update(ProductAttribute productAttribute){
        new UpdateNoteAsyncTask(productAttributeDao).execute(productAttribute);
    }
    public void delete(ProductAttribute productAttribute){new DeleteNoteAsyncTask(productAttributeDao).execute(productAttribute); }


    public List<ProductAttribute> getAllProductAttribute(){
        return allProductAttribute;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<ProductAttribute, Void, Void> {
        private ProductAttributeDao productAttributeDao;
        private InsertNoteAsyncTask(ProductAttributeDao productAttributeDao){
            this.productAttributeDao = productAttributeDao;
        }
        @Override
        protected Void doInBackground(ProductAttribute... productAttributes){
            productAttributeDao.insert(productAttributes[0]);
            return null;
        }
    }
    private static class UpdateNoteAsyncTask extends AsyncTask<ProductAttribute, Void, Void>{
        private ProductAttributeDao productAttributeDao;
        private UpdateNoteAsyncTask(ProductAttributeDao productAttributeDao){
            this.productAttributeDao = productAttributeDao;
        }
        @Override
        protected Void doInBackground(ProductAttribute... productAttributes){
            productAttributeDao.update(productAttributes[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<ProductAttribute, Void, Void> {
        private ProductAttributeDao productAttributeDao;
        private DeleteNoteAsyncTask(ProductAttributeDao productAttributeDao){
            this.productAttributeDao = productAttributeDao;
        }
        @Override
        protected Void doInBackground(ProductAttribute... productAttributes){
            productAttributeDao.delete(productAttributes[0]);
            return null;
        }
    }
}