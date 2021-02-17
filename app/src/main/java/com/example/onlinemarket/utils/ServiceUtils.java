package com.example.onlinemarket.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.onlinemarket.OnlineShopApplication;
import com.example.onlinemarket.R;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.ProductRepository;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;
import com.example.onlinemarket.view.activity.MainActivity;

import java.util.List;

public class ServiceUtils {
    public static void checkLastProductId(Context context) {
        ProductRepository productRepository=ProductRepository.getInstance();
        productRepository.requestToServerForReceiveProducts(NetworkParams.MAP_KEYS);

        Handler handler = new Handler(context.getMainLooper());

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<Product> productList=productRepository.getProducts();

                int lastedProductId = productList.get(productList.size() - 1).getId();
                if (lastedProductId != OnlineShopSharePref.getLastedProductId(context.getApplicationContext())) {
                    Log.d(ProgramUtils.TEST_TAG,"show notification");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        showNotification(context);
                    }
                    OnlineShopSharePref.setLastedProductId(context.getApplicationContext(), lastedProductId);
                }else {
                    Log.d(ProgramUtils.TEST_TAG,"nothing to show notification");
                }
            }
        };

        handler.post(runnable);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void showNotification(Context context){
        NotificationManager notificationManager=context.getSystemService(NotificationManager.class);
        PendingIntent pendingIntent=PendingIntent.getService(context,
                1,
                MainActivity.newIntent(context),
                PendingIntent.FLAG_NO_CREATE);

        NotificationCompat.Builder builder=
                new NotificationCompat.Builder(context, OnlineShopApplication.CHANNEL_ID ).
                        setSmallIcon(R.drawable.ic_notifications).
                        setContentTitle("Online Shop").
                        setContentText("There is new product").
                        setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);
        notificationManager.notify(1,builder.build());
    }
}
