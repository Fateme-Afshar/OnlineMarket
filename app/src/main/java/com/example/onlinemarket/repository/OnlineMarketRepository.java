package com.example.onlinemarket.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.model.Titles;
import com.example.onlinemarket.network.retrofit.RetrofitInstance;
import com.example.onlinemarket.network.retrofit.RetrofitInterface;
import com.example.onlinemarket.network.retrofit.gson.ProductGsonConverterCustomize;
import com.example.onlinemarket.utils.NetworkParams;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OnlineMarketRepository {
    private static OnlineMarketRepository sInstance;
    private MutableLiveData<List<Product>> mNewestProductLiveData =new MutableLiveData<>();
    private MutableLiveData<List<Product>> mPopulateProductLiveData =new MutableLiveData<>();
    private MutableLiveData<List<Product>> mBestProductLiveData=new MutableLiveData<>();

    private OnlineMarketRepository() {
    }

    public static OnlineMarketRepository getInstance() {
        if (sInstance == null)
            sInstance = new OnlineMarketRepository();
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
        Retrofit retrofit = new RetrofitInstance(
                new TypeToken<List<Product>>() {
                }.getType(),
                new ProductGsonConverterCustomize()).
                getRetrofit();
        RetrofitInterface retrofitInterface = getRetrofitInterface(retrofit);

        return retrofitInterface.getListProductObjects(NetworkParams.queryForReceiveProduct(queryMap));
    }

    private RetrofitInterface getRetrofitInterface(Retrofit retrofit) {
        return retrofit.create(RetrofitInterface.class);
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
