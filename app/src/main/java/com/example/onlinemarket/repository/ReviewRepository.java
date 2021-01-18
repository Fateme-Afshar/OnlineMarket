package com.example.onlinemarket.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.onlinemarket.model.Review;
import com.example.onlinemarket.network.retrofit.RetrofitInstance;
import com.example.onlinemarket.network.retrofit.RetrofitInterface;
import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.utils.ProgramUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReviewRepository {
    private static ReviewRepository sInstance;
    private RetrofitInterface mRetrofitInterface;

    private MutableLiveData<List<Review>> mReviewsMutableLiveData =
            new MutableLiveData<>();

    private ReviewRepository() {
        Retrofit retrofit = RetrofitInstance.getPostRetrofit();
        mRetrofitInterface = retrofit.create(RetrofitInterface.class);
    }

    public static ReviewRepository getInstance() {
        if (sInstance == null)
            sInstance = new ReviewRepository();
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

    public void requestToReceiveProductReview(int productId) {
        Map<String, String> queryParameter = NetworkParams.MAP_KEYS;
        queryParameter.put("product", String.valueOf(productId));

        Call<List<Review>> reviewCall=mRetrofitInterface.getReview(queryParameter);

        reviewCall.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                Log.d(ProgramUtils.TAG, " ReviewRepository : receive Review successfully");

                List<Review> reviews=new ArrayList<>();

                for (Review review :
                        response.body()) {
                    Review review1=new Review(
                            review.getReview(),
                            review.getProductId(),
                            review.getRating(),
                            review.getReviewer(),
                            review.getReviewerEmail());

                    reviews.add(review1);
                }

                mReviewsMutableLiveData.setValue(reviews);
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<List<Review>> getReviewsMutableLiveData() {
        return mReviewsMutableLiveData;
    }
}
