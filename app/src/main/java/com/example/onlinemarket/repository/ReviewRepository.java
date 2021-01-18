package com.example.onlinemarket.repository;

import android.util.Log;

import com.example.onlinemarket.model.Review;
import com.example.onlinemarket.network.retrofit.RetrofitInstance;
import com.example.onlinemarket.network.retrofit.RetrofitInterface;
import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.utils.ProgramUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReviewRepository {
    private static ReviewRepository sInstance;
    private RetrofitInterface mRetrofitInterface;

    private ReviewRepository() {
        Retrofit retrofit= RetrofitInstance.getPostRetrofit();
        mRetrofitInterface=retrofit.create(RetrofitInterface.class);
    }

    public static ReviewRepository getInstance() {
        if (sInstance==null)
            sInstance=new ReviewRepository();
        return sInstance;
    }

    public void postReviewToServer(Review review){
        Call<Review> reviewCall=
                mRetrofitInterface.postReview(review, NetworkParams.MAP_KEYS);

        reviewCall.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.isSuccessful())
                    Log.d(ProgramUtils.TAG,
                            " ReviewRepository : post Review successfully");
                else
                    Log.e(ProgramUtils.TAG,
                            " ReviewRepository : post Review fail response code is "+response.code());
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {

            }
        });

    }

    public void requestToReceiveProductReview(int productId){

    }
}
