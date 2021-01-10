package com.example.onlinemarket;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.onlinemarket.repository.CustomerRepository;
import com.example.onlinemarket.repository.ProductPurchasedRepository;

public class OnlineShopApplication extends Application {
    public static final String CHANNEL_ID = "OnlineShopChannel";
    private static ProductPurchasedRepository sProductPurchasedRepository;
    private static CustomerRepository sCustomerRepository;
    @Override
    public void onCreate() {
        super.onCreate();
        sProductPurchasedRepository = ProductPurchasedRepository.getInstance(this);
        sCustomerRepository=CustomerRepository.getInstance();

        createNotificationChannel();
    }

    public static ProductPurchasedRepository getProductPurchasedRepository() {
        return sProductPurchasedRepository;
    }

    public static CustomerRepository getCustomerRepository() {
        return sCustomerRepository;
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=
                    new NotificationChannel(
                            CHANNEL_ID,
                            "Online shop",
                            NotificationManager.IMPORTANCE_HIGH);

            NotificationManager notificationManager=this.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
