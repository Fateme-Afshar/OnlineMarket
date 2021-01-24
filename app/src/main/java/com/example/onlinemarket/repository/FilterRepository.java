package com.example.onlinemarket.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.model.AttributeInfo;
import com.example.onlinemarket.network.retrofit.RetrofitInstance;
import com.example.onlinemarket.network.retrofit.RetrofitInterface;
import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.utils.ProgramUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FilterRepository {
    private static FilterRepository sInstance;
    private MutableLiveData<List<AttributeInfo>> mColorAttributeListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<AttributeInfo>> mSizeAttributeListLiveData = new MutableLiveData<>();
    private MutableLiveData<AttributeInfo> mAttributeLiveData = new MutableLiveData<>();

    private RetrofitInterface mRetrofitInterface;

    private List<Integer> mAttributeId = new ArrayList<>();

    private FilterRepository() {
        Retrofit retrofit = RetrofitInstance.getRetrofit();
        mRetrofitInterface = retrofit.create(RetrofitInterface.class);
    }

    public static FilterRepository getInstance() {
        if (sInstance == null)
            sInstance = new FilterRepository();
        return sInstance;
    }

    public void requestToServerForReceiveAttributes() {
        Call<List<AttributeInfo>> call = mRetrofitInterface.getAttributes(NetworkParams.MAP_KEYS);
        Log.d(ProgramUtils.TAG, "FilterRepository : Request to server for Receive Attributes");
        call.enqueue(new Callback<List<AttributeInfo>>() {
            @Override
            public void onResponse(Call<List<AttributeInfo>> call, Response<List<AttributeInfo>> response) {
                mColorAttributeListLiveData.setValue(response.body());
                Log.d(ProgramUtils.TAG, "FilterRepository : Receive Attributes");
            }

            @Override
            public void onFailure(Call<List<AttributeInfo>> call, Throwable t) {

            }
        });
    }

    /*    Request to server for Receive Information Every Section Attributes (for example if id=3 receive all colors that define in site)
         reuse same live data that use in requestToServerForReceiveAttributes method*/
    public void requestToServerForReceiveInfoColorAttribute() {
        Call<List<AttributeInfo>> call = mRetrofitInterface.
                getEveryAttributePart(String.valueOf(3), NetworkParams.MAP_KEYS);

        call.enqueue(new Callback<List<AttributeInfo>>() {
            @Override
            public void onResponse(Call<List<AttributeInfo>> call, Response<List<AttributeInfo>> response) {
                Log.d(ProgramUtils.TAG,
                        "FilterRepository : Request to server for Receive Information Color Attributes");
                mColorAttributeListLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<AttributeInfo>> call, Throwable t) {

            }
        });
    }

    public void requestToServerForReceiveInfoSizeAttribute(){
        Call<List<AttributeInfo>> call = mRetrofitInterface.
                getEveryAttributePart(String.valueOf(4), NetworkParams.MAP_KEYS);

        call.enqueue(new Callback<List<AttributeInfo>>() {
            @Override
            public void onResponse(Call<List<AttributeInfo>> call, Response<List<AttributeInfo>> response) {
                Log.d(ProgramUtils.TAG,
                        "FilterRepository : Request to server for Receive Information Size Attributes");
                mSizeAttributeListLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<AttributeInfo>> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<List<AttributeInfo>> getColorAttributeListLiveData() {
        return mColorAttributeListLiveData;
    }

    public MutableLiveData<List<AttributeInfo>> getSizeAttributeListLiveData() {
        return mSizeAttributeListLiveData;
    }

    public MutableLiveData<AttributeInfo> getAttributeLiveData() {
        return mAttributeLiveData;
    }
}
