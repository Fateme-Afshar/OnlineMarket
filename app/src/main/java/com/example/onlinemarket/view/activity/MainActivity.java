package com.example.onlinemarket.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.onlinemarket.R;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.view.IOnBackPress;
import com.example.onlinemarket.view.fragment.CategoriesFragment;
import com.example.onlinemarket.view.fragment.CategoryProductsFragment;
import com.example.onlinemarket.view.fragment.HomePageFragment;
import com.example.onlinemarket.view.fragment.ProductInfoFragment;
import com.example.onlinemarket.view.fragment.SearchFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends SingleFragmentActivity
        implements HomePageFragment.HomePageFragmentCallbacks,
        CategoriesFragment.CategoriesFragmentCallbacks,
        CategoryProductsFragment.CategoryProductsFragmentCallback{

    public static final String HOME_PAGE_FRAGMENT_TAG = "HomePageFragment";
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_categories, R.id.nav_search,R.id.nav_filter)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public Fragment getFragment() {
        getSupportFragmentManager().
                beginTransaction().
                add(R.id.nav_host_fragment,
                        HomePageFragment.newInstance(), HOME_PAGE_FRAGMENT_TAG);
        return HomePageFragment.newInstance();
    }

    @Override
    public void onStartCategoryFragment() {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.nav_host_fragment,
                        CategoriesFragment.newInstance()).
                commit();
    }

    @Override
    public void onItemClickListener(Product product) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.nav_host_fragment,
                        ProductInfoFragment.newInstance(product)).
                commit();
    }

    @Override
    public void onClickSearchView() {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.nav_host_fragment,
                        SearchFragment.newInstance()).
                addToBackStack(HOME_PAGE_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void onCatSelected(int catId) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.nav_host_fragment,
                        CategoryProductsFragment.newInstance(catId)).
                commit();
    }

    @Override
    public void onSelectedMoreInfoBtn(Product product) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.nav_host_fragment,
                        ProductInfoFragment.newInstance(product)).
                commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (!(fragment instanceof IOnBackPress) || !((IOnBackPress) fragment).onBackPressed()) {
            super.onBackPressed();
        }else {
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.nav_host_fragment,
                            HomePageFragment.newInstance()).
                    commit();
        }
    }
}