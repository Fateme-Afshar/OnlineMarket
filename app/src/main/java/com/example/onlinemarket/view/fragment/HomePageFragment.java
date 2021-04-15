package com.example.onlinemarket.view.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.OnlineShopApplication;
import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.ProductAdapter;
import com.example.onlinemarket.databinding.FragmentHomePageBinding;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.view.slider.ImageSlider;
import com.example.onlinemarket.viewModel.HomePageViewModel;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFragment extends Fragment {
    private FragmentHomePageBinding mBinding;

    @Inject
    HomePageViewModel mHomePageViewModel;

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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        ((OnlineShopApplication) getActivity().getApplication()).getApplicationGraph().inject(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupBackButton();
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

    private void setupBackButton() {
        OnBackPressedCallback onBackPressedCallback=new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                    getActivity().finish();
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(onBackPressedCallback);
    }

}