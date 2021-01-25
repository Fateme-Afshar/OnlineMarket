package com.example.onlinemarket.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.CategoryRepository;
import com.example.onlinemarket.repository.MainLoadingRepository;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewModel extends ViewModel {
    private CategoryRepository mCategoryRepository;
    private List<Category> mCategoryList;
    private LiveData<List<Product>> mProductLiveData;

    private MainLoadingRepository mMainLoadingRepository;

    private List<Product> mProductList=new ArrayList<>();

    {
        mCategoryRepository=CategoryRepository.getInstance();
        mMainLoadingRepository=MainLoadingRepository.getInstance();

        mProductLiveData=mCategoryRepository.getProducts();
        mCategoryList=mMainLoadingRepository.getCategoryList();
    }

    public List<Category> getCategoryList() {
        return mCategoryList;
    }


    public LiveData<List<Product>> getProductLiveData() {
        return mProductLiveData;
    }

    public List<Product> getProductList() {
        return mProductList;
    }

    public void setProductList(List<Product> productList) {
        mProductList = productList;
    }

    public void requestToServerForSpecificCatProduct(int catId){
        mCategoryRepository.requestToServerForSpecificCatProduct(catId);
    }
}
