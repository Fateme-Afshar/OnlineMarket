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

    private MutableLiveData<List<Review>> mReviewListMutableLiveData =
            new MutableLiveData<>();
    private MutableLiveData<Review> mReviewMutableLiveData =
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

    public void requestToReceiveProductReviews(int productId) {
        Map<String, String> queryParameter = NetworkParams.MAP_KEYS;
        queryParameter.put("product", String.valueOf(productId));

        Call<List<Review>> reviewCall = mRetrofitInterface.getReviews(queryParameter);

        reviewCall.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                Log.d(ProgramUtils.TAG, " ReviewRepository : receive Review successfully");

                List<Review> reviews=new ArrayList<>();

                for (Review review :
                        response.body()) {
                    Review review1=new Review(
                            review.getId(),
                            review.getReview(),
                            review.getProductId(),
                            review.getRating(),
                            review.getReviewer(),
                            review.getReviewerEmail());

                    reviews.add(review1);
                }

                mReviewListMutableLiveData.setValue(reviews);
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {

            }
        });
    }

    public void requestToReceiveProductReview(int reviewId) {
        Call<Review> call =
                mRetrofitInterface.getReview(String.valueOf(reviewId), NetworkParams.MAP_KEYS);

        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                Review review = new Review
                        (response.body().getId(),
                                response.body().getReview(),
                                response.body().getProductId(),
                                response.body().getRating(),
                                response.body().getReviewer(),
                                response.body().getReviewerEmail());

                mReviewMutableLiveData.setValue(review);
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {

            }
        });
    }

    public void deleteReview(int reviewId) {
        Call<Review> reviewCall =
                mRetrofitInterface.deleteReview(String.valueOf(reviewId), NetworkParams.MAP_KEYS);
        reviewCall.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.isSuccessful())
                    Log.d(ProgramUtils.TAG,
                            " ReviewRepository : delete Review successfully");
                else
                    Log.e(ProgramUtils.TAG,
                            " ReviewRepository : delete Review fail response code is "+response.code());
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {

            }
        });

    }

    public void updateReview(int reviewId,Review review){
        Call<Review> reviewCall=
                mRetrofitInterface.updateReview(String.valueOf(reviewId),review,NetworkParams.MAP_KEYS);
        reviewCall.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.isSuccessful())
                    Log.d(ProgramUtils.TAG,
                            " ReviewRepository : update Review successfully   " +response.code());
                else
                    Log.e(ProgramUtils.TAG,
                            " ReviewRepository : update Review fail response code is " + response.code());
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<List<Review>> getReviewListMutableLiveData() {
        return mReviewListMutableLiveData;
    }

    public MutableLiveData<Review> getReviewMutableLiveData() {
        return mReviewMutableLiveData;
    }
}
