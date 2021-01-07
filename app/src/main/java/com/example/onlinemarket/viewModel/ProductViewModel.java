package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.OnlineShopApplication;
import com.example.onlinemarket.databases.ProductRepository;
import com.example.onlinemarket.model.Product;

public class ProductViewModel extends ViewModel {
    private Product mProduct;

    public Product getProduct() {
        return mProduct;
    }

    public void setProduct(Product product) {
        mProduct = product;
    }

    public String getNormalText(String text) {
        if (text.length() <= 20)
            return text;
        else
            return text.substring(0, 21)+"...";
    }
}
