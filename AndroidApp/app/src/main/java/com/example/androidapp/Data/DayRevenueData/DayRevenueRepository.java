package com.example.androidapp.Data.DayRevenueData;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.androidapp.Data.AppDatabase;
import com.example.androidapp.Data.DayRevenueData.DayRevenueDao;

import java.util.List;

public class DayRevenueRepository {
    private DayRevenueDao dayRevenueDao;
    private LiveData<List<DayRevenue>> allDayRevenuesLive;
    private List<DayRevenue> allDayRevenues;

    public DayRevenueRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        dayRevenueDao = database.dayRevenueDao();
        allDayRevenuesLive = dayRevenueDao.getAllDayRevenuesLive();
        allDayRevenues = dayRevenueDao.getAllDayRevenues();
    }

    public void insertDayRevenue(DayRevenue dayRevenue) {
        new InsertDayRevenueAsyncTask(dayRevenueDao).execute(dayRevenue);
    }

    public void updateDayRevenue(DayRevenue dayRevenue) {
        new UpdateDayRevenueAsyncTask(dayRevenueDao).execute(dayRevenue);
    }

    public void deleteDayRevenue(DayRevenue dayRevenue) {
        new DeleteDayRevenueAsyncTask(dayRevenueDao).execute(dayRevenue);
    }

    public LiveData<List<DayRevenue>> getAllDayRevenuesLive() { return allDayRevenuesLive; }
    public List<DayRevenue> getAllDayRevenues() { return allDayRevenues; }

    private static class InsertDayRevenueAsyncTask extends AsyncTask<DayRevenue, Void, Void> {
        private DayRevenueDao dayRevenueDao;

        private InsertDayRevenueAsyncTask(DayRevenueDao dayRevenueDao) {
            this.dayRevenueDao = dayRevenueDao;
        }

        @Override
        protected Void doInBackground(DayRevenue... dayRevenues) {
            dayRevenueDao.insertDayRevenue(dayRevenues[0]);
            return null;
        }
    }

    private static class UpdateDayRevenueAsyncTask extends AsyncTask<DayRevenue, Void, Void> {
        private DayRevenueDao dayRevenueDao;

        private UpdateDayRevenueAsyncTask(DayRevenueDao dayRevenueDao) {
            this.dayRevenueDao = dayRevenueDao;
        }

        @Override
        protected Void doInBackground(DayRevenue... dayRevenues) {
            dayRevenueDao.updateDayRevenue(dayRevenues[0]);
            return null;
        }
    }

    private static class DeleteDayRevenueAsyncTask extends AsyncTask<DayRevenue, Void, Void> {
        private DayRevenueDao dayRevenueDao;

        private DeleteDayRevenueAsyncTask(DayRevenueDao dayRevenueDao) {
            this.dayRevenueDao = dayRevenueDao;
        }

        @Override
        protected Void doInBackground(DayRevenue... dayRevenues) {
            dayRevenueDao.deleteDayRevenue(dayRevenues[0]);
            return null;
        }
    }
}
