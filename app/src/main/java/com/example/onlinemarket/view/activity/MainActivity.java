package com.example.onlinemarket.view.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.example.onlinemarket.R;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.view.fragment.HomePageFragment;
import com.example.onlinemarket.view.fragment.ProductInfoFragment;

public class MainActivity extends SingleFragmentActivity
        implements HomePageFragment.HomePageFragmentCallbacks {

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public Fragment getFragment() {
        return HomePageFragment.newInstance();
    }

    @Override
    public void onStartCategoryFragment() {

    }

    @Override
    public void onClickBestProduct() {

    }

    @Override
    public void onClickMoreBtn(Product product) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.fragment_container,
                        ProductInfoFragment.newInstance(product)).
                commit();
    }
}