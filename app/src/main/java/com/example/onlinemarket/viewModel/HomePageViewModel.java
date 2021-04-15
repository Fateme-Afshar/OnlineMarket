package com.example.onlinemarket.viewModel;

import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.MainLoadingRepository;

import java.util.List;

import javax.inject.Inject;

public class HomePageViewModel {
    private MainLoadingRepository mRepository;

    @Inject
    public HomePageViewModel(MainLoadingRepository repository) {
        mRepository = repository;
    }

    public List<Product> getNewestProductList(){
        return mRepository.getNewestProductList();
    }

    public List<Product> getBestProductList(){
        return mRepository.getBestProductList();
    }

    public List<Product> getPopulateProductList(){
        return mRepository.getPopulateProductList();
    }

    public List<Product> getSpecialProductList(){
        return mRepository.getSpecialProductList();
    }
}
