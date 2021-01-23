package com.example.onlinemarket.view.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.ProductAdapter;
import com.example.onlinemarket.databinding.FragmentHomePageBinding;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.services.PollWorkManager;
import com.example.onlinemarket.view.OpenProductPage;
import com.example.onlinemarket.view.slider.ImageSlider;
import com.example.onlinemarket.repository.HomePageRepository;
import com.example.onlinemarket.viewModel.HomePageViewModel;
import com.example.onlinemarket.viewModel.NetworkTaskViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFragment extends Fragment{
    private FragmentHomePageBinding mBinding;

    private NetworkTaskViewModel mNetworkTaskViewModel;
    private HomePageViewModel mHomePageViewModel;

    private ImageSlider mImageSlider;

    public HomePageFragment() {
        // Required empty public constructor
    }

    public static HomePageFragment newInstance() {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PollWorkManager.enqueue(getContext(),1,false);
        mNetworkTaskViewModel = new ViewModelProvider(this).get(NetworkTaskViewModel.class);
        mHomePageViewModel =new ViewModelProvider(this).get(HomePageViewModel.class);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_home_page,
                container,
                false);

        mImageSlider=new ImageSlider(mBinding.vfSliderProduct);
        mImageSlider.startSlider();
        setupProducts();
        return mBinding.getRoot();
    }

    private void setupProducts() {
        setupAdapter(mHomePageViewModel.getNewestProductList(),
                mBinding.recyclerViewNewestProduct);

        setupAdapter(mHomePageViewModel.getBestProductList(),
                mBinding.recyclerViewBestProduct);

        setupAdapter(mHomePageViewModel.getPopulateProductList(),
                mBinding.recyclerViewPopulateProduct);

        setupAdapter(mHomePageViewModel.getSpecialProductList(),
                mBinding.recyclerViewSpecialProduct);
    }

    private void setupAdapter(List<Product> productList, RecyclerView recyclerView) {
        ProductAdapter productAdapter = new ProductAdapter(getContext(),productList);
        productAdapter.setCallback(new ProductAdapter.ProductAdapterCallback() {
            @Override
            public void onProductSelected(int productId) {
                NavController navController=
                        Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                HomePageFragmentDirections.
                        ActionNavHomeToNavProductInfo actionNavHomeToNavProductInfo=
                        HomePageFragmentDirections.actionNavHomeToNavProductInfo(productId);

                actionNavHomeToNavProductInfo.setProductId(productId);
                navController.navigate(actionNavHomeToNavProductInfo);
            }
        });

        recyclerView.setLayoutManager
                (new LinearLayoutManager(getContext(),
                        RecyclerView.HORIZONTAL,
                        true));
        recyclerView.setAdapter(productAdapter);
    }

}