package com.example.onlinemarket.di;

import com.example.onlinemarket.view.fragment.CategoriesFragment;
import com.example.onlinemarket.view.fragment.CategoryProductsFragment;
import com.example.onlinemarket.view.fragment.HomePageFragment;
import com.example.onlinemarket.view.fragment.MainLoadingFragment;
import com.example.onlinemarket.view.fragment.ProductInfoFragment;
import com.example.onlinemarket.view.fragment.SearchFragment;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent
public interface ActivityComponent {

    @Subcomponent.Factory
    interface Factory{
        ActivityComponent create();
    }

    void inject(HomePageFragment homePageFragment);
    void inject(ProductInfoFragment productInfoFragment);
    void inject(CategoriesFragment categoriesFragment);
    void inject(CategoryProductsFragment categoryProductsFragment);
    void inject(SearchFragment searchFragment);
}
