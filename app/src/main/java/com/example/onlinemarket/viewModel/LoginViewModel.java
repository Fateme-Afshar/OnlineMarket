package com.example.onlinemarket.viewModel;

import android.app.Application;
import android.text.Editable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.onlinemarket.OnlineShopApplication;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.repository.CustomerRepository;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;

public class LoginViewModel extends AndroidViewModel {
    private Customer mCustomer=new Customer();
    private CustomerRepository mRepository;

    private LifecycleOwner mLifecycleOwner;

    private LoginViewModelCallback mCallback;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mRepository= OnlineShopApplication.getCustomerRepository();
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
                        Toast.makeText(
                                getApplication(),
                                "اطلاعات وارد شده نا معتبر است",
                                Toast.LENGTH_LONG).
                                show();

                        return;
                    }
                    if (mCustomer.getUsername().equals(customer.getUsername())
                            && mCustomer.getEmail().equals(customer.getEmail())){
                        OnlineShopSharePref.saveCustomer(getApplication(),customer);

                        mCallback.onLoginBtnClickListener();

                        Toast.makeText(
                                getApplication(),
                                "تبریک شما با موفقیت login شدید",
                                Toast.LENGTH_LONG).
                                show();
                    }else {
                        Toast.makeText(
                                getApplication(),
                                "اطلاعات وارد شده نا معتبر است",
                                Toast.LENGTH_LONG).
                                show();
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
