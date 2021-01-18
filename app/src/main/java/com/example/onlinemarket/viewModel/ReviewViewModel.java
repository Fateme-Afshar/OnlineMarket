package com.example.onlinemarket.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.onlinemarket.model.Review;
import com.example.onlinemarket.repository.ReviewRepository;

import java.util.List;

public class ReviewViewModel extends AndroidViewModel {
    private ReviewRepository mReviewRepository;
    private LiveData<List<Review>> mListLiveData;

    public ReviewViewModel(@NonNull Application application) {
        super(application);
        mReviewRepository=ReviewRepository.getInstance();
        mListLiveData=mReviewRepository.getReviewsMutableLiveData();
    }

    public void requestToReceiveProductReview(int productId){
        mReviewRepository.requestToReceiveProductReview(productId);
    }

    public LiveData<List<Review>> getListLiveData() {
        return mListLiveData;
    }
}
