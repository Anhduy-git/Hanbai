package com.example.androidapp.Data.HistoryOrder;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.androidapp.Data.AppDatabase;

import java.util.List;

public class HistoryOrderRepository {

    private HistoryOrderDao historyOrderDao;
    private LiveData<List<HistoryOrder>> allHistoryOrder;
    private LiveData<List<HistoryOrder>> allHistorySuccessOrder;
    private LiveData<List<HistoryOrder>> allHistoryCancelOrder;
    public HistoryOrderRepository(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        historyOrderDao = database.historyOrderDao();
        allHistoryOrder = historyOrderDao.getAllHistoryOrder();
        allHistorySuccessOrder = historyOrderDao.getAllHistorySuccessOrder();
        allHistoryCancelOrder = historyOrderDao.getAllHistoryCancelOrder();
    }
    public void insert(HistoryOrder historyOrder){
        new HistoryOrderRepository.InsertNoteAsyncTask(historyOrderDao).execute(historyOrder);
    }

    public void delete(HistoryOrder historyOrder){new DeleteNoteAsyncTask(historyOrderDao).execute(historyOrder); }

    public LiveData<List<HistoryOrder>> getAllHistoryOrder(){
        return allHistoryOrder;
    }
    public LiveData<List<HistoryOrder>> getAllHistorySuccessOrder(){

        return allHistorySuccessOrder;
    }
    public LiveData<List<HistoryOrder>> getAllHistoryCancelOrder(){
        return allHistoryCancelOrder;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<HistoryOrder, Void, Void> {
        private HistoryOrderDao historyOrderDao;
        private InsertNoteAsyncTask(HistoryOrderDao historyOrderDao){
            this.historyOrderDao = historyOrderDao;
        }
        @Override
        protected Void doInBackground(HistoryOrder... historyOrders){
            historyOrderDao.insert(historyOrders[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<HistoryOrder, Void, Void>{
        private HistoryOrderDao historyOrderDao;
        private DeleteNoteAsyncTask(HistoryOrderDao historyOrderDao){
            this.historyOrderDao = historyOrderDao;
        }
        @Override
        protected Void doInBackground(HistoryOrder... historyOrders){
            historyOrderDao.delete(historyOrders[0]);
            return null;
        }
    }
}

