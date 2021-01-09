package com.example.onlinemarket.viewModel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

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

public class CartViewModel extends AndroidViewModel {
    private ProductPurchasedRepository mRepository= OnlineShopApplication.getProductPurchasedRepository();
    private int mTotalPrice=0;

    private List<Product> mProductList=new ArrayList<>();

    private LifecycleOwner mLifecycleOwner;

    private Orders mOrders;
    private Customer mCustomer;

    public CartViewModel(@NonNull Application application) {
        super(application);
        mCustomer=OnlineShopSharePref.getCustomer(getApplication());
    }

    public LiveData<List<Product>> getProductRegisteredForPurchase(){
        return mRepository.getList();
    }

    public void onBuyBtnClickListener(){
        List<LineItemsItem> productLines=new ArrayList<>();

        for (Product product : mProductList) {
            LineItemsItem lineItemsItem=new LineItemsItem(
                    String.valueOf(product.getRegularPrice()),
                    (int) product.getPrice(),
                    product.getId(),
                    product.getName());

            productLines.add(lineItemsItem);
        }
        mOrders=new Orders(String.valueOf(mTotalPrice),mCustomer.getId(),productLines,mCustomer.getUsername());

        postOrdersToServer(mOrders);
    }

    private void postOrdersToServer(Orders orders) {
        mRepository.postOrdersToServer(orders);
        mRepository.getResponseCode().observe(mLifecycleOwner, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer==201) {
                    Log.d(ProgramUtils.TAG,
                            "CartViewModel : Orders post successfully" + integer);
                    Toast.makeText(getApplication(),
                            R.string.order_success,
                            Toast.LENGTH_LONG).
                            show();
                } else {
                    Log.e(ProgramUtils.TAG,
                            "CartViewModel : Orders post fail response code is  " + integer);
                    Toast.makeText(getApplication(),
                            R.string.order_fail,
                            Toast.LENGTH_LONG).
                            show();
                }
            }
        });
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
