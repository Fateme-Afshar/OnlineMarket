package com.example.onlinemarket.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.onlinemarket.OnlineShopApplication;
import com.example.onlinemarket.R;
import com.example.onlinemarket.di.LoadingActivityComponent;
import com.example.onlinemarket.view.fragment.MainLoadingFragment;

public class LoadingActivity extends AppCompatActivity implements
        MainLoadingFragment.MainLoadingFragmentCallback {
    private LoadingActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mActivityComponent=((OnlineShopApplication)getApplication()).
                getApplicationGraph().
                getLoadingActivityComponent().
                create();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Fragment fragment=getSupportFragmentManager().
                findFragmentById(R.id.loading_fragment_container);

        if (fragment==null)
            getSupportFragmentManager().
                    beginTransaction().
                    add(R.id.loading_fragment_container, MainLoadingFragment.newInstance()).
                    commit();
    }

    public LoadingActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    public void onStartMainActivity() {
        MainActivity.start(this);
        finish();
    }
}