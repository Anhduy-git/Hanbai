package com.example.androidapp.Data.ProductDetailData;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.androidapp.Data.AppDatabase;
import com.example.androidapp.Data.ProductDetailData.ProductDetail;
import com.example.androidapp.Data.ProductDetailData.ProductDetailDao;


import java.util.List;

public class ProductDetailRepository {
    private ProductDetailDao productDetailDao;
    private List<ProductDetail> allProductDetail;

    public ProductDetailRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        productDetailDao = database.productDetailDao();
//        allProductDetail = productDetailDao.getAllProductDetail();
    }
    public void insert(ProductDetail productDetail){
        new ProductDetailRepository.InsertNoteAsyncTask(productDetailDao).execute(productDetail);
    }
    public void update(ProductDetail productDetail){
        new ProductDetailRepository.UpdateNoteAsyncTask(productDetailDao).execute(productDetail);
    }
    public void delete(ProductDetail productDetail){new ProductDetailRepository.DeleteNoteAsyncTask(productDetailDao).execute(productDetail); }


//    public List<ProductDetail> getAllProductDetail(){
//        return allProductDetail;
//    }

    private static class InsertNoteAsyncTask extends AsyncTask<ProductDetail, Void, Void> {
        private ProductDetailDao productDetailDao;
        private InsertNoteAsyncTask(ProductDetailDao productDetailDao){
            this.productDetailDao = productDetailDao;
        }
        @Override
        protected Void doInBackground(ProductDetail... productDetailEntities){
            productDetailDao.insert(productDetailEntities[0]);
            return null;
        }
    }
    private static class UpdateNoteAsyncTask extends AsyncTask<ProductDetail, Void, Void>{
        private ProductDetailDao productDetailDao;
        private UpdateNoteAsyncTask(ProductDetailDao productDetailDao){
            this.productDetailDao = productDetailDao;
        }
        @Override
        protected Void doInBackground(ProductDetail... productDetailEntities){
            productDetailDao.update(productDetailEntities[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<ProductDetail, Void, Void> {
        private ProductDetailDao productDetailDao;
        private DeleteNoteAsyncTask(ProductDetailDao productDetailDao){
            this.productDetailDao = productDetailDao;
        }
        @Override
        protected Void doInBackground(ProductDetail... productDetailEntities){
            productDetailDao.delete(productDetailEntities[0]);
            return null;
        }
    }
}