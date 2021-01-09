package com.example.onlinemarket;

import android.app.Application;

import com.example.onlinemarket.repository.CustomerRepository;
import com.example.onlinemarket.repository.ProductPurchasedRepository;

public class OnlineShopApplication extends Application {
    private static ProductPurchasedRepository sProductPurchasedRepository;
    private static CustomerRepository sCustomerRepository;
    @Override
    public void onCreate() {
        super.onCreate();
        sProductPurchasedRepository = ProductPurchasedRepository.getInstance(this);
        sCustomerRepository=CustomerRepository.getInstance();
    }

    public static ProductPurchasedRepository getProductPurchasedRepository() {
        return sProductPurchasedRepository;
    }

    public static CustomerRepository getCustomerRepository() {
        return sCustomerRepository;
    }
}
