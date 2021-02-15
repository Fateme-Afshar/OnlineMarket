package com.example.onlinemarket.view.fragment;

import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.LoadingViewBinding;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.utils.LoadingUtils;
import com.example.onlinemarket.view.observer.SingleEventObserver;
import com.example.onlinemarket.viewModel.ProductViewModel;

public class LoadingProductInfoFragment extends Fragment {
    private LoadingViewBinding mBinding;
    private ProductViewModel mViewModel;

    private int mProductId;

    public LoadingProductInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(getActivity()).get(ProductViewModel.class);
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
        setupLoadProduct();
        return mBinding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setupLoadProduct() {
        if (LoadingUtils.checkHasInternet(getActivity().getSystemService(ConnectivityManager.class))) {
            mViewModel.requestToServerForReceiveProductById(mProductId);
        }
    }

    private void setupVisibility(int gone, int visible) {
        mBinding.animLoading.setVisibility(gone);

        mBinding.animNoInternet.setVisibility(visible);
        mBinding.tvNoInternet.setVisibility(visible);
        mBinding.btnRefresh.setVisibility(visible);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}