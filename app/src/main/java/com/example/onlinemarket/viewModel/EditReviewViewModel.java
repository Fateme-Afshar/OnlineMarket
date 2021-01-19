package com.example.onlinemarket.viewModel;

import android.text.Editable;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.model.Review;
import com.example.onlinemarket.repository.ReviewRepository;
import com.example.onlinemarket.utils.ProgramUtils;

public class EditReviewViewModel extends ViewModel {
    private ReviewRepository mReviewRepository;
    private LiveData<Review> mReviewLiveData;

    private Review mReview = new Review();

    private EditReviewViewModelCallback mCallback;

    {
        mReviewRepository = ReviewRepository.getInstance();
    }

    public void afterTextChangeReviewer(Editable editable) {
        mReview.setReviewer(editable.toString());
    }

    public void afterTextChangeReview(Editable editable) {
        mReview.setReview(editable.toString());
    }

    public void afterTextChangeRate(Editable editable) {
        mReview.setRating(Integer.parseInt(editable.toString()));
    }

    public void onEditBtnClickListener(Review review) {
        try {
            mCallback.onEditBtnClickListener(review);
        } catch (NullPointerException nullPointerException) {
            Log.e(ProgramUtils.TAG, " EditReviewViewModel : " + nullPointerException.getMessage());
        }
    }

    public void onCancelBtnClickListener() {
        try {
            mCallback.onCancelBtnClickListener();
        } catch (NullPointerException nullPointerException) {
            Log.e(ProgramUtils.TAG, " EditReviewViewModel : " + nullPointerException.getMessage());
        }
    }

    public void updateReview(int reviewId,Review review) {
        mReviewRepository.updateReview(reviewId,review);
    }

    public Review getReview() {
        return mReview;
    }

    public void setReview(Review review) {
        mReview = review;
    }

    public void setCallback(EditReviewViewModelCallback callback) {
        mCallback = callback;
    }

    public interface EditReviewViewModelCallback {
        void onEditBtnClickListener(Review review);
        void onCancelBtnClickListener();
    }
}
