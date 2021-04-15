package com.example.onlinemarket.di;

import android.content.Context;

import com.example.onlinemarket.view.fragment.CategoriesFragment;
import com.example.onlinemarket.view.fragment.HomePageFragment;
import com.example.onlinemarket.view.fragment.MainLoadingFragment;
import com.example.onlinemarket.view.fragment.ProductInfoFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class})
public interface ApplicationGraph {
    Context getContext();

    void inject(MainLoadingFragment mainLoadingFragment);
    void inject(HomePageFragment homePageFragment);
    void inject(ProductInfoFragment productInfoFragment);
    void inject(CategoriesFragment categoriesFragment);
}
