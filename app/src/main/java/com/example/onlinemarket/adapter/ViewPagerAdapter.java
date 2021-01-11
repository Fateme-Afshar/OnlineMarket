package com.example.onlinemarket.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.onlinemarket.view.fragment.CustomerEditInfoFragment;
import com.example.onlinemarket.view.fragment.CustomerInfoFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position==0)
            return CustomerInfoFragment.newInstance();
        else if (position==1)
            return CustomerEditInfoFragment.newInstance();

        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
