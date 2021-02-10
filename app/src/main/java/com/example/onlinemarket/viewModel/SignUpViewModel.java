package com.example.onlinemarket.viewModel;

import android.app.Application;
import android.content.Context;
import android.text.Editable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.onlinemarket.OnlineShopApplication;
import com.example.onlinemarket.R;
import com.example.onlinemarket.model.customer.Billing;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.model.customer.Links;
import com.example.onlinemarket.model.customer.Shipping;
import com.example.onlinemarket.repository.CustomerRepository;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;
import com.example.onlinemarket.utils.ProgramUtils;

import java.util.ArrayList;

//TODO: implementation billing and shipping part
public class SignUpViewModel extends AndroidViewModel {
    private Customer mCustomer = new Customer();

    private CustomerRepository mRepository;

    private LifecycleOwner mLifecycleOwner;

    private SignUpViewModelCallback mCallback;

    private Links links = new Links(new ArrayList<>(),
            new ArrayList<>());
    private Billing billing = new Billing(
            "Iran",
            "Zanjan",
            "9034213778",
            "",
            "",
            "",
            "Afshar",
            "utab",
            "",
            "Fateme",
            "utab2019@yahoo.com");
    private Shipping shipping = new Shipping(
            "Turkey",
            "Izmir",
            "", "",
            "",
            "Ana",
            "Utab",
            "",
            "Aysan");

    public SignUpViewModel(@NonNull Application application) {
        super(application);
        mRepository = CustomerRepository.getInstance();
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
        Log.d(ProgramUtils.TAG, "SignUpViewModel : save user in share preferences successfully");

        mCustomer.setLinks(links);
        mCustomer.setBilling(billing);
        mCustomer.setShipping(shipping);

        mRepository.postCustomerToServer(mCustomer);

        checkResponse(getApplication());
    }

    public void checkResponse(Context context) {
            mRepository.getResponseCode().observe(mLifecycleOwner, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if (integer==201) {
                        Log.d(ProgramUtils.TAG,
                                "SignUpViewModel : Customer post successfully" + integer);
                        OnlineShopSharePref.saveCustomer(context, mCustomer);

                        try {
                            mCallback.startHomePage();
                        }catch (NullPointerException exception){
                            OnlineShopApplication.getUiUtils().returnToast("#DEVELOPER : implement SignUpViewModelCallback callback");
                        }
                    } else {
                        Log.e(ProgramUtils.TAG,
                                "SignUpViewModel : Customer post fail response code is  " + integer);
                        OnlineShopApplication.getUiUtils().returnToast(R.string.repetitive_email);
                    }
                }
            });

    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        mLifecycleOwner = lifecycleOwner;
    }

    public void setCallback(SignUpViewModelCallback callback) {
        mCallback = callback;
    }

    public interface SignUpViewModelCallback{
        void startHomePage();
    }
}


