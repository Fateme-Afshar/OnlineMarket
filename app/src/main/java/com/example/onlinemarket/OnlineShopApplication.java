package com.example.onlinemarket;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.repository.CustomerLocationRepository;
import com.example.onlinemarket.repository.CustomerRepository;
import com.example.onlinemarket.repository.ProductPurchasedRepository;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;

public class OnlineShopApplication extends Application{
    public static final String CHANNEL_ID = "OnlineShopChannel";
    private static ProductPurchasedRepository sProductPurchasedRepository;
    private static CustomerRepository sCustomerRepository;
    private static CustomerLocationRepository sLocationRepository;

    private static Customer sCustomer;

    @Override
    public void onCreate() {
        super.onCreate();
        sProductPurchasedRepository = ProductPurchasedRepository.getInstance(this);
        sCustomerRepository=CustomerRepository.getInstance();

        sLocationRepository=CustomerLocationRepository.getInstance(this);

        sCustomer= OnlineShopSharePref.getCustomer(this);
        createNotificationChannel();
    }

    public static ProductPurchasedRepository getProductPurchasedRepository() {
        return sProductPurchasedRepository;
    }

    public static CustomerRepository getCustomerRepository() {
        return sCustomerRepository;
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
                            NotificationManager.IMPORTANCE_HIGH);

            NotificationManager notificationManager=this.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public static Customer getsCustomer() {
        return sCustomer;
    }

}
