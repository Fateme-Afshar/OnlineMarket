package com.example.onlinemarket.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.model.AttributeInfo;
import com.example.onlinemarket.network.retrofit.RetrofitInstance;
import com.example.onlinemarket.network.retrofit.RetrofitInterface;
import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.utils.ProgramUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

@Singleton
public class FilterRepository {
    private static FilterRepository sInstance;
    private List<AttributeInfo> mColorAttributeList = new ArrayList<>();
    private List<AttributeInfo> mSizeAttributeList = new ArrayList<>();
    private MutableLiveData<AttributeInfo> mAttributeLiveData = new MutableLiveData<>();

    private RetrofitInterface mRetrofitInterface;

    private List<Integer> mAttributeId = new ArrayList<>();

    @Inject
    public FilterRepository() {
        Retrofit retrofit = RetrofitInstance.getRetrofit();
        mRetrofitInterface = retrofit.create(RetrofitInterface.class);
    }

    public static FilterRepository getInstance() {
        if (sInstance == null)
            sInstance = new FilterRepository();
        return sInstance;
    }

    @SuppressLint("CheckResult")
    public void requestToServerForReceiveAttributes() {
        Observable<List<AttributeInfo>> observable = mRetrofitInterface.getAttributes(NetworkParams.MAP_KEYS);
        Log.d(ProgramUtils.TAG, "FilterRepository : Request to server for Receive Attributes");

        observable.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(this::setColorAttributeList,throwable -> {Log.e(ProgramUtils.TAG, "FilterRepository :Fail Receive Attributes");});
    }

    public void setColorAttributeList(List<AttributeInfo> colorAttributeList) {
        mColorAttributeList = colorAttributeList;
    }

    /*    Request to server for Receive Information Every Section Attributes (for example if id=3 receive all colors that define in site)
             reuse same live data that use in requestToServerForReceiveAttributes method*/
    @SuppressLint("CheckResult")
    public Observable<List<AttributeInfo>> requestToServerForReceiveInfoColorAttribute() {
        return mRetrofitInterface.
                getEveryAttributePart(String.valueOf(3), NetworkParams.MAP_KEYS);
    }

    @SuppressLint("CheckResult")
    public Observable<List<AttributeInfo>> requestToServerForReceiveInfoSizeAttribute(){
        return mRetrofitInterface.
                getEveryAttributePart(String.valueOf(4), NetworkParams.MAP_KEYS);
    }

    public void setSizeAttributeList(List<AttributeInfo> sizeAttributeList) {
        mSizeAttributeList = sizeAttributeList;
    }

    public List<AttributeInfo> getColorAttributeList() {
        return mColorAttributeList;
    }

    public List<AttributeInfo> getSizeAttributeList() {
        return mSizeAttributeList;
    }

    public MutableLiveData<AttributeInfo> getAttributeLiveData() {
        return mAttributeLiveData;
    }
}
