package com.example.onlinemarket.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.model.AttributeInfo;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.FilterRepository;
import com.example.onlinemarket.repository.MainLoadingRepository;
import com.example.onlinemarket.repository.ProductRepository;
import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.utils.ProgramUtils;
import com.example.onlinemarket.utils.QueryParameters;
import com.example.onlinemarket.utils.Titles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterViewModel extends ViewModel {
    private FilterRepository mRepository;
    private ProductRepository mProductRepository;
    private MainLoadingRepository mMainLoadingRepository;
    private OnBtnClickListener mOnBtnClickListener;

    private List<Integer> mFilterIds;

    public FilterViewModel() {
        mRepository = FilterRepository.getInstance();
        mProductRepository = ProductRepository.getInstance();
        mMainLoadingRepository=MainLoadingRepository.getInstance();
    }

    public void requestToServerForReceiveAttributes() {
        mRepository.requestToServerForReceiveAttributes();
    }

    public void requestToServerForReceiveFilterProductsOnAttributeTerm(List<Integer> filterItemIds, String filterName) {
        Log.d(ProgramUtils.TAG,"FilterViewModel : Creating filter query parameters");

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put(QueryParameters.ATTRIBUTE, filterName);
        StringBuilder queryValues = new StringBuilder();
        for (int i = 0; i < filterItemIds.size(); i++) {
            queryValues.append(filterItemIds.get(i)).append(",");
        }
        queryParameters.put(QueryParameters.ATTRIBUTE_TERM, queryValues.toString());
        queryParameters.put("per_page","100");

        Log.d(ProgramUtils.TAG,"FilterViewModel : Request to server for receive filter products");
        mProductRepository.requestToServerForReceiveProducts(queryParameters);
    }

    public void requestToServerForReceiveFilterProductsOnMore(String orderby,String order){
        Map<String,String> queryParameter=new HashMap<>();
        queryParameter.put(QueryParameters.ORDER_BY,orderby);
        queryParameter.put(QueryParameters.ORDER,order);
        queryParameter.put("per_page","100");
        mProductRepository.requestToServerForReceiveProducts(queryParameter);
    }

    public void onAttributeBtnClickListener(int attributeId) {
        mOnBtnClickListener.onAttributeSelected(attributeId);
    }

    public void onMoreFilterBtnClickListener(){
        mOnBtnClickListener.onFilterBtnClickListener();
    }

    public List<AttributeInfo> getColorAttributeInfoList() {
        return mMainLoadingRepository.getColorAttributeInfoList();
    }

    public List<AttributeInfo> getSizeAttributeInfoList() {
        return mMainLoadingRepository.getSizeAttributeInfoList();
    }

    public LiveData<AttributeInfo> getAttributeLiveData() {
        return mRepository.getAttributeLiveData();
    }

    public void setOnFilterAttributeClickListener(OnBtnClickListener onBtnClickListener) {
        mOnBtnClickListener = onBtnClickListener;
    }

    public List<Product> getFilterProducts() {
        return mProductRepository.getProducts();
    }

    public List<Integer> getFilterIds() {
        return mFilterIds;
    }

    public void setFilterIds(List<Integer> filterIds) {
        mFilterIds = filterIds;
    }

    public interface OnBtnClickListener {
        void onAttributeSelected(int attributeId);
        void onFilterBtnClickListener();
    }
}
