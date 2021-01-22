package com.example.onlinemarket.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.HomePageRepository;

import java.util.List;

public class MainLoadingViewModel extends ViewModel {
    private HomePageRepository mRepository;

    {
        mRepository=HomePageRepository.getInstance();
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
}
