package com.example.onlinemarket.view.observers;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public abstract class SingleLiveData<T> implements Observer<T> {
    private LiveData<T> mLiveData;
    private Lifecycle mLifecycleOwner;

    public SingleLiveData(LiveData<T> liveData, Lifecycle lifecycleOwner) {
        mLiveData = liveData;
        mLifecycleOwner = lifecycleOwner;
    }
}
