package com.example.onlinemarket.viewModel;

import android.content.Context;
import android.text.Editable;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.R;
import com.example.onlinemarket.di.ContextModule;
import com.example.onlinemarket.model.customer.Billing;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.model.customer.Links;
import com.example.onlinemarket.model.customer.Shipping;
import com.example.onlinemarket.repository.CustomerRepository;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;
import com.example.onlinemarket.utils.ProgramUtils;
import com.example.onlinemarket.utils.UiUtils;

import java.util.ArrayList;

import javax.inject.Inject;

//TODO: implementation billing and shipping part
public class SignUpViewModel extends ViewModel {
    private Customer mCustomer = new Customer();

    private CustomerRepository mRepository;

    private ContextModule mContextModule;

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

    @Inject
    public SignUpViewModel(ContextModule contextModule,CustomerRepository customerRepository) {
        mContextModule=contextModule;
        mRepository =customerRepository;
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
        OnlineShopSharePref.saveCustomer(mContextModule.provideContext().getApplicationContext(), mCustomer);
        Log.d(ProgramUtils.TAG, "SignUpViewModel : save user in share preferences successfully");

        mCustomer.setLinks(links);
        mCustomer.setBilling(billing);
        mCustomer.setShipping(shipping);

        mRepository.postCustomerToServer(mCustomer);

        checkResponse(mContextModule.provideContext().getApplicationContext());
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
                            UiUtils.returnToast(mContextModule.provideContext().getApplicationContext(),"#DEVELOPER : implement SignUpViewModelCallback callback");
                        }
                    } else {
                        Log.e(ProgramUtils.TAG,
                                "SignUpViewModel : Customer post fail response code is  " + integer);
                        UiUtils.returnToast(mContextModule.provideContext().getApplicationContext(),R.string.repetitive_email);
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


