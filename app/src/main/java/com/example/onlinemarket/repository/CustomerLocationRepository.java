package com.example.onlinemarket.repository;

import androidx.lifecycle.LiveData;

import com.example.onlinemarket.databases.IRepository;
import com.example.onlinemarket.databases.OnlineShopDatabase;
import com.example.onlinemarket.databases.dao.LocationDao;
import com.example.onlinemarket.di.ContextModule;
import com.example.onlinemarket.model.CustomerLocation;

import java.util.List;

import javax.inject.Inject;

public class CustomerLocationRepository implements IRepository<CustomerLocation> {
    private LocationDao mDao;

    @Inject
    public CustomerLocationRepository(ContextModule contextModule) {
        OnlineShopDatabase database=
                OnlineShopDatabase.getInstance(contextModule.provideContext().getApplicationContext());

        mDao=database.getLocationDao();
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
