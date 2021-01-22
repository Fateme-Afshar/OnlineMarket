package com.example.onlinemarket.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.utils.QueryParameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeViewModel extends ViewModel {
   private List<Product> mNewestProductList=new ArrayList<>();
   private List<Product> mPopulateProductList=new ArrayList<>();
   private List<Product> mBestProductList=new ArrayList<>();
   private List<Product> mSpecialProductList=new ArrayList<>();

    public List<Product> getNewestProductList() {
        return mNewestProductList;
    }

    public void setNewestProductList(List<Product> newestProductList) {
        mNewestProductList = newestProductList;
    }

    public List<Product> getPopulateProductList() {
        return mPopulateProductList;
    }

    public void setPopulateProductList(List<Product> populateProductList) {
        mPopulateProductList = populateProductList;
    }

    public List<Product> getBestProductList() {
        return mBestProductList;
    }

    public void setBestProductList(List<Product> bestProductList) {
        mBestProductList = bestProductList;
    }

    public List<Product> getSpecialProductList() {
        return mSpecialProductList;
    }

    public void setSpecialProductList(List<Product> specialProductList) {
        mSpecialProductList = specialProductList;
    }
}
