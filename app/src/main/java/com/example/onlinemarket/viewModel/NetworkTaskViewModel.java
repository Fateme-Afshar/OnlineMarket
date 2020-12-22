package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.model.Titles;
import com.example.onlinemarket.repository.CategoryRepository;
import com.example.onlinemarket.repository.ProductRepository;

import java.util.List;
import java.util.Map;


public class NetworkTaskViewModel extends AndroidViewModel {
    private ProductRepository mProductRepository;
    private CategoryRepository mCategoryRepository;

    private LiveData<List<Product>> mNewestProductLiveData;
    private LiveData<List<Product>> mPopulateProductLiveData;
    private LiveData<List<Product>> mBestProductLiveData;

    private LiveData<List<Category>> mCategoryLiveData;

    public NetworkTaskViewModel(@NonNull Application application) {
        super(application);

        mProductRepository = ProductRepository.getInstance();

        mNewestProductLiveData= mProductRepository.getNewestProductLiveData() ;
        mPopulateProductLiveData= mProductRepository.getPopulateProductLiveData() ;
        mBestProductLiveData= mProductRepository.getBestProductLiveData() ;

        mCategoryRepository= CategoryRepository.getInstance();
        mCategoryLiveData=mCategoryRepository.getCategoryLiveData();
    }

    public void requestToServerForReceiveProducts(Map<String,String> queryMap, Titles title){
        mProductRepository.requestToServerForReceiveProducts(queryMap,title);
    }

    public List<Product> requestToServerForSpecificCatProduct(int catId){
        return mProductRepository.requestToServerForSpecificCatProduct(catId);
    }

    public List<Category> requestToServerForCategories(){
        return mCategoryRepository.requestToServerForCategories();
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

    public LiveData<List<Category>> getCategoryLiveData() {
        return mCategoryLiveData;
    }
}
