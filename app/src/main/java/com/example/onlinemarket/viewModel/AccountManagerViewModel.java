package com.example.onlinemarket.viewModel;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.di.ContextModule;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;
import com.example.onlinemarket.utils.ProgramUtils;

import javax.inject.Inject;

public class AccountManagerViewModel extends ViewModel {
    private AccountManagerViewModelCallback mCallback;
    private final ContextModule mContextModule;

    @Inject
    public AccountManagerViewModel(ContextModule contextModule) {
        mContextModule=contextModule;
    }

    public void getAccountFragment(){
            try {
                if (OnlineShopSharePref.getCustomer(mContextModule.provideContext()) !=null)
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
