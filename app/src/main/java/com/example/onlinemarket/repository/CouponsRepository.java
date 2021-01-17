package com.example.onlinemarket.repository;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.model.coupons.Coupons;
import com.example.onlinemarket.network.retrofit.RetrofitInstance;
import com.example.onlinemarket.network.retrofit.RetrofitInterface;
import com.example.onlinemarket.utils.NetworkParams;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CouponsRepository {
    private static CouponsRepository sInstance;
    private RetrofitInterface mRetrofitInterface;

    private MutableLiveData<Coupons> mCouponsMutableLiveData=new MutableLiveData<>();

    private CouponsRepository() {
        Retrofit retrofit= RetrofitInstance.getPostRetrofit();
        mRetrofitInterface=retrofit.create(RetrofitInterface.class);
    }

    public static CouponsRepository getInstance() {
        if (sInstance==null)
            sInstance=new CouponsRepository();
        return sInstance;
    }

    public void searchCouponsCode(String code){
        Map<String,String> queryParameter=NetworkParams.querySearch("code",code);
        queryParameter.putAll(NetworkParams.MAP_KEYS);

        Call<Coupons> couponsCall=mRetrofitInterface.getCoupons(queryParameter);
        couponsCall.enqueue(new Callback<Coupons>() {
            @Override
            public void onResponse(Call<Coupons> call, Response<Coupons> response) {
               mCouponsMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Coupons> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<Coupons> getCouponsMutableLiveData() {
        return mCouponsMutableLiveData;
    }
}
