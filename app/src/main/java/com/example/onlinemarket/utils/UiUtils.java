package com.example.onlinemarket.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.onlinemarket.OnlineShopApplication;

public class UiUtils {
    private Context mContext;

    public UiUtils(Context context) {
        mContext = context;
    }

    public void returnToast(String text){
        Toast.makeText(mContext,
                text,
                Toast.LENGTH_LONG).show();
    }

    public void returnToast(int text){
        Toast.makeText(mContext,
                text,
                Toast.LENGTH_LONG).show();
    }
}
