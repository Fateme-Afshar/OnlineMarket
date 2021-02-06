package com.example.onlinemarket.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.onlinemarket.databases.IRepository;
import com.example.onlinemarket.databases.OnlineShopDatabase;
import com.example.onlinemarket.databases.dao.LocationDao;
import com.example.onlinemarket.model.CustomerLocation;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;

import java.util.List;

public class CustomerLocationRepository implements IRepository<CustomerLocation> {
    private static CustomerLocationRepository sInstance;
    private LocationDao mDao;

    private CustomerLocationRepository(Context context) {
        OnlineShopDatabase database=
                OnlineShopDatabase.getInstance(context.getApplicationContext());

        mDao=database.getLocationDao();
    }

    public static CustomerLocationRepository getInstance(Context context) {
        if (sInstance==null)
            sInstance=new CustomerLocationRepository(context);
        return sInstance;
    }

    @Override
    public LiveData<CustomerLocation> get(int id) {
        return mDao.get(id);
    }

    @Override
    public LiveData<List<CustomerLocation>> getList() {
        return mDao.getList();
    }

    @Override
    public void insert(CustomerLocation customerLocation) {
        OnlineShopDatabase.databaseWriteExecutor.execute(()->mDao.insert(customerLocation));
    }

    @Override
    public void delete(CustomerLocation customerLocation) {
        OnlineShopDatabase.databaseWriteExecutor.execute(()->mDao.delete(customerLocation));
    }

    public void deleteAll(){
        OnlineShopDatabase.databaseWriteExecutor.execute(()->mDao.deleteAll());
    }

    @Override
    public void update(CustomerLocation customerLocation) {
        OnlineShopDatabase.databaseWriteExecutor.execute(()->mDao.update(customerLocation));
    }
}
