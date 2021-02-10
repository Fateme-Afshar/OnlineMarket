package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlinemarket.OnlineShopApplication;
import com.example.onlinemarket.model.CustomerLocation;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.repository.CustomerLocationRepository;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;

import java.util.List;

public class CustomerInfoViewModel extends AndroidViewModel {
    private Customer mCustomer;
    private CustomerInfoViewModelCallback mCallback;
    private CustomerLocationRepository mLocationRepository;

    public CustomerInfoViewModel(@NonNull Application application) {
        super(application);
        mCustomer= OnlineShopSharePref.getCustomer(getApplication());
        mLocationRepository= CustomerLocationRepository.getInstance(getApplication());
    }

    public Customer getCustomer() {
        return mCustomer;
    }

    public LiveData<List<CustomerLocation>> getCustomerLocationList(){
        return mLocationRepository.getList();
    }

    public void onMapButtonClickListener() {
                mCallback.onMapButtonClickListener();
    }

    public void onLogoutBtnClickListener(){
        OnlineShopSharePref.saveCustomer(getApplication(),null);
        mLocationRepository.deleteAll();
        mCallback.onLogoutBtnClickListener();
    }

    public void setCallback(CustomerInfoViewModelCallback callback) {
        mCallback = callback;
    }

    public interface CustomerInfoViewModelCallback{
        void onMapButtonClickListener();
        void onLogoutBtnClickListener();
    }
}
