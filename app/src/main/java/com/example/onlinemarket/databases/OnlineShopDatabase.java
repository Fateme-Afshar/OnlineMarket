package com.example.onlinemarket.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.onlinemarket.databases.dao.LocationDao;
import com.example.onlinemarket.databases.dao.ProductDao;
import com.example.onlinemarket.model.CustomerLocation;
import com.example.onlinemarket.model.Product;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Product.class, CustomerLocation.class}, version = OnlineShopSchema.VERSION)
@TypeConverters(value = {DatabaseConverter.class})
public abstract class OnlineShopDatabase extends RoomDatabase {
    private static final String DB_NAME = OnlineShopSchema.NAME;

    public abstract ProductDao getProductDao();
    public abstract LocationDao getLocationDao();

    public static ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(6);

    public static OnlineShopDatabase getInstance(Context context) {
        return Room.databaseBuilder
                (context.getApplicationContext(),
                        OnlineShopDatabase.class,
                        DB_NAME).
                build();
    }
}
