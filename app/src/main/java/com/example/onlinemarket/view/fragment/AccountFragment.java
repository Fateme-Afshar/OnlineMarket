package com.example.onlinemarket.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.FragmentAccountBinding;
import com.example.onlinemarket.viewModel.AccountManagerViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment{
    private FragmentAccountBinding mBinding;
    private AccountManagerViewModel mViewModel;

    private AccountFragmentCallback mCallback;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AccountFragmentCallback)
            mCallback=(AccountFragmentCallback) context;
        else
            throw new ClassCastException(
                    "Must implement AccountFragmentCallback interface");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel=new ViewModelProvider(this).get(AccountManagerViewModel.class);
        mViewModel.setCallback(new AccountManagerViewModel.AccountManagerViewModelCallback() {
            @Override
            public void getLoginFragment() {
                mCallback.getLoginFragment();
            }

            @Override
            public void getCustomerFragment() {
                mCallback.getCustomerFragment();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_account,
                container,
                false);
        mViewModel.getAccountFragment();
        return mBinding.getRoot();
    }

    public interface AccountFragmentCallback{
        void getLoginFragment();
        void getCustomerFragment();
    }
}