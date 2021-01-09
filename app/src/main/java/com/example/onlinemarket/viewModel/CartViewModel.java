package com.example.onlinemarket.viewModel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.OnlineShopApplication;
import com.example.onlinemarket.R;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.model.orders.LineItemsItem;
import com.example.onlinemarket.model.orders.Orders;
import com.example.onlinemarket.repository.ProductPurchasedRepository;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;
import com.example.onlinemarket.utils.ProgramUtils;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends ViewModel {
    private ProductPurchasedRepository mRepository= OnlineShopApplication.getProductPurchasedRepository();
    private int mTotalPrice=0;

    private List<Product> mProductList=new ArrayList<>();

    private LifecycleOwner mLifecycleOwner;


    public LiveData<List<Product>> getProductRegisteredForPurchase(){
        return mRepository.getList();
    }

    public List<Product> getProductList() {
        return mProductList;
    }

    public void setProductList(List<Product> productList) {
        mProductList = productList;
    }

    public void setTotalPrice(int totalPrice){
        mTotalPrice=totalPrice;
    }

    public String getTotalPrice() {
        return String.valueOf(mTotalPrice);
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        mLifecycleOwner = lifecycleOwner;
    }
}
