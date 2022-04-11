package com.example.androidapp.Data.MonthRevenueData;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.androidapp.Data.AppDatabase;

import java.time.Month;
import java.util.List;

public class MonthRevenueRepository {
    private MonthRevenueDao monthRevenueDao;
    private LiveData<List<MonthRevenue>> allMonthRevenues;
    private List<MonthRevenue> allMonthRevenuesSync;

    public MonthRevenueRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        monthRevenueDao = database.monthRevenueDao();
        allMonthRevenues = monthRevenueDao.getAllMonthRevenues();
        allMonthRevenuesSync = monthRevenueDao.getAllMonthRevenuesSync();
    }

    public void insertMonthRevenue(MonthRevenue monthRevenue) {
        new InsertMonthRevenueAsyncTask(monthRevenueDao).execute(monthRevenue);
    }

    public void updateMonthRevenue(MonthRevenue monthRevenue) {
        new UpdateMonthRevenueAsyncTask(monthRevenueDao).execute(monthRevenue);
    }

    public void deleteMonthRevenue(MonthRevenue monthRevenue){
        new DeleteMonthRevenueAsyncTask(monthRevenueDao).execute(monthRevenue);
    }

    public LiveData<List<MonthRevenue>> getAllMonthRevenues() {
        return allMonthRevenues;
    }
    public List<MonthRevenue> getAllMonthRevenuesSync() { return allMonthRevenuesSync;}

    private static class InsertMonthRevenueAsyncTask extends AsyncTask<MonthRevenue, Void, Void> {
        private MonthRevenueDao monthRevenueDao;

        private InsertMonthRevenueAsyncTask(MonthRevenueDao monthRevenueDao) {
            this.monthRevenueDao = monthRevenueDao;
        }

        @Override
        protected Void doInBackground(MonthRevenue... monthRevenuesEntities) {
            monthRevenueDao.insertMonthRevenue(monthRevenuesEntities[0]);
            return null;
        }
    }

    private static class UpdateMonthRevenueAsyncTask extends AsyncTask<MonthRevenue, Void, Void> {
        private MonthRevenueDao monthRevenueDao;

        private UpdateMonthRevenueAsyncTask(MonthRevenueDao monthRevenueDao) {
            this.monthRevenueDao = monthRevenueDao;
        }

        @Override
        protected Void doInBackground(MonthRevenue... monthRevenuesEntities) {
            monthRevenueDao.updateMonthRevenue(monthRevenuesEntities[0]);
            return null;
        }
    }

    private static class DeleteMonthRevenueAsyncTask extends AsyncTask<MonthRevenue, Void, Void> {
        private MonthRevenueDao monthRevenueDao;

        private DeleteMonthRevenueAsyncTask(MonthRevenueDao monthRevenueDao) {
            this.monthRevenueDao= monthRevenueDao;
        }

        @Override
        protected Void doInBackground(MonthRevenue... monthRevenuesEntities) {
            monthRevenueDao.deleteMonthRevenue(monthRevenuesEntities[0]);
            return null;
        }
    }
}
