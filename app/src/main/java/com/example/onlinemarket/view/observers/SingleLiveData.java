package com.example.onlinemarket.view.observers;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public abstract class SingleLiveData<T> implements Observer<T> {
    private LiveData<T> mLiveData;
    private LifecycleOwner mLifecycleOwner;

    public SingleLiveData(LiveData<T> liveData, LifecycleOwner lifecycleOwner) {
        mLiveData = liveData;
        mLifecycleOwner = lifecycleOwner;
    }

    @Override
    public void onChanged(T t) {
        mLiveData.removeObservers(mLifecycleOwner);
    }
}
