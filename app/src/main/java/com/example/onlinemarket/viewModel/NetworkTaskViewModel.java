package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.utils.QueryParameters;
import com.example.onlinemarket.utils.Titles;
import com.example.onlinemarket.repository.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NetworkTaskViewModel extends AndroidViewModel {
    private ProductRepository mProductRepository;
    private LiveData<List<Product>> mNewestProductLiveData;
    private LiveData<List<Product>> mPopulateProductLiveData;
    private LiveData<List<Product>> mBestProductLiveData;
    private LiveData<List<Product>> mSpecialProductLiveData;
    private LiveData<List<Product>> mProductLiveData;

    public NetworkTaskViewModel(@NonNull Application application) {
        super(application);

        mProductRepository = ProductRepository.getInstance();

        mNewestProductLiveData= mProductRepository.getNewestProductLiveData() ;
        mPopulateProductLiveData= mProductRepository.getPopulateProductLiveData() ;
        mBestProductLiveData= mProductRepository.getBestProductLiveData() ;
        mSpecialProductLiveData=mProductRepository.getSpecialProductLiveData();
        mProductLiveData =mProductRepository.getProducts();

    }

    public void requestToServerForReceiveProducts(Map<String,String> queryMap, Titles title){
        mProductRepository.requestToServerForReceiveProducts(queryMap,title);
    }

    public void requestToServerForSearchProducts(String title,String search){
        Map<String, String> queryParameter = NetworkParams.querySearch(title, search);
        mProductRepository.requestToServerForReceiveProducts(queryParameter);
    }

    public LiveData<List<Product>> geNewestProductLiveData() {
        return mNewestProductLiveData;
    }

    public LiveData<List<Product>> getPopulateProductLiveData() {
        return mPopulateProductLiveData;
    }

    public LiveData<List<Product>> getBestProductLiveData() {
        return mBestProductLiveData;
    }

    public LiveData<List<Product>> getProductLiveData() {
        return mProductLiveData;
    }

    public LiveData<List<Product>> getSpecialProductLiveData() {
        return mSpecialProductLiveData;
    }

    public Map<String, String> getQueryMapNewest() {
        Map<String, String> queryMapNewest = new HashMap<>();
        queryMapNewest.put(QueryParameters.ORDER_BY, "date");
        queryMapNewest.put(QueryParameters.ORDER, NetworkParams.ORDER_DESC);
        return queryMapNewest;
    }

    public Map<String, String> getQueryMapBest() {
        Map<String, String> queryMapBest = new HashMap<>();
        queryMapBest.put(QueryParameters.ORDER_BY, "rating");
        queryMapBest.put(QueryParameters.ORDER, NetworkParams.ORDER_DESC);
        return queryMapBest;
    }

    public Map<String, String> getQueryMapPopulate() {
        Map<String, String> queryMapPopulate = new HashMap<>();
        queryMapPopulate.put(QueryParameters.ORDER_BY, "popularity");
        queryMapPopulate.put(QueryParameters.ORDER, NetworkParams.ORDER_DESC);
        return queryMapPopulate;
    }

    public Map<String, String> getQueryMapSpecial() {
        Map<String, String> queryMapSpecial = new HashMap<>();
        queryMapSpecial.put(QueryParameters.ON_SALE, "true");
        return queryMapSpecial;
    }
}
