package com.example.onlinemarket.databases;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.onlinemarket.model.Product;

import java.util.List;

public class ProductRepository implements IRepository<Product> {
    private static ProductRepository sInstance;

    private final ProductDao mDao;

    private ProductRepository(Context context) {
        OnlineShopDatabase onlineShopDatabase =
                OnlineShopDatabase.getInstance(context.getApplicationContext());

        mDao=onlineShopDatabase.getProductDao();
    }

    public static ProductRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new ProductRepository(context.getApplicationContext());
        return sInstance;
    }

    @Override
    public LiveData<Product> get(int id) {
        return mDao.get(id);
    }

    @Override
    public LiveData<List<Product>> getList() {
        return mDao.getList();
    }

    @Override
    public void insert(Product product) {
        OnlineShopDatabase.databaseWriteExecutor.execute(()->mDao.insert(product));
    }

    @Override
    public void delete(Product product) {
        OnlineShopDatabase.databaseWriteExecutor.execute(()->mDao.delete(product));
    }

    @Override
    public void update(Product product) {
        OnlineShopDatabase.databaseWriteExecutor.execute(()->mDao.update(product));
    }
}
