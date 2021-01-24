package com.example.onlinemarket.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.MainLoadingRepository;

import java.util.List;

public class MainLoadingViewModel extends ViewModel {
    private MainLoadingRepository mRepository;

    {
        mRepository= MainLoadingRepository.getInstance();
    }


    public void setNewestProductList(List<Product> newestProductList) {
       mRepository.setNewestProductList(newestProductList);
    }

    public void setPopulateProductList(List<Product> populateProductList) {
        mRepository.setPopulateProductList(populateProductList);
    }

    public void setBestProductList(List<Product> bestProductList) {
        mRepository.setBestProductList(bestProductList);
    }

    public void setSpecialProductList(List<Product> specialProductList) {
        mRepository.setSpecialProductList(specialProductList);
    }

    public void setCategories(List<Category> categories){
        mRepository.setCategoryList(categories);
    }
}
