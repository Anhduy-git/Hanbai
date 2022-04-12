package com.example.androidapp.Data.ProductTypeRevenue;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.androidapp.Data.AppDatabase;

import java.util.List;

public class ProductTypeRevenueRepository {
    private ProductTypeRevenueDao productTypeRevenueDao;
    private List<ProductTypeRevenue> allProductTypeRevenue;
    private LiveData<List<ProductTypeRevenue>> allProductTypeRevenueLive;

    public ProductTypeRevenueRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        productTypeRevenueDao = database.productTypeRevenueDao();
        allProductTypeRevenue = productTypeRevenueDao.getAllProductTypeRevenues();
        allProductTypeRevenueLive = productTypeRevenueDao.getAllProductRevenuesLive();
    }

    public void insert(ProductTypeRevenue productTypeRevenue) {
        new InsertProductTypeRevAsyncTask(productTypeRevenueDao).execute(productTypeRevenue);
    }

    public void update(ProductTypeRevenue productTypeRevenue) {
        new UpdateProductTypeRevAsyncTask(productTypeRevenueDao).execute(productTypeRevenue);
    }

    public void delete(ProductTypeRevenue productTypeRevenue) {
        new DeleteProductTypeRevAsyncTask(productTypeRevenueDao).execute(productTypeRevenue);
    }

    public List<ProductTypeRevenue> getAllProductTypeRevenue() { return allProductTypeRevenue; }
    public LiveData<List<ProductTypeRevenue>> getAllProductTypeRevenueLive() { return allProductTypeRevenueLive; }

    private static class InsertProductTypeRevAsyncTask extends AsyncTask<ProductTypeRevenue, Void, Void> {
        private ProductTypeRevenueDao productTypeRevenueDao;
        private InsertProductTypeRevAsyncTask(ProductTypeRevenueDao productTypeRevenueDao) {
            this.productTypeRevenueDao = productTypeRevenueDao;
        }

        @Override
        protected Void doInBackground(ProductTypeRevenue... productTypeRevenues) {
            productTypeRevenueDao.insert(productTypeRevenues[0]);
            return null;
        }
    }

    private static class UpdateProductTypeRevAsyncTask extends AsyncTask<ProductTypeRevenue, Void, Void> {
        private ProductTypeRevenueDao productTypeRevenueDao;
        private UpdateProductTypeRevAsyncTask(ProductTypeRevenueDao productTypeRevenueDao) {
            this.productTypeRevenueDao = productTypeRevenueDao;
        }

        @Override
        protected Void doInBackground(ProductTypeRevenue... productTypeRevenues) {
            productTypeRevenueDao.update(productTypeRevenues[0]);
            return null;
        }
    }

    private static class DeleteProductTypeRevAsyncTask extends AsyncTask<ProductTypeRevenue, Void, Void> {
        private ProductTypeRevenueDao productTypeRevenueDao;
        private DeleteProductTypeRevAsyncTask(ProductTypeRevenueDao productTypeRevenueDao) {
            this.productTypeRevenueDao = productTypeRevenueDao;
        }

        @Override
        protected Void doInBackground(ProductTypeRevenue... productTypeRevenues) {
            productTypeRevenueDao.delete(productTypeRevenues[0]);
            return null;
        }
    }

}
