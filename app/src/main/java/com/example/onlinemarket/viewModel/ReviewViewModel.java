package com.example.onlinemarket.viewModel;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlinemarket.R;
import com.example.onlinemarket.model.Review;
import com.example.onlinemarket.repository.ReviewRepository;

import java.util.List;

public class ReviewViewModel extends AndroidViewModel {
    private ReviewRepository mReviewRepository;
    private LiveData<List<Review>> mListLiveData;

    private ReviewViewModelCallback mCallback;

    public ReviewViewModel(@NonNull Application application) {
        super(application);
        mReviewRepository = ReviewRepository.getInstance();
        mListLiveData = mReviewRepository.getReviewsMutableLiveData();
    }

    public void requestToReceiveProductReview(int productId) {
        mReviewRepository.requestToReceiveProductReview(productId);
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

    public void setCallback(ReviewViewModelCallback callback) {
        mCallback = callback;
    }

    public interface ReviewViewModelCallback{
        void onDeleteReviewClickListener(int reviewId);
        void onEditBtnClickListener(Review review);
    }
}
