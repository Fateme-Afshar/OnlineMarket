package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.OnlineShopApplication;
import com.example.onlinemarket.di.ContextModule;
import com.example.onlinemarket.model.CustomerLocation;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.repository.CustomerLocationRepository;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;

import java.util.List;

import javax.inject.Inject;

public class CustomerInfoViewModel extends ViewModel {
    private Customer mCustomer;
    private CustomerInfoViewModelCallback mCallback;
    private CustomerLocationRepository mLocationRepository;

    private ContextModule mContextModule;

    @Inject
    public CustomerInfoViewModel(ContextModule contextModule,CustomerLocationRepository customerLocationRepository) {
        mContextModule=contextModule;
        mCustomer= OnlineShopSharePref.getCustomer(mContextModule.provideContext().getApplicationContext());
        mLocationRepository= customerLocationRepository;
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
        OnlineShopSharePref.saveCustomer(mContextModule.provideContext().getApplicationContext(),null);
        mLocationRepository.deleteAll();
        mCallback.onLogoutBtnClickListener();
    }

    public void setCallback(CustomerInfoViewModelCallback callback) {
        mCallback = callback;
    }

    public CustomerLocationRepository getLocationRepository() {
        return mLocationRepository;
    }

    public interface CustomerInfoViewModelCallback{
        void onMapButtonClickListener();
        void onLogoutBtnClickListener();
    }
}
