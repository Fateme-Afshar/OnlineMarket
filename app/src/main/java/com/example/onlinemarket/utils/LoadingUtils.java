package com.example.onlinemarket.utils;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class LoadingUtils {

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean checkHasInternet(ConnectivityManager connectivityManager) {
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo!=null;
    }
}
