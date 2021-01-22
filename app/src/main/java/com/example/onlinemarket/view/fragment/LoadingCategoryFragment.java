package com.example.onlinemarket.view.fragment;

import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.LoadingViewBinding;
import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.utils.LoadingUtils;
import com.example.onlinemarket.viewModel.CategoryViewModel;
import com.example.onlinemarket.viewModel.NetworkTaskViewModel;

import java.util.List;


public class LoadingCategoryFragment extends Fragment {
    private LoadingViewBinding mBinding;
    private CategoryViewModel mViewModel;

    public LoadingCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel=new ViewModelProvider(this).get(CategoryViewModel.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding= DataBindingUtil.inflate
                (inflater,
                        R.layout.loading_view,
                        container,
                        false);
        setupLoadCategory();
        return mBinding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setupLoadCategory() {

        if (LoadingUtils.checkHasInternet(getActivity().getSystemService(ConnectivityManager.class))){
                mViewModel.requestToServerForCategories();

                mViewModel.getCategoryLiveData().observe(getActivity(), new Observer<List<Category>>() {
                    @Override
                    public void onChanged(List<Category> categories) {
                            mViewModel.setCategory(categories);
                    }
                });
        }else {
            setupVisibility(View.GONE, View.VISIBLE);

            mBinding.btnRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setupVisibility(View.VISIBLE, View.GONE);

                    setupLoadCategory();
                }
            });
        }
    }

    private void setupVisibility(int gone, int visible) {
        mBinding.animLoading.setVisibility(gone);

        mBinding.animNoInternet.setVisibility(visible);
        mBinding.tvNoInternet.setVisibility(visible);
        mBinding.btnRefresh.setVisibility(visible);
    }
}