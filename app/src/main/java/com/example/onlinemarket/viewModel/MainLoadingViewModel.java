package com.example.onlinemarket.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.model.AttributeInfo;
import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.MainLoadingRepository;

import java.util.List;

import javax.inject.Inject;

public class MainLoadingViewModel{
    private MainLoadingRepository mRepository;

    @Inject
    public MainLoadingViewModel(MainLoadingRepository repository) {
        mRepository = repository;
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

    public void setColorAttributeInfoList(List<AttributeInfo> colorAttributeInfoList) {
        mRepository.setColorAttributeInfoList(colorAttributeInfoList);
    }

    public void setSizeAttributeInfoList(List<AttributeInfo> sizeAttributeInfoList) {
        mRepository.setSizeAttributeInfoList(sizeAttributeInfoList);
    }
}
