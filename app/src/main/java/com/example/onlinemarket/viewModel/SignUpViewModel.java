package com.example.onlinemarket.viewModel;

import android.app.Application;
import android.content.Context;
import android.text.Editable;
import android.util.Log;
import android.widget.Toast;

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
import com.example.onlinemarket.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.Map;

//TODO: implementation billing and shipping part
public class SignUpViewModel extends AndroidViewModel {
    private Customer mCustomer = new Customer();

    private CustomerRepository mRepository;

    private LifecycleOwner mLifecycleOwner;

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
        mRepository = OnlineShopApplication.getCustomerRepository();
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
                        Log.d(ProgramUtils.TEST_TAG,
                                "CustomerRepository : Customer post successfully" + integer);
                        OnlineShopSharePref.saveCustomer(context, mCustomer);

                        //TODO: this is anti pattern, using callback or use another way :))
                        MainActivity.start(getApplication());
                    } else {
                        Log.e(ProgramUtils.TEST_TAG,
                                "CustomerRepository : Customer post fail response code is  " + integer);
                        Toast.makeText(context.getApplicationContext(),
                                R.string.repetitive_email,
                                Toast.LENGTH_LONG).
                                show();
                    }
                }
            });

    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        mLifecycleOwner = lifecycleOwner;
    }
}

