package com.example.onlinemarket.viewModel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.OnlineShopApplication;
import com.example.onlinemarket.utils.ProgramUtils;

public class AccountManagerViewModel extends ViewModel {
    private AccountManagerViewModelCallback mCallback;

    public void getAccountFragment(){
            try {
                if (OnlineShopApplication.getCustomer() !=null)
                    mCallback.getCustomerFragment();
                else
                    mCallback.getLoginFragment();
            }catch (NullPointerException e){
                Log.e(ProgramUtils.TAG,
                        "AccountManagerViewModel : " +
                                "At first implementation AccountManagerViewModelCallback with setCallback method");
            }
    }

    public void setCallback(AccountManagerViewModelCallback callback) {
        mCallback = callback;
    }

    public interface AccountManagerViewModelCallback{
        void getLoginFragment();
        void getCustomerFragment();
    }
}
