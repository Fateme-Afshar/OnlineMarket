package com.example.onlinemarket.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.databases.IRepository;
import com.example.onlinemarket.databases.OnlineShopDatabase;
import com.example.onlinemarket.databases.dao.ProductDao;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.model.orders.Orders;
import com.example.onlinemarket.network.retrofit.RetrofitInstance;
import com.example.onlinemarket.network.retrofit.RetrofitInterface;
import com.example.onlinemarket.utils.NetworkParams;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductPurchasedRepository implements IRepository<Product> {
    private static ProductPurchasedRepository sInstance;

    private RetrofitInterface mRetrofitInterface;
    private MutableLiveData<Integer> mResponseCode=new MutableLiveData<>();

    private final ProductDao mDao;

    private ProductPurchasedRepository(Context context) {
        OnlineShopDatabase onlineShopDatabase =
                OnlineShopDatabase.getInstance(context.getApplicationContext());

        mDao=onlineShopDatabase.getProductDao();
    }

    public static ProductPurchasedRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new ProductPurchasedRepository(context.getApplicationContext());
        return sInstance;
    }

    public void postOrdersToServer(Orders orders){
        Retrofit retrofit= RetrofitInstance.getRetrofit();
        mRetrofitInterface=retrofit.create(RetrofitInterface.class);

        mRetrofitInterface.postOrders(orders, NetworkParams.MAP_KEYS).enqueue(new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                    mResponseCode.setValue(response.code());
            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {

            }
        });
    }

    @Override
    public LiveData<Product> get(int id) {
        return mDao.get(id);
    }

    @Override
    public LiveData<List<Product>> getList() {
        return mDao.getList();
    }

    @Override
    public void insert(Product product) {
        OnlineShopDatabase.databaseWriteExecutor.execute(()->mDao.insert(product));
    }

    @Override
    public void delete(Product product) {
        OnlineShopDatabase.databaseWriteExecutor.execute(()->mDao.delete(product));
    }

    @Override
    public void update(Product product) {
        OnlineShopDatabase.databaseWriteExecutor.execute(()->mDao.update(product));
    }

    public void deleteAll(){
        OnlineShopDatabase.databaseWriteExecutor.execute(mDao::deleteAll);
    }

    public MutableLiveData<Integer> getResponseCode() {
        return mResponseCode;
    }
}
