package com.example.onlinemarket;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.repository.CustomerLocationRepository;
import com.example.onlinemarket.repository.ProductPurchasedRepository;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;
import com.example.onlinemarket.utils.UiUtils;
import com.squareup.leakcanary.LeakCanary;

public class OnlineShopApplication extends Application{
    public static final String CHANNEL_ID = "OnlineShopChannel";
    private static ProductPurchasedRepository sProductPurchasedRepository;
    private static CustomerLocationRepository sLocationRepository;

    private static UiUtils sUiUtils;

    private static Customer sCustomer;

    @Override
    public void onCreate() {
        super.onCreate();
        sProductPurchasedRepository = ProductPurchasedRepository.getInstance(this);
        sLocationRepository=CustomerLocationRepository.getInstance(this);
        sCustomer= OnlineShopSharePref.getCustomer(this);
        sUiUtils=new UiUtils(this);

        LeakCanary.install(this);

        createNotificationChannel();
    }

    public static ProductPurchasedRepository getProductPurchasedRepository() {
        return sProductPurchasedRepository;
    }

    public static CustomerLocationRepository getLocationRepository() {
        return sLocationRepository;
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=
                    new NotificationChannel(
                            CHANNEL_ID,
                            "Online shop",
                            NotificationManager.IMPORTANCE_LOW);

            NotificationManager notificationManager=this.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public static Customer getCustomer() {
        return sCustomer;
    }

    public static UiUtils getUiUtils() {
        return sUiUtils;
    }
}
