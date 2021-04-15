package com.example.onlinemarket;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.onlinemarket.di.ApplicationGraph;
import com.example.onlinemarket.di.ContextModule;
import com.example.onlinemarket.di.DaggerApplicationGraph;


public class OnlineShopApplication extends Application{
    public static final String CHANNEL_ID = "OnlineShopChannel";
    private  ApplicationGraph mApplicationGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationGraph= DaggerApplicationGraph.builder().contextModule(new ContextModule(this)).build();
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

    public  ApplicationGraph getApplicationGraph() {
        return mApplicationGraph;
    }
}
