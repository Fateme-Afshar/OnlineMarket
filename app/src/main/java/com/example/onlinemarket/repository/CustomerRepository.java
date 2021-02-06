package com.example.onlinemarket.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.network.retrofit.RetrofitInstance;
import com.example.onlinemarket.network.retrofit.RetrofitInterface;
import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.utils.ProgramUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CustomerRepository {
    private static CustomerRepository sInstance;
    private RetrofitInterface mRetrofitInterface;

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
                Customer customer;
                if (!response.isSuccessful() || response.body().size() == 0){
                    Log.e(ProgramUtils.TAG,
                            "CustomerRepository : can't receive customer from server cause by : one of them : response code: "+
                                    response.code()+"response  body size"+ response.body().size());
                    customer=null;
                }else {
                    Log.d(ProgramUtils.TAG, "CustomerRepository : receive customer from server successfully");
                    customer=response.body().get(0);
                }
                    mCustomerLiveData.postValue(customer);
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Log.e(ProgramUtils.TAG,
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
