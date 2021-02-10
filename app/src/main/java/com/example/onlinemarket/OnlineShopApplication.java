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

public class OnlineShopApplication extends Application{
    public static final String CHANNEL_ID = "OnlineShopChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
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
}
