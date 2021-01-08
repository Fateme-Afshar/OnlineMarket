package com.example.onlinemarket.viewModel;

import android.app.Application;
import android.text.Editable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;
import com.example.onlinemarket.utils.ProgramUtils;
import com.example.onlinemarket.view.activity.MainActivity;

public class SignUpViewModel extends AndroidViewModel {
    private Customer mCustomer=new Customer();

    public SignUpViewModel(@NonNull Application application) {
        super(application);
    }

    public void afterTextChangeFirstName(Editable editable) {
        mCustomer.setFirstName(editable.toString());
    }

    public void afterTextChangeLastName(Editable editable) {
        mCustomer.setLastName(editable.toString());
    }

    public void afterTextChangeEmail(Editable editable) {
        mCustomer.setEmail(editable.toString());
    }

    public void afterTextChangeUsername(Editable editable) {
        mCustomer.setUsername(editable.toString());
    }

    public void onSignUpBtnClickListener() {
        OnlineShopSharePref.saveCustomer(getApplication(), mCustomer);
        Log.d(ProgramUtils.TAG,"SignUpViewModel : save user in share preferences successfully");
        //TODO: this is anti pattern
        MainActivity.start(getApplication());
    }

}
