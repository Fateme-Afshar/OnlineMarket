package com.example.onlinemarket.sharePref;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.onlinemarket.model.CustomerLocation;
import com.example.onlinemarket.model.customer.Customer;
import com.google.gson.Gson;

public class OnlineShopSharePref {
    private static final String KEY_CUSTOMER="customerLogin";

    private static final String KEY_LASTED_PRODUCT_ID="lastedProductId";

    private static final String KEY_CUSTOMER_LASTED_LOCATION ="customerLastLocation";

    public static Customer getCustomer(Context context) {
        Gson gson=new Gson();
        String json=getSharePreference(context).getString(KEY_CUSTOMER,null);
        return gson.fromJson(json,Customer.class);
    }

    public static void saveCustomer(Context context, Customer customer){
        String json = getJsonFormat(customer);
        getSharePreference(context).
                edit().
                putString(KEY_CUSTOMER,json).
                apply();
    }

    public static void setLastedProductId(Context context,int lastedProductId){
        getSharePreference(context.getApplicationContext()).
                edit().
                putLong(KEY_LASTED_PRODUCT_ID,lastedProductId).
                apply();
    }

    public static int getLastedProductId(Context context){
        return getSharePreference(context.getApplicationContext()).
                getInt(KEY_LASTED_PRODUCT_ID,0);
    }

    public static void setCustomerLastedLocation(Context context,CustomerLocation customerLocation){
        String json=getJsonFormat(customerLocation);
        getSharePreference(context.getApplicationContext()).
                edit().
                putString(KEY_CUSTOMER_LASTED_LOCATION,json).
                apply();
    }

    public static CustomerLocation getCustomerLastedLocation(Context context) {
        Gson gson=new Gson();
        String customerLocationJson =
                getSharePreference(context).
                        getString(KEY_CUSTOMER_LASTED_LOCATION,null);

        return gson.fromJson(customerLocationJson,CustomerLocation.class);
    }

    private static SharedPreferences getSharePreference(Context context){
        return context.getApplicationContext().
                getSharedPreferences("com.example.onlinemarket",Context.MODE_PRIVATE);
    }

    private static String getJsonFormat(Object object) {
        Gson gson=new Gson();
        return gson.toJson(object);
    }
}
