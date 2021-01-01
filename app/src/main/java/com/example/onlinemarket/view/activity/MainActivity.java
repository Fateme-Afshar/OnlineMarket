package com.example.onlinemarket.view.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.onlinemarket.R;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.view.IOnBackPress;
import com.example.onlinemarket.view.fragment.CategoriesFragment;
import com.example.onlinemarket.view.fragment.CategoryProductsFragment;
import com.example.onlinemarket.view.fragment.HomePageFragment;
import com.example.onlinemarket.view.fragment.ProductInfoFragment;
import com.example.onlinemarket.view.fragment.SearchFragment;

public class MainActivity extends SingleFragmentActivity
        implements HomePageFragment.HomePageFragmentCallbacks,
        CategoriesFragment.CategoriesFragmentCallbacks,
        CategoryProductsFragment.CategoryProductsFragmentCallback{

    public static final String HOME_PAGE_FRAGMENT_TAG = "HomePageFragment";

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public Fragment getFragment() {
        getSupportFragmentManager().
                beginTransaction().
                add(R.id.fragment_container,
                        HomePageFragment.newInstance(), HOME_PAGE_FRAGMENT_TAG);
        return HomePageFragment.newInstance();
    }

    @Override
    public void onStartCategoryFragment() {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container,
                        CategoriesFragment.newInstance()).
                commit();
    }

    @Override
    public void onItemClickListener(Product product) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container,
                        ProductInfoFragment.newInstance(product)).
                commit();
    }

    @Override
    public void onClickSearchView() {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container,
                        SearchFragment.newInstance()).
                addToBackStack(HOME_PAGE_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onCatSelected(int catId) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container,
                        CategoryProductsFragment.newInstance(catId)).
                commit();
    }

    @Override
    public void onSelectedMoreInfoBtn(Product product) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container,
                        ProductInfoFragment.newInstance(product)).
                commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (!(fragment instanceof IOnBackPress) || !((IOnBackPress) fragment).onBackPressed()) {
            super.onBackPressed();
        }else {
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.fragment_container,
                            HomePageFragment.newInstance()).
                    commit();
        }
    }

}