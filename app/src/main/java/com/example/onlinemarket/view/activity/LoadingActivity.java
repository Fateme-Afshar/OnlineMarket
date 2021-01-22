package com.example.onlinemarket.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.onlinemarket.R;
import com.example.onlinemarket.view.fragment.MainLoadingFragment;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
}