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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.ProductSearchAdapter;
import com.example.onlinemarket.databinding.FragmentCategoryDetailBinding;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.view.OpenProductPage;
import com.example.onlinemarket.viewModel.CategoryViewModel;
import com.example.onlinemarket.viewModel.NetworkTaskViewModel;

import java.util.List;

public class CategoryProductsFragment extends Fragment{
    private FragmentCategoryDetailBinding mBinding;
    private ProductSearchAdapter mAdapter;

    private CategoryViewModel mViewModel;
    
    public CategoryProductsFragment() {
        // Required empty public constructor
    }

    public static CategoryProductsFragment newInstance() {
       CategoryProductsFragment fragment = new CategoryProductsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int catId=
                CategoryProductsFragmentArgs.fromBundle(getArguments()).getCatId();

        mViewModel =
                new ViewModelProvider(getActivity()).get(CategoryViewModel.class);

        mViewModel.requestToServerForSpecificCatProduct(catId);
        mViewModel.getProductLiveData().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                setupAdapter(productList);
                mBinding.animLoading.setVisibility(View.GONE);
                mBinding.recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate(inflater,
                R.layout.fragment_category_detail,
                container,
                false);

        setupBackButton();

        return mBinding.getRoot();
    }

    private void setupBackButton() {
        OnBackPressedCallback onBackPressedCallback=new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavController navController=
                        Navigation.findNavController(getActivity(),R.id.nav_host_fragment);

                navController.navigate(R.id.nav_categories);
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(onBackPressedCallback);
    }

    private void setupAdapter(List<Product> models) {
            mAdapter = new ProductSearchAdapter(getContext(),models);
            mAdapter.setCallback(new ProductSearchAdapter.ProductSearchAdapterCallback() {
                @Override
                public void onProductSelected(int productId) {
                    NavController navController=
                            Navigation.findNavController(getActivity(),R.id.nav_host_fragment);

                    CategoryProductsFragmentDirections.
                            ActionNavCategoryProductToNavProductInfo
                            actionNavCategoryProductToNavLoadingProduct=
                            CategoryProductsFragmentDirections.
                                    actionNavCategoryProductToNavProductInfo(productId);

                    actionNavCategoryProductToNavLoadingProduct.setProductId(productId);
                    navController.navigate(actionNavCategoryProductToNavLoadingProduct);
                }
            });
            mBinding.recyclerView.setAdapter(mAdapter);
            mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}