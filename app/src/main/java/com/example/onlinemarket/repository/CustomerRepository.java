package com.example.onlinemarket.repository;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.network.retrofit.RetrofitInstance;
import com.example.onlinemarket.network.retrofit.RetrofitInterface;
import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.utils.ProgramUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CustomerRepository {
    private static CustomerRepository sInstance;
    private RetrofitInterface mRetrofitInterface;

    private CustomerRepository() {
    }

    public static CustomerRepository getInstance() {
        if (sInstance == null)
            sInstance = new CustomerRepository();
        return sInstance;
    }


    public static Map<String, String> getMapFormat(Customer customer) {
        Map<String, String> customerMap = new HashMap<>();
        customerMap.put("first_name", customer.getFirstName());
        customerMap.put("last_name", customer.getLastName());
        customerMap.put("email", customer.getEmail());
        return customerMap;
    }
}
