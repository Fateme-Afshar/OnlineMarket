package com.example.onlinemarket.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.FragmentSignupPageBinding;
import com.example.onlinemarket.view.activity.MainActivity;
import com.example.onlinemarket.viewModel.SignUpViewModel;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpPageFragment extends Fragment{
    private FragmentSignupPageBinding mBinding;
    @Inject
    SignUpViewModel mViewModel;

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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        ((MainActivity)getActivity()).getActivityComponent().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel.setCallback(() -> {
            NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
            navController.navigate(R.id.nav_user_account);
        });
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
}