package com.example.onlinemarket.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.utils.Titles;
import com.example.onlinemarket.network.retrofit.RetrofitInstance;
import com.example.onlinemarket.network.retrofit.RetrofitInterface;
import com.example.onlinemarket.network.retrofit.gson.ProductGsonConverterCustomize;
import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.utils.ProgramUtils;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductRepository {
    private static ProductRepository sInstance;

    private MutableLiveData<List<Product>> mNewestProductLiveData =new MutableLiveData<>();
    private MutableLiveData<List<Product>> mPopulateProductLiveData =new MutableLiveData<>();
    private MutableLiveData<List<Product>> mBestProductLiveData=new MutableLiveData<>();
    private MutableLiveData<List<Product>> mSpecialProductLiveData=new MutableLiveData<>();
    private MutableLiveData<List<Product>> mProducts =new MutableLiveData<>();

    private RetrofitInterface mRetrofitInterface;

    private ProductRepository() {
        Retrofit retrofit=RetrofitInstance.getRetrofit
                (new TypeToken<List<Product>>(){}.getType()
                ,new ProductGsonConverterCustomize());
        mRetrofitInterface=retrofit.create(RetrofitInterface.class);
    }

    public static ProductRepository getInstance() {
        if (sInstance == null)
            sInstance = new ProductRepository();
        return sInstance;
    }

    public void requestToServerForReceiveProducts(Map<String,String> queryMap, Titles title) {
        Call<List<Product>> call = getListCall(queryMap);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                switch (title){
                    case BEST_PRODUCT:
                        mBestProductLiveData.setValue(response.body());
                        break;
                    case NEWEST_PRODUCT:
                        mNewestProductLiveData.setValue(response.body());
                        break;
                    case MORE_REVIEWS_PRODUCT:
                        mPopulateProductLiveData.setValue(response.body());
                        break;
                    case SPECIAL_PRODUCT:
                        mSpecialProductLiveData.setValue(response.body());
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    public void requestToServerForReceiveProducts(Map<String,String> queryMap) {
        Call<List<Product>> call = getListCall(queryMap);

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    mProducts.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    public List<Product> requestToServerForSpecificCatProduct(int catId) {
        List<Product> ProductList = new ArrayList<>();

        int page = 1;
        List<Product> products = null;
        try {
            products = getProductObjs(page, catId);

            while (products.size() != 0) {
                ProductList.addAll(products);

                products = getProductObjs(++page, catId);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        // mProductListMutableLiveData.setValue(ProductList);
        return ProductList;
    }

    private  List<Product> getProductObjs(int pageNumber,int catId) throws IOException {
        Log.d(ProgramUtils.TAG, "Start request Server for receive Products every Category");
        List<Product> Products = new ArrayList<>();

        Call<List<Product>> productObjects = mRetrofitInterface.getListProductObjects(NetworkParams.
                queryForReceiveSpecificCategoryProduct(catId, pageNumber));

        Response<List<Product>> response = productObjects.execute();
        Products.addAll(response.body());

        return Products;
    }

    private Call<List<Product>> getListCall(Map<String, String> queryMap) {
        return mRetrofitInterface.getListProductObjects(NetworkParams.queryForReceiveProduct(queryMap));
    }

    public MutableLiveData<List<Product>> getNewestProductLiveData() {
        return mNewestProductLiveData;
    }

    public MutableLiveData<List<Product>> getPopulateProductLiveData() {
        return mPopulateProductLiveData;
    }

    public MutableLiveData<List<Product>> getBestProductLiveData() {
        return mBestProductLiveData;
    }

    public MutableLiveData<List<Product>> getSpecialProductLiveData() {
        return mSpecialProductLiveData;
    }

    public MutableLiveData<List<Product>> getProducts() {
        return mProducts;
    }
}
