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
import com.example.onlinemarket.view.OpenProductPage;
import com.example.onlinemarket.view.fragment.CartFragment;
import com.example.onlinemarket.view.fragment.CategoriesFragment;
import com.example.onlinemarket.view.fragment.CategoryProductsFragment;
import com.example.onlinemarket.view.fragment.HomePageFragment;
import com.example.onlinemarket.view.fragment.ProductInfoFragment;
import com.example.onlinemarket.view.fragment.SearchFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends SingleFragmentActivity
        implements CategoriesFragment.CategoriesFragmentCallbacks, OpenProductPage {

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
                R.id.nav_home, R.id.nav_categories, R.id.nav_search,R.id.nav_filter,R.id.nav_user_account)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
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
        getSupportFragmentManager().
                beginTransaction().
                add(R.id.nav_host_fragment,
                        ProductInfoFragment.newInstance(product)).
                commit();
    }

    @Override
    public void onCatSelected(int catId) {
        getSupportFragmentManager().
                beginTransaction().
                add(R.id.nav_host_fragment,
                        CategoryProductsFragment.newInstance(catId)).
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
                    add(R.id.nav_host_fragment,
                            HomePageFragment.newInstance()).
                    commit();
        }
    }
}