package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.utils.Titles;
import com.example.onlinemarket.repository.CategoryRepository;
import com.example.onlinemarket.repository.ProductRepository;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NetworkTaskViewModel extends AndroidViewModel {
    private ProductRepository mProductRepository;

    private LiveData<List<Product>> mNewestProductLiveData;
    private LiveData<List<Product>> mPopulateProductLiveData;
    private LiveData<List<Product>> mBestProductLiveData;
    private LiveData<List<Product>> mSpecialProductLiveData;
    private LiveData<List<Product>> mSearchProductLiveData;

    public NetworkTaskViewModel(@NonNull Application application) {
        super(application);

        mProductRepository = ProductRepository.getInstance();

        mNewestProductLiveData= mProductRepository.getNewestProductLiveData() ;
        mPopulateProductLiveData= mProductRepository.getPopulateProductLiveData() ;
        mBestProductLiveData= mProductRepository.getBestProductLiveData() ;
        mSpecialProductLiveData=mProductRepository.getSpecialProductLiveData();
        mSearchProductLiveData=mProductRepository.getProducts();
    }

    public void requestToServerForReceiveProducts(Map<String,String> queryMap, Titles title){
        mProductRepository.requestToServerForReceiveProducts(queryMap,title);
    }

    public void requestToServerForSpecificCatProduct(int catId){
         mProductRepository.requestToServerForSpecificCatProduct(catId);
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

    public LiveData<List<Product>> getSearchProductLiveData() {
        return mSearchProductLiveData;
    }

    public LiveData<List<Product>> getSpecialProductLiveData() {
        return mSpecialProductLiveData;
    }
}
