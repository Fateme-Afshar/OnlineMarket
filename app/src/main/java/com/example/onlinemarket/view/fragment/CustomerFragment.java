package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.ViewPagerAdapter;
import com.example.onlinemarket.databinding.FragmentCustmerBinding;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerFragment extends Fragment {
    private FragmentCustmerBinding mBinding;
    private ViewPagerAdapter mAdapter;
    private String[] mState = {"اطلاعات کاربر", "ویرایش اطلاعات"};

    public CustomerFragment() {
        // Required empty public constructor
    }

    public static CustomerFragment newInstance() {
        CustomerFragment fragment = new CustomerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_custmer,
                container,
                false);
        setupAdapter();
        return mBinding.getRoot();
    }

    private void setupAdapter() {
        mAdapter = new ViewPagerAdapter(getActivity());
        mBinding.viewPager.setAdapter(mAdapter);
        new TabLayoutMediator(mBinding.tabLayout, mBinding.viewPager,
                (tab, position) -> tab.setText(mState[position])).
                attach();
    }
}