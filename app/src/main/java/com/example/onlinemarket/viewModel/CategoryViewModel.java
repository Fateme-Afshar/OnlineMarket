package com.example.onlinemarket.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewModel extends ViewModel {
    private CategoryRepository mCategoryRepository;
    private LiveData<List<Category>> mCategoryLiveData;
    private LiveData<List<Product>> mProductLiveData;

    private List<Product> mProductList=new ArrayList<>();
    private List<Category> mCategory;

    {
        mCategoryRepository=CategoryRepository.getInstance();
        mCategoryLiveData=mCategoryRepository.getCategoryLiveData();
        mProductLiveData=mCategoryRepository.getProducts();
    }

    public LiveData<List<Category>> getCategoryLiveData() {
        return mCategoryLiveData;
    }

    public List<Category> getCategory() {
        return mCategory;
    }

    public void setCategory(List<Category> category) {
        mCategory = category;
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

    public void requestToServerForCategories(){
        mCategoryRepository.requestToServerForCategories();
    }

    public void requestToServerForSpecificCatProduct(int catId){
        mCategoryRepository.requestToServerForSpecificCatProduct(catId);
    }
}
