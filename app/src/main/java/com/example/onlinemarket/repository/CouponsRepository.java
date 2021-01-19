package com.example.onlinemarket.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.model.coupons.Coupons;
import com.example.onlinemarket.network.retrofit.RetrofitInstance;
import com.example.onlinemarket.network.retrofit.RetrofitInterface;
import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.utils.ProgramUtils;

import java.util.List;
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

    public void searchCouponsCode(String code) {
        Call<List<Coupons>> couponsCall=
                mRetrofitInterface.getCoupons(NetworkParams.querySearch("code",code));

        couponsCall.enqueue(new Callback<List<Coupons>>() {
            @Override
            public void onResponse(Call<List<Coupons>> call, Response<List<Coupons>> response) {
                    Coupons coupons=new Coupons
                            (response.body().get(0).getAmount(),
                                    response.body().get(0).getCode(),
                                    response.body().get(0).getMinimumAmount(),
                                    response.body().get(0).getDescription());

                    mCouponsMutableLiveData.setValue(coupons);
            }

            @Override
            public void onFailure(Call<List<Coupons>> call, Throwable t) {
                Log.e(ProgramUtils.TEST_TAG,"CouponsRepository : Coupons check fail " +t.getMessage());
            }
        });
    }

    public MutableLiveData<Coupons> getCouponsMutableLiveData() {
        return mCouponsMutableLiveData;
    }
}
