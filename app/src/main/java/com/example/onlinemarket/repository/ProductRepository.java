package com.example.onlinemarket.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.model.Titles;
import com.example.onlinemarket.model.response.CatObj;
import com.example.onlinemarket.network.retrofit.RetrofitInstance;
import com.example.onlinemarket.network.retrofit.RetrofitInterface;
import com.example.onlinemarket.network.retrofit.gson.ProductGsonConverterCustomize;
import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.utils.ProgramUtils;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductRepository {
    private static ProductRepository sInstance;

    private MutableLiveData<List<Product>> mNewestProductLiveData =new MutableLiveData<>();
    private MutableLiveData<List<Product>> mPopulateProductLiveData =new MutableLiveData<>();
    private MutableLiveData<List<Product>> mBestProductLiveData=new MutableLiveData<>();

    private RetrofitInterface mRetrofitInterface;

    private ProductRepository() {
        Retrofit retrofit=new RetrofitInstance().getRetrofit();
        mRetrofitInterface=retrofit.create(RetrofitInterface.class);
    }

    public static ProductRepository getInstance() {
        if (sInstance == null)
            sInstance = new ProductRepository();
        return sInstance;
    }

    public void requestToServerForReceiveProducts(Map<String,String> queryMap, Titles title) {
        Call<List<Product>> call = getListCall(queryMap);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                switch (title){
                    case BEST_PRODUCT:
                        mBestProductLiveData.setValue(response.body());
                        break;
                    case NEWEST_PRODUCT:
                        mNewestProductLiveData.setValue(response.body());
                        break;
                    case MORE_REVIEWS_PRODUCT:
                        mPopulateProductLiveData.setValue(response.body());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private Call<List<Product>> getListCall(Map<String, String> queryMap) {
        return mRetrofitInterface.getListProductObjects(NetworkParams.queryForReceiveProduct(queryMap));
    }

    public MutableLiveData<List<Product>> getNewestProductLiveData() {
        return mNewestProductLiveData;
    }

    public MutableLiveData<List<Product>> getPopulateProductLiveData() {
        return mPopulateProductLiveData;
    }

    public MutableLiveData<List<Product>> getBestProductLiveData() {
        return mBestProductLiveData;
    }


}
