package com.example.onlinemarket.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.OnlineShopApplication;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.ProductPurchasedRepository;

public class ProductViewModel extends ViewModel {
    private Product mProduct;
    private ProductPurchasedRepository mRepository=OnlineShopApplication.getProductPurchasedRepository();

    public Product getProduct() {
        return mProduct;
    }

    public void setProduct(Product product) {
        mProduct=product;
    }

    public String getNormalText(String text) {
        if (text.length() <= 20)
            return text;
        else
            return text.substring(0, 21)+"...";
    }

    public void onAddCartBtnClickListener(){
        mRepository.insert(mProduct);
    }
}
