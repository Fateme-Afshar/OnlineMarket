package com.example.onlinemarket.network.retrofit;

import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.model.response.CatObj;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface RetrofitInterface {

    @GET("products/{path}")
    Call<List<CatObj>> getListCatObjects(@Path("path") String path,@QueryMap Map<String,String> queryMap);

    @GET("products")
    Call<List<Product>> getListProductObjects(@QueryMap Map<String,String> queryMap);
}
