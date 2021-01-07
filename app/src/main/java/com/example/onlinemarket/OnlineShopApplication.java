package com.example.onlinemarket;

import android.app.Application;

import com.example.onlinemarket.databases.ProductRepository;

public class OnlineShopApplication extends Application {
    private static ProductRepository mRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        mRepository= ProductRepository.getInstance(this);
    }

    public static ProductRepository getRepository() {
        return mRepository;
    }
}
