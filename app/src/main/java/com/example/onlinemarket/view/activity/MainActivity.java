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
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.onlinemarket.R;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.view.OpenProductPage;
import com.example.onlinemarket.view.fragment.AccountFragment;
import com.example.onlinemarket.view.fragment.CartFragment;
import com.example.onlinemarket.view.fragment.CategoriesFragment;
import com.example.onlinemarket.view.fragment.CustomerFragment;
import com.example.onlinemarket.view.fragment.CustomerInfoFragment;
import com.example.onlinemarket.view.fragment.HomePageFragment;
import com.example.onlinemarket.view.fragment.HomePageFragmentDirections;
import com.example.onlinemarket.view.fragment.MapFragment;
import com.example.onlinemarket.view.fragment.SearchFragment;
import com.example.onlinemarket.view.fragment.SignUpPageFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends SingleFragmentActivity
        implements CategoriesFragment.CategoriesFragmentCallbacks,
        OpenProductPage, AccountFragment.AccountFragmentCallback,
        CustomerInfoFragment.CustomerInfoFragmentCallback {

    public static final String HOME_PAGE_FRAGMENT_TAG = "HomePageFragment";
    public static final String ARG_PRODUCT_INFO = "ProductInfo";
    public static final String ARG_CATEGORY_ID = "categoryId";
    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        mAppBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph())
                .setDrawerLayout(drawer)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    public static Intent newIntent(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return starter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_basket:
                getSupportFragmentManager().
                        beginTransaction().
                        add(R.id.nav_host_fragment,
                                CartFragment.newInstance()).
                        commit();
                return true;
            case R.id.menu_search:
                getSupportFragmentManager().
                        beginTransaction().
                        add(R.id.nav_host_fragment,
                                SearchFragment.newInstance()).
                        commit();
                return true;
            default:
                return false;
        }
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
    public void onItemClickListener(Product product) {
        HomePageFragmentDirections.ActionNavHomeToNavProductInfo actionNavHomeToNavProductInfo=
                HomePageFragmentDirections.actionNavHomeToNavProductInfo(product);

        actionNavHomeToNavProductInfo.setProductId(product);
        navController.navigate(actionNavHomeToNavProductInfo);
    }

    @Override
    public void onCatSelected(int catId) {
        Bundle bundle=new Bundle();
        bundle.putInt(ARG_CATEGORY_ID,catId);
        navController.navigate(R.id.nav_category_product,bundle);
    }

    @Override
    public void getSignUpFragment() {
        getSupportFragmentManager().
                beginTransaction().
                add(R.id.nav_host_fragment,
                        SignUpPageFragment.newInstance()).
                commit();
    }

    @Override
    public void getCustomerFragment() {
        getSupportFragmentManager().
                beginTransaction().
                add(R.id.nav_host_fragment,
                        CustomerFragment.newInstance()).
                commit();
    }

    @Override
    public void getMapFragment() {
        getSupportFragmentManager().
                beginTransaction().
                add(R.id.map_fragment_container,
                        MapFragment.newInstance()).
                commit();
    }
}