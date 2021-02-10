package com.example.onlinemarket.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.onlinemarket.OnlineShopApplication;

public class UiUtils {

    public static void returnToast(Context context,String text){
        Toast.makeText(context.getApplicationContext(),
                text,
                Toast.LENGTH_LONG).show();
    }

    public static void returnToast(Context context, int text){
        Toast.makeText(context.getApplicationContext(),
                text,
                Toast.LENGTH_LONG).show();
    }
}
