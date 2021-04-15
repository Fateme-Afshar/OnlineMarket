package com.example.onlinemarket.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.di.ActivityScope;
import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.CategoryRepository;
import com.example.onlinemarket.repository.MainLoadingRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class CategoryViewModel extends ViewModel {
    private CategoryRepository mCategoryRepository;
    private List<Category> mCategoryList;
    private List<Product> mProductList=new ArrayList<>();

    private MainLoadingRepository mMainLoadingRepository;

    @Inject
    public CategoryViewModel(CategoryRepository categoryRepository,MainLoadingRepository mainLoadingRepository) {
        mCategoryRepository=categoryRepository;
        mMainLoadingRepository=mainLoadingRepository;

        mProductList.addAll(mCategoryRepository.getProductList());
        mCategoryList=mMainLoadingRepository.getCategoryList();
    }

    public List<Category> getCategoryList() {
        return mCategoryList;
    }

    public List<Product> getProductList() {
        return mProductList;
    }

    public void setProductList(List<Product> productList) {
        mProductList = productList;
    }

    public Observable<List<Product>> requestToServerForSpecificCatProduct(int catId){
        return mCategoryRepository.requestToServerForSpecificCatProduct(catId);
    }
}
