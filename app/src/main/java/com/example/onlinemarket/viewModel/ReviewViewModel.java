package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.model.Review;
import com.example.onlinemarket.repository.ReviewRepository;

import java.util.List;

public class ReviewViewModel extends ViewModel {
    private ReviewRepository mReviewRepository;

    private LiveData<List<Review>> mListLiveData;
    private LiveData<Review> mReviewLiveData;

    private ReviewViewModelCallback mCallback;

    public ReviewViewModel() {
        mReviewRepository = ReviewRepository.getInstance();
        mListLiveData = mReviewRepository.getReviewListMutableLiveData();
        mReviewLiveData=mReviewRepository.getReviewMutableLiveData();
    }

    public void requestToReceiveProductReviewList(int productId) {
        mReviewRepository.requestToReceiveProductReviews(productId);
    }

    public void requestToReceiveProductReview(int reviewId){
        mReviewRepository.requestToReceiveProductReview(reviewId);
    }


    public void onDeleteBtnClickListener(int reviewId){
        mCallback.onDeleteReviewClickListener(reviewId);
    }

    public void onEditBtnClickListener(Review review){
        mCallback.onEditBtnClickListener(review);
    }

    public void deleteReview(int reviewId) {
        mReviewRepository.deleteReview(reviewId);
    }

    public LiveData<List<Review>> getListLiveData() {
        return mListLiveData;
    }

    public LiveData<Review> getReviewLiveData() {
        return mReviewLiveData;
    }

    public void setCallback(ReviewViewModelCallback callback) {
        mCallback = callback;
    }

    public interface ReviewViewModelCallback{
        void onDeleteReviewClickListener(int reviewId);
        void onEditBtnClickListener(Review review);
    }
}
