package com.example.onlinemarket.network.retrofit;

import com.example.onlinemarket.utils.NetworkParams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * this class has two ststic method ,
 * one of them create retrofit instance with Gson Converter Customize
 * another create retrofit instance with default Gson Converter.
 */
@Singleton
public class RetrofitInstance {

    @Inject
    public RetrofitInstance() {
    }

    public static Retrofit getRetrofit(Type type, Object typeAdapter) {
        return new Retrofit.Builder().
                baseUrl(NetworkParams.BASE_URL).
                addConverterFactory(createConverterFactoryCustom(type,typeAdapter)).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                build();
    }

    public static Retrofit getRetrofit(){
        return new Retrofit.Builder().
                baseUrl(NetworkParams.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                build();
    }

    public static Retrofit getPostRetrofit(){
        OkHttpClient client = new OkHttpClient();
        return new Retrofit.Builder().
                baseUrl(NetworkParams.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client).
                build();
    }

    private static Converter.Factory createConverterFactoryCustom(Type type,Object typeAdapter){
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.registerTypeAdapter(type,typeAdapter);

        Gson gson=gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }
}
