package com.example.onlinemarket.sharePref;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.onlinemarket.model.customer.Customer;
import com.google.gson.Gson;

public class OnlineShopSharePref {
    private static final String KEY_CUSTOMER="productIds";

    public static Customer getCustomer(Context context) {
        Gson gson=new Gson();
        String json=getSharePreference(context).getString(KEY_CUSTOMER,null);
        return gson.fromJson(json,Customer.class);
    }

    public static void saveCustomer(Context context,Customer customer){
        Gson gson=new Gson();
        String json=gson.toJson(customer);
        getSharePreference(context).
                edit().
                putString(KEY_CUSTOMER,json).
                apply();
    }

    private static SharedPreferences getSharePreference(Context context){
        return context.getApplicationContext().
                getSharedPreferences("com.example.onlinemarket",Context.MODE_PRIVATE);
    }
}
