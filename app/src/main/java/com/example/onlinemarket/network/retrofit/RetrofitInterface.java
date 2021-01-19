package com.example.onlinemarket.network.retrofit;

import androidx.room.Update;

import com.example.onlinemarket.model.AttributeInfo;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.model.Review;
import com.example.onlinemarket.model.coupons.Coupons;
import com.example.onlinemarket.model.customer.Customer;
import com.example.onlinemarket.model.orders.Orders;
import com.example.onlinemarket.model.response.CatObj;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface RetrofitInterface {

    @GET("products/{path}")
    Call<List<CatObj>> getListCatObjects(@Path("path") String path, @QueryMap Map<String, String> queryMap);

    @GET("products")
    Call<List<Product>> getListProductObjects(@QueryMap Map<String, String> queryMap);

    //for receive filter sections such as color,size
    @GET("products/attributes")
    Call<List<AttributeInfo>> getAttributes(@QueryMap Map<String, String> queryMap);

    //for receive every filter part attribute for example in size:32,33,45,50,...
    @GET("products/attributes/{id}/terms")
    Call<List<AttributeInfo>> getEveryAttributePart(@Path("id") String path,
                                                    @QueryMap Map<String, String> queryMap);

    @GET("customers")
    Call<Customer> getCustomer(@QueryMap Map<String, String> queryMap);

    @POST("customers")
    Call<Customer> postCustomer(@Body Customer customer,
                                @QueryMap Map<String, String> queryMap);

    @POST("orders")
    Call<Orders> postOrders(@Body Orders orders,
                              @QueryMap Map<String, String> queryMap);

    @POST("products/reviews")
    Call<Review> postReview(@Body Review review,
                            @QueryMap Map<String, String> queryMap);

    @GET("products/reviews")
    Call<List<Review>> getReview(@QueryMap Map<String, String> queryMap);

    @DELETE("products/reviews/{id}")
    Call<Review> deleteReview(@Path("id") String reviewId,@QueryMap Map<String, String> queryMap);

    @PUT("products/reviews/{id}")
    Call<Review> updateReview(@Path("id") String reviewId,@Body Review review, @QueryMap Map<String, String> queryMap);

    @GET("coupons")
    Call<Coupons> getCoupons(@QueryMap  Map<String, String> queryMap);
}
