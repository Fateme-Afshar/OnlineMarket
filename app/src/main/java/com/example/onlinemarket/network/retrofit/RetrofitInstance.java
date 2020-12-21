package com.example.onlinemarket.network.retrofit;

import com.example.onlinemarket.utils.NetworkParams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * this class has two constructor ,
 * one of them create retrofit instance with Gson Converter Customize
 * another create retrofit instance with default Gson Converter.
 */
public class RetrofitInstance {
    private Retrofit mRetrofit;

    public RetrofitInstance() {
            mRetrofit=new Retrofit.Builder().
                    baseUrl(NetworkParams.BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).
                    build();
    }

    public RetrofitInstance(Type type,Object typeAdapter) {
        mRetrofit=new Retrofit.Builder().
                baseUrl(NetworkParams.BASE_URL).
                addConverterFactory(createConverterFactoryCustom(type,typeAdapter)).
                build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
    private Converter.Factory createConverterFactoryCustom(Type type,Object typeAdapter){
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.registerTypeAdapter(type,typeAdapter);

        Gson gson=gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }
}
