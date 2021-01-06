package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.model.AttributeInfo;
import com.example.onlinemarket.repository.AttributeRepository;

import java.util.ArrayList;
import java.util.List;

public class FilterViewModel extends AndroidViewModel {
    private AttributeRepository mRepository;
    private OnBtnClickListener mOnBtnClickListener;

    public FilterViewModel(@NonNull Application application) {
        super(application);
        mRepository=AttributeRepository.getInstance();
    }

    public void requestToServerForReceiveAttributes(){
        mRepository.requestToServerForReceiveAttributes();
    }

    public void requestToServerForReceiveInfoSectionAttribute(int attributeId){
        mRepository.requestToServerForReceiveInfoSectionAttribute(attributeId);
    }

    public void onClickListener(int attributeId){
        mOnBtnClickListener.onAttributeSelected(attributeId);
    }

    public LiveData<List<AttributeInfo>> getAttributeListLiveData(){
        return mRepository.getAttributeListLiveData();
    }

    public LiveData<AttributeInfo> getAttributeLiveData(){
            return mRepository.getAttributeLiveData();
    }

    public interface OnBtnClickListener {
        void onAttributeSelected(int attributeId);
    }

    public void setOnBtnClickListener(OnBtnClickListener onBtnClickListener) {
        mOnBtnClickListener = onBtnClickListener;
    }

}
