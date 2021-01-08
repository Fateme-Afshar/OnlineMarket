package com.example.onlinemarket.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.OnlineShopApplication;
import com.example.onlinemarket.databases.ProductRepository;
import com.example.onlinemarket.model.Product;

import java.util.List;

public class CartViewModel extends ViewModel {
    private ProductRepository mRepository= OnlineShopApplication.getRepository();
    private int mTotalPrice=0;

    public LiveData<List<Product>> getProductRegisteredForPurchase(){
        return mRepository.getList();
    }

    public void setTotalPrice(int totalPrice){
        mTotalPrice=totalPrice;
    }

    public String getTotalPrice() {
        return String.valueOf(mTotalPrice);
    }
}
