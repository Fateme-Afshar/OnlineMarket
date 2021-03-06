package com.example.onlinemarket.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.databases.IRepository;
import com.example.onlinemarket.databases.OnlineShopDatabase;
import com.example.onlinemarket.databases.dao.ProductDao;
import com.example.onlinemarket.di.ContextModule;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.model.orders.Orders;
import com.example.onlinemarket.network.retrofit.RetrofitInstance;
import com.example.onlinemarket.network.retrofit.RetrofitInterface;
import com.example.onlinemarket.utils.NetworkParams;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

@Singleton
public class ProductPurchasedRepository implements IRepository<Product> {
    private RetrofitInterface mRetrofitInterface;
    private MutableLiveData<Integer> mResponseCode=new MutableLiveData<>();

    private final ProductDao mDao;

    @Inject
    public ProductPurchasedRepository(ContextModule contextModule) {
        OnlineShopDatabase onlineShopDatabase =
                OnlineShopDatabase.getInstance(contextModule.provideContext().getApplicationContext());

        mDao=onlineShopDatabase.getProductDao();
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
