package com.example.onlinemarket.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.FragmentSignupPageBinding;
import com.example.onlinemarket.view.IOnBackPress;
import com.example.onlinemarket.viewModel.SignUpViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpPageFragment extends Fragment implements IOnBackPress {
    private FragmentSignupPageBinding mBinding;
    private SignUpViewModel mViewModel;

    public SignUpPageFragment() {
        // Required empty public constructor
    }

    public static SignUpPageFragment newInstance() {
        SignUpPageFragment fragment = new SignUpPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel=new ViewModelProvider(this).get(SignUpViewModel.class);
        mViewModel.setLifecycleOwner(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate
                (inflater,
                        R.layout.fragment_signup_page,
                        container,
                        false);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}