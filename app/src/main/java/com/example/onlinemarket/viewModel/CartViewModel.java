package com.example.onlinemarket.viewModel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.OnlineShopApplication;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.ProductPurchasedRepository;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends ViewModel {
    private ProductPurchasedRepository mRepository = OnlineShopApplication.getProductPurchasedRepository();
    private int mTotalPrice = 0;

    private List<Product> mProductList = new ArrayList<>();

    private LifecycleOwner mLifecycleOwner;


    public LiveData<List<Product>> getProductRegisteredForPurchase() {
        return mRepository.getList();
    }

    public List<Product> getProductList() {
        return mProductList;
    }

    public void setProductList(List<Product> productList) {
        mProductList = productList;
    }

    public void setTotalPrice(int totalPrice) {
        mTotalPrice = totalPrice;
    }

    public String getTotalPrice() {
        return String.valueOf(mTotalPrice);
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        mLifecycleOwner = lifecycleOwner;
    }
}
