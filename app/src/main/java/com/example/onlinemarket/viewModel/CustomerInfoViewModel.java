package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;

public class CustomerInfoViewModel extends AndroidViewModel {
    private Customer mCustomer;
    private CustomerInfoViewModelCallback mCallback;

    public CustomerInfoViewModel(@NonNull Application application) {
        super(application);
        mCustomer= OnlineShopSharePref.getCustomer(getApplication());
    }

    public Customer getCustomer() {
        return mCustomer;
    }

    public void onMapButtonClickListener() {
                mCallback.onMapButtonClickListener();
    }

    public void setCallback(CustomerInfoViewModelCallback callback) {
        mCallback = callback;
    }

    public interface CustomerInfoViewModelCallback{
        void onMapButtonClickListener();
    }
}
