package com.example.onlinemarket.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.MainLoadingRepository;

import java.util.List;

public class HomePageViewModel extends ViewModel {
    private MainLoadingRepository mRepository;

    {
        mRepository= new MainLoadingRepository();
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
