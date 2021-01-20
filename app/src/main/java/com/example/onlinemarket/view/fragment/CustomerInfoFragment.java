package com.example.onlinemarket.view.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.FragmentCustmerBinding;
import com.example.onlinemarket.databinding.FragmentCustomerInfoBinding;
import com.example.onlinemarket.view.OpenProductPage;
import com.example.onlinemarket.viewModel.CustomerInfoViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerInfoFragment extends Fragment {
    private FragmentCustomerInfoBinding mBinding;
    private CustomerInfoFragmentCallback mCallback;

    private CustomerInfoViewModel mViewModel;

    public CustomerInfoFragment() {
        // Required empty public constructor
    }


    public static CustomerInfoFragment newInstance() {
        CustomerInfoFragment fragment = new CustomerInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof CustomerInfoFragmentCallback)
            mCallback=(CustomerInfoFragmentCallback) context;
        else
            throw new ClassCastException(
                    "Must implement CustomerInfoFragmentCallback interface");

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel=new ViewModelProvider(this).get(CustomerInfoViewModel.class);

        mViewModel.setCallback(new CustomerInfoViewModel.CustomerInfoViewModelCallback() {
            @Override
            public void onMapButtonClickListener() {
                mCallback.getMapFragment();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_customer_info,
                container,
                false);

        mBinding.setViewModel(mViewModel);

        return mBinding.getRoot();
    }

    public interface CustomerInfoFragmentCallback{
        void getMapFragment();
    }
}