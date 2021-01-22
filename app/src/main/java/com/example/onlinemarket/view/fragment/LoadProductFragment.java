package com.example.onlinemarket.view.fragment;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.example.onlinemarket.databinding.FragmentLoadProductBinding;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.viewModel.ProductViewModel;

public class LoadProductFragment extends Fragment {
    private FragmentLoadProductBinding mBinding;
    private ProductViewModel mViewModel;

    private int mProductId;

    public LoadProductFragment() {
        // Required empty public constructor
    }

    public static LoadProductFragment newInstance() {
        LoadProductFragment fragment = new LoadProductFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductId = LoadProductFragmentArgs.fromBundle(getArguments()).getProductId();
        mViewModel = new ViewModelProvider(getActivity()).get(ProductViewModel.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding= DataBindingUtil.inflate
                (inflater,
                        R.layout.fragment_load_product,
                        container,
                        false);

        setupLoadProduct();
        return mBinding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setupLoadProduct() {
        if (checkHasInternet()) {
            mViewModel.requestToServerForReceiveProductById(mProductId);

            mViewModel.getProductLiveData().observe(getActivity(), new Observer<Product>() {
                @Override
                public void onChanged(Product product) {
                    mViewModel.setProduct(product);

                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    navController.navigate(R.id.nav_product_info);
                }
            });
        }else {
            setupVisibility(View.GONE, View.VISIBLE);

            mBinding.btnRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setupVisibility(View.VISIBLE, View.GONE);

                    setupLoadProduct();
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


    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkHasInternet() {
        ConnectivityManager connectivityManager =
                getActivity().getSystemService(ConnectivityManager.class);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo!=null;
    }
}