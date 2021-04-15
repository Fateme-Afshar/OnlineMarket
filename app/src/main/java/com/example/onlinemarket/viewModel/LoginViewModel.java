package com.example.onlinemarket.viewModel;

import android.app.Application;
import android.text.Editable;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.OnlineShopApplication;
import com.example.onlinemarket.di.ContextModule;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.repository.CustomerRepository;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;
import com.example.onlinemarket.utils.UiUtils;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {
    private Customer mCustomer=new Customer();
    private CustomerRepository mRepository;

    private ContextModule mContextModule;

    private LifecycleOwner mLifecycleOwner;

    private LoginViewModelCallback mCallback;

    @Inject
    public LoginViewModel(ContextModule contextModule,CustomerRepository customerRepository) {
        mRepository= customerRepository;
        mContextModule=contextModule;
    }

    public void afterTextChangeUsername(Editable editable) {
        mCustomer.setUsername(editable.toString());
    }

    public void afterTextChangeEmail(Editable editable) {
        mCustomer.setEmail(editable.toString());
    }

    public void onSignUpBtnClickListener() {
        mCallback.onSignUpBtnClickListener();
    }

    public void onLoginBtnClickListener() {
            mRepository.requestToFindCustomer(mCustomer.getEmail());

            mRepository.getCustomerLiveData().observe(mLifecycleOwner,new Observer<Customer>() {
                @Override
                public void onChanged(Customer customer) {
                    if (customer==null) {
                        UiUtils.returnToast(mContextModule.provideContext().getApplicationContext(),"اطلاعات وارد شده نا معتبر است");
                        return;
                    }
                    if (mCustomer.getUsername().equals(customer.getUsername())
                            && mCustomer.getEmail().equals(customer.getEmail())){
                        OnlineShopSharePref.saveCustomer(mContextModule.provideContext().getApplicationContext(),customer);

                        mCallback.onLoginBtnClickListener();

                        UiUtils.returnToast(mContextModule.provideContext().getApplicationContext(),"تبریک شما با موفقیت login شدید");
                    }else {
                        UiUtils.returnToast(mContextModule.provideContext().getApplicationContext(),"اطلاعات وارد شده نا معتبر است");
                    }
                }
            });
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        mLifecycleOwner = lifecycleOwner;
    }

    public void setCallback(LoginViewModelCallback callback) {
        mCallback = callback;
    }

    public interface LoginViewModelCallback{
        void onLoginBtnClickListener();
        void onSignUpBtnClickListener();
    }
}
