package com.example.onlinemarket.di;

import android.content.Context;

import com.example.onlinemarket.view.fragment.CategoriesFragment;
import com.example.onlinemarket.view.fragment.HomePageFragment;
import com.example.onlinemarket.view.fragment.MainLoadingFragment;
import com.example.onlinemarket.view.fragment.ProductInfoFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class,AppModule.class})
public interface ApplicationGraph {

    ActivityComponent.Factory getActivityComponent();
    LoadingActivityComponent.Factory getLoadingActivityComponent();
}
