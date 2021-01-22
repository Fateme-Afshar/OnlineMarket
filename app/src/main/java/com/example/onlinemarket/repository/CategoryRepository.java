package com.example.onlinemarket.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.model.response.CatObj;
import com.example.onlinemarket.network.retrofit.RetrofitInstance;
import com.example.onlinemarket.network.retrofit.RetrofitInterface;
import com.example.onlinemarket.network.retrofit.gson.ProductListGsonConverterCustomize;
import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.utils.ProgramUtils;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoryRepository {
    private static CategoryRepository sInstance;
    private RetrofitInterface mRetrofitInterface;

    private MutableLiveData<List<Category>> mCategoryLiveData=new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProducts =new MutableLiveData<>();

    private CategoryRepository() {
        Retrofit retrofit=RetrofitInstance.getRetrofit();

        mRetrofitInterface=retrofit.create(RetrofitInterface.class);
    }

    public static CategoryRepository getInstance() {
        if (sInstance==null)
            sInstance=new CategoryRepository();
        return sInstance;
    }

    public void requestToServerForCategories() {
        List<Category> categoriesList = new ArrayList<>();
        Retrofit retrofit =RetrofitInstance.getRetrofit();

        mRetrofitInterface = retrofit.create(RetrofitInterface.class);

        Call<List<CatObj>> catObjects =
                mRetrofitInterface.getListCatObjects("categories",NetworkParams.MAP_KEYS);
        catObjects.enqueue(new Callback<List<CatObj>>() {
            @Override
            public void onResponse(Call<List<CatObj>> call, Response<List<CatObj>> response) {
                    if (response.body()!=null){
                        for (CatObj catObj : response.body()) {
                                Category category=new Category
                                        (catObj.getId(),
                                                catObj.getName(),
                                                catObj.getImage().getSrc());

                                categoriesList.add(category);
                        }

                        mCategoryLiveData.setValue(categoriesList);
                    }
            }

            @Override
            public void onFailure(Call<List<CatObj>> call, Throwable t) {

            }
        });
    }

    public void requestToServerForSpecificCatProduct(int catId) {
        Retrofit retrofit =RetrofitInstance.getRetrofit(new TypeToken<List<Product>>(){}.getType()
                ,new ProductListGsonConverterCustomize());

        mRetrofitInterface = retrofit.create(RetrofitInterface.class);
        Call<List<Product>> productObjects=
                mRetrofitInterface.getListProductObjects
                        (NetworkParams.queryForReceiveSpecificCategoryProduct(catId));

        productObjects.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Log.d(ProgramUtils.TAG,
                        "ProductRepository : request Server for receive products for Category by id = "+catId);

                mProducts.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

    }

    public MutableLiveData<List<Product>> getProducts() {
        return mProducts;
    }

    public MutableLiveData<List<Category>> getCategoryLiveData() {
        return mCategoryLiveData;
    }
}
