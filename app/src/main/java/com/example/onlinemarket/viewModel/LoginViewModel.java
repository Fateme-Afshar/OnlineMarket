package com.example.onlinemarket.viewModel;

import android.app.Application;
import android.text.Editable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;

import com.example.onlinemarket.OnlineShopApplication;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.repository.CustomerRepository;

public class LoginViewModel extends AndroidViewModel {
    private Customer mCustomer=new Customer();
    private CustomerRepository mRepository;

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

    }

    public void onLoginBtnClickListener() {
            mRepository.requestToFindCustomer(mCustomer.getEmail());

            mRepository.getCustomerLiveData().observeForever(new Observer<Customer>() {
                @Override
                public void onChanged(Customer customer) {
                    if (mCustomer.getUsername().equals(customer.getUsername())){
                        //TODO: get home page
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
}
