package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.utils.Titles;
import com.example.onlinemarket.repository.CategoryRepository;
import com.example.onlinemarket.repository.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NetworkTaskViewModel extends AndroidViewModel {
    private ProductRepository mProductRepository;
    private CategoryRepository mCategoryRepository;

    private LiveData<List<Product>> mNewestProductLiveData;
    private LiveData<List<Product>> mPopulateProductLiveData;
    private LiveData<List<Product>> mBestProductLiveData;
    private LiveData<List<Product>> mSpecialProductLiveData;
    private LiveData<List<Product>> mSearchProductLiveData;

    private LiveData<List<Category>> mCategoryLiveData;

    public NetworkTaskViewModel(@NonNull Application application) {
        super(application);

        mProductRepository = ProductRepository.getInstance();

        mNewestProductLiveData= mProductRepository.getNewestProductLiveData() ;
        mPopulateProductLiveData= mProductRepository.getPopulateProductLiveData() ;
        mBestProductLiveData= mProductRepository.getBestProductLiveData() ;
        mSpecialProductLiveData=mProductRepository.getSpecialProductLiveData();
        mSearchProductLiveData=mProductRepository.getProducts();

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

    public void requestToServerForSearchProducts(String title,String search){
        Map<String,String> queryParameter=new HashMap<>();
        queryParameter.put("attributes",title);
        queryParameter.put("search",search);
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

    public LiveData<List<Category>> getCategoryLiveData() {
        return mCategoryLiveData;
    }

    public LiveData<List<Product>> getSearchProductLiveData() {
        return mSearchProductLiveData;
    }

    public LiveData<List<Product>> getSpecialProductLiveData() {
        return mSpecialProductLiveData;
    }
}
