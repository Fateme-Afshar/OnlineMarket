package com.example.onlinemarket.repository;

import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MainLoadingRepository {
    private static MainLoadingRepository sMainLoadingRepository;

    private MainLoadingRepository() {
    }

    public static MainLoadingRepository getInstance() {
        if (sMainLoadingRepository ==null)
            sMainLoadingRepository =new MainLoadingRepository();
        return sMainLoadingRepository;
    }

   private List<Product> mNewestProductList=new ArrayList<>();
   private List<Product> mPopulateProductList=new ArrayList<>();
   private List<Product> mBestProductList=new ArrayList<>();
   private List<Product> mSpecialProductList=new ArrayList<>();

   private List<Category> mCategoryList=new ArrayList<>();

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

    public List<Category> getCategoryList() {
        return mCategoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        mCategoryList = categoryList;
    }
}
