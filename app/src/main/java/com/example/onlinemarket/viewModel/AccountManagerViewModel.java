package com.example.onlinemarket.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinemarket.sharePref.OnlineShopSharePref;
import com.example.onlinemarket.utils.ProgramUtils;

public class AccountManagerViewModel extends AndroidViewModel {
    private AccountManagerViewModelCallback mCallback;

    public AccountManagerViewModel(@NonNull Application application) {
        super(application);
    }

    public void getAccountFragment(){
            try {
                if (OnlineShopSharePref.getCustomer(getApplication())!=null)
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
