package com.example.onlinemarket.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.onlinemarket.databases.IRepository;
import com.example.onlinemarket.databases.OnlineShopDatabase;
import com.example.onlinemarket.databases.ProductDao;
import com.example.onlinemarket.model.Product;

import java.util.List;

public class ProductPurchasedRepository implements IRepository<Product> {
    private static ProductPurchasedRepository sInstance;

    private final ProductDao mDao;

    private ProductPurchasedRepository(Context context) {
        OnlineShopDatabase onlineShopDatabase =
                OnlineShopDatabase.getInstance(context.getApplicationContext());

        mDao=onlineShopDatabase.getProductDao();
    }

    public static ProductPurchasedRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new ProductPurchasedRepository(context.getApplicationContext());
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
