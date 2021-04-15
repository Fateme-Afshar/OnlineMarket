package com.example.onlinemarket.di;

import com.example.onlinemarket.view.fragment.MainLoadingFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component
public interface ApplicationGraph {

    void inject(MainLoadingFragment mainLoadingFragment);
}
