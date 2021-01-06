package com.example.onlinemarket.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlinemarket.model.AttributeInfo;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.FilterRepository;
import com.example.onlinemarket.repository.ProductRepository;
import com.example.onlinemarket.utils.ProgramUtils;
import com.example.onlinemarket.utils.QueryParameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterViewModel extends AndroidViewModel {
    private FilterRepository mRepository;
    private ProductRepository mProductRepository;
    private OnBtnClickListener mOnBtnClickListener;

    private List<Integer> mFilterIds;

    public FilterViewModel(@NonNull Application application) {
        super(application);
        mRepository = FilterRepository.getInstance();
        mProductRepository = ProductRepository.getInstance();
    }

    public void requestToServerForReceiveAttributes() {
        mRepository.requestToServerForReceiveAttributes();
    }

    public void requestToServerForReceiveInfoSectionAttribute(int attributeId) {
        mRepository.requestToServerForReceiveInfoSectionAttribute(attributeId);
    }

    public void requestToServerForReceiveFilterProducts(List<Integer> filterItemIds, String filterName) {
        Log.d(ProgramUtils.TAG,"FilterViewModel : Creating filter query parameters");

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(QueryParameters.ATTRIBUTE, filterName);
        StringBuilder queryValues = new StringBuilder();
        for (int i = 0; i < filterItemIds.size(); i++) {
            queryValues.append(filterItemIds.get(i)).append(",");
        }
        queryParameters.put(QueryParameters.ATTRIBUTE_TERM, queryValues.toString());

        Log.d(ProgramUtils.TAG,"FilterViewModel : Request to server for receive filter products");
        mProductRepository.requestToServerForReceiveProducts(queryParameters);
    }

    public void onClickListener(int attributeId) {
        mOnBtnClickListener.onAttributeSelected(attributeId);
    }

    public LiveData<List<AttributeInfo>> getAttributeListLiveData() {
        return mRepository.getAttributeListLiveData();
    }

    public LiveData<AttributeInfo> getAttributeLiveData() {
        return mRepository.getAttributeLiveData();
    }

    public interface OnBtnClickListener {
        void onAttributeSelected(int attributeId);
    }

    public void setOnBtnClickListener(OnBtnClickListener onBtnClickListener) {
        mOnBtnClickListener = onBtnClickListener;
    }

    public LiveData<List<Product>> getFilterProducts() {
        return mProductRepository.getProducts();
    }

    public List<Integer> getFilterIds() {
        return mFilterIds;
    }

    public void setFilterIds(List<Integer> filterIds) {
        mFilterIds = filterIds;
    }
}
