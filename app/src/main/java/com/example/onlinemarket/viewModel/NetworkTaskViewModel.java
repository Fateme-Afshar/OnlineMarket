package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.model.Titles;
import com.example.onlinemarket.repository.OnlineMarketRepository;

import java.util.List;
import java.util.Map;


public class NetworkTaskViewModel extends AndroidViewModel {
    private OnlineMarketRepository mOnlineMarketRepository;
    private LiveData<List<Product>> mNewestProductLiveData;
    private LiveData<List<Product>> mPopulateProductLiveData;
    private LiveData<List<Product>> mBestProductLiveData;

    public NetworkTaskViewModel(@NonNull Application application) {
        super(application);

        mOnlineMarketRepository = OnlineMarketRepository.getInstance();

        mNewestProductLiveData=mOnlineMarketRepository.getNewestProductLiveData() ;
        mPopulateProductLiveData=mOnlineMarketRepository.getPopulateProductLiveData() ;
        mBestProductLiveData=mOnlineMarketRepository.getBestProductLiveData() ;
    }

    public void requestToServerForReceiveProducts(Map<String,String> queryMap, Titles title){
        mOnlineMarketRepository.requestToServerForReceiveProducts(queryMap,title);
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

    /*    public LiveData<List<ProductModel>> getProductListLiveData() {
        mCategoryListLiveData=mDigiKalaRepository.getCategoryListMutableLiveData();
        return mProductListLiveData;
    }

    public LiveData<List<CategoriesModel>> getCategoryListLiveData() {
        mCategoryListLiveData=mDigiKalaRepository.getCategoryListMutableLiveData();
        return mCategoryListLiveData;
    }*/

}
