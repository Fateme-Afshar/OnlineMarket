package com.example.onlinemarket.network.retrofit;

import com.example.onlinemarket.model.AttributeInfo;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.model.Review;
import com.example.onlinemarket.model.Coupons;
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

    @GET("products/categories?per_page=100")
    Call<List<CatObj>> getListCatObjects(@QueryMap Map<String, String> queryMap);

    @GET("products?per_page=100")
    Call<List<Product>> getListProductObjects(@QueryMap Map<String, String> queryMap);

    @GET("products/{id}")
    Call<Product> getProduct(@Path("id") int id,@QueryMap Map<String, String> queryMap);

    //for receive filter sections such as color,size
    @GET("products/attributes")
    Call<List<AttributeInfo>> getAttributes(@QueryMap Map<String, String> queryMap);

    //for receive every filter part attribute for example in size:32,33,45,50,...
    @GET("products/attributes/{id}/terms")
    Call<List<AttributeInfo>> getEveryAttributePart(@Path("id") String path,
                                                    @QueryMap Map<String, String> queryMap);

    @GET("customers")
    Call<List<Customer>> getCustomer(@QueryMap Map<String, String> queryMap);

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
    Call<List<Review>> getReviews(@QueryMap Map<String, String> queryMap);

    @GET("products/reviews/{id}")
    Call<Review> getReview(
            @Path("id") String id, @QueryMap Map<String, String> queryMap);

    @DELETE("products/reviews/{id}")
    Call<Review> deleteReview(@Path("id") String reviewId,@QueryMap Map<String, String> queryMap);

    @PUT("products/reviews/{id}")
    Call<Review> updateReview(
            @Path("id") String reviewId, @Body Review review, @QueryMap Map<String, String> queryMap);

    // at first when we want test this method , this error occur ->BEGIN_OBJECT but was BEGIN_ARRAY - > solution : we must receive coupons into list.
    @GET("coupons")
    Call<List<Coupons>> getCoupons(@QueryMap  Map<String, String> queryMap);
}
