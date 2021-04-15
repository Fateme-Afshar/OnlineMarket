package com.example.onlinemarket.di;

import com.example.onlinemarket.view.fragment.MainLoadingFragment;

import dagger.Subcomponent;

@Subcomponent
public interface LoadingActivityComponent {

    @Subcomponent.Factory
    interface Factory{
        LoadingActivityComponent create();
    }

    void inject(MainLoadingFragment mainLoadingFragment);
}
