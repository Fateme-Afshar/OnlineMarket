package com.example.onlinemarket.repository;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.R;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.network.retrofit.RetrofitInstance;
import com.example.onlinemarket.network.retrofit.RetrofitInterface;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;
import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.utils.ProgramUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CustomerRepository {
    private static CustomerRepository sInstance;
    private RetrofitInterface mRetrofitInterface;

    private Response<Customer> mResponse;
    private final MutableLiveData<Integer> mResponseCode=new MutableLiveData<>();

    private MutableLiveData<Customer> mCustomerLiveData=new MutableLiveData<>();

    private CustomerRepository() {
        Retrofit retrofit = RetrofitInstance.getPostRetrofit();
        mRetrofitInterface = retrofit.create(RetrofitInterface.class);
    }

    public static CustomerRepository getInstance() {
        if (sInstance == null)
            sInstance = new CustomerRepository();
        return sInstance;
    }
    // important note to post customer:Email must be unique
    public void postCustomerToServer(Customer customer) {

        /*        RequestBody body =
        RequestBody.create( MediaType.parse("application/json"),
        ProgramUtils.customerTesting().toString());*/
        mRetrofitInterface.postCustomer(customer, NetworkParams.MAP_KEYS).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                mResponseCode.setValue(response.code());
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {

            }
        });
    }

    public void requestToFindCustomer(String email){
        Call<List<Customer>> customerCall=
                mRetrofitInterface.getCustomer(NetworkParams.queryForFindCustomer(email));

        customerCall.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                Log.d(ProgramUtils.TEST_TAG,"CustomerRepository : receive customer from server ");
                mCustomerLiveData.postValue(response.body().get(0));
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Log.e(ProgramUtils.TEST_TAG,
                        "CustomerRepository : can't receive customer from server cause by : "+ t.getMessage());
            }
        });
    }

    public MutableLiveData<Customer> getCustomerLiveData() {
        return mCustomerLiveData;
    }

    public MutableLiveData<Integer> getResponseCode() {
        return mResponseCode;
    }
}
