package com.example.onlinemarket.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.network.retrofit.RetrofitInstance;
import com.example.onlinemarket.network.retrofit.RetrofitInterface;
import com.example.onlinemarket.network.retrofit.gson.ProductGsonConverterCustomize;
import com.example.onlinemarket.utils.NetworkParams;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OnlineMarketRepository {
    private static OnlineMarketRepository sInstance;
    private MutableLiveData<List<Product>> mProductLiveData=new MutableLiveData<>();

    private OnlineMarketRepository() {
    }

    public static OnlineMarketRepository getInstance() {
        if (sInstance == null)
            sInstance = new OnlineMarketRepository();
        return sInstance;
    }

    public void requestToServerForReceiveProducts(Map<String,String> queryMap) {
        Retrofit retrofit = new RetrofitInstance(
                new TypeToken<List<Product>>() {
                }.getType(),
                new ProductGsonConverterCustomize()).
                getRetrofit();
        RetrofitInterface retrofitInterface = getRetrofitInterface(retrofit);

        Call<List<Product>> call =
                retrofitInterface.getListProductObjects(NetworkParams.queryForReceiveProduct(queryMap));

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                mProductLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private RetrofitInterface getRetrofitInterface(Retrofit retrofit) {
        return retrofit.create(RetrofitInterface.class);
    }

    public MutableLiveData<List<Product>> getProductLiveData() {
        return mProductLiveData;
    }
}
