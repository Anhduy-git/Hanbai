package com.example.androidapp.Data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.androidapp.Data.ClientData.Client;
import com.example.androidapp.Data.ClientData.ClientDao;
import com.example.androidapp.Data.OrderData.OrderTodayData.DataConverter;
import com.example.androidapp.Data.OrderData.OrderTodayData.Order;
import com.example.androidapp.Data.OrderData.OrderUpcomingData.UpcomingOrder;
import com.example.androidapp.Data.OrderData.OrderTodayData.OrderDao;
import com.example.androidapp.Data.OrderData.OrderUpcomingData.UpcomingOrderDao;
import com.example.androidapp.Data.ProductType.ProductType;
import com.example.androidapp.Data.ProductType.ProductTypeDao;
import com.example.androidapp.Data.ProductData.Product;

//App database
//Add more entities (tables) to database by listing them inside {}
@Database(entities = {Client.class, Order.class, UpcomingOrder.class, ProductType.class, Product.class}, version = 5)
@TypeConverters({DataConverter.class})
public abstract class AppDatabase extends RoomDatabase{
    private static final String DATABASE_NAME = "database.db";
    private static AppDatabase instance;

    //Entities' DAO
    public abstract ClientDao clientDao();
    public abstract OrderDao orderDao();
    public abstract UpcomingOrderDao upcomingOrderDao();
    public abstract ProductTypeDao productTypeDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ClientDao clientDao;
//        private DishDao dishDao;
        private OrderDao orderDao;
//        private UnpaidOrderDao unpaidOrderDao;
        private UpcomingOrderDao upcomingOrderDao;
        private ProductTypeDao productTypeDao;
//        private HistoryOrderDao historyOrderDao;

        private PopulateDbAsyncTask(AppDatabase db) {
            clientDao = db.clientDao();
//            dishDao = db.dishDao();
            orderDao = db.orderDao();
//            unpaidOrderDao = db.unpaidOrderDao();
            upcomingOrderDao = db.upcomingOrderDao();
//            historyOrderDao = db.historyOrderDao();
            productTypeDao = db.productTypeDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
