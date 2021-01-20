package com.example.onlinemarket.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.model.Coupons;
import com.example.onlinemarket.network.retrofit.RetrofitInstance;
import com.example.onlinemarket.network.retrofit.RetrofitInterface;
import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.utils.ProgramUtils;

import java.util.List;

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

    public void searchCouponsCode(String code) {
        Call<List<Coupons>> couponsCall=
                mRetrofitInterface.getCoupons(NetworkParams.querySearch("code",code));

        couponsCall.enqueue(new Callback<List<Coupons>>() {
            @Override
            public void onResponse(Call<List<Coupons>> call, Response<List<Coupons>> response) {
                Coupons coupons = null;
                if (!response.isSuccessful() || response.body().size() == 0) {
                    Log.e(ProgramUtils.TAG,
                            "CouponsRepository : Coupons check fail cause by one of them : " +
                                    response.code() + " response body size:  " + response.body().size());
                }
                mCouponsMutableLiveData.setValue(coupons);
            }

            @Override
            public void onFailure(Call<List<Coupons>> call, Throwable t) {
                Log.e(ProgramUtils.TAG,"CouponsRepository : Coupons check fail " +t.getMessage());
            }
        });
    }

    public MutableLiveData<Coupons> getCouponsMutableLiveData() {
        return mCouponsMutableLiveData;
    }
}
