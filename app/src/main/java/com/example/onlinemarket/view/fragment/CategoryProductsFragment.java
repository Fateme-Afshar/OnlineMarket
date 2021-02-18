package com.example.onlinemarket.view.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.ProductSearchAdapter;
import com.example.onlinemarket.databinding.FragmentCategoryDetailBinding;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.viewModel.CategoryViewModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

        mViewModel =
                new ViewModelProvider(getActivity()).get(CategoryViewModel.class);
    }

    @SuppressLint("CheckResult")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate(inflater,
                R.layout.fragment_category_detail,
                container,
                false);

        setupBackButton();
        setupObserver();

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

    @SuppressLint("CheckResult")
    private void setupObserver() {
        int catId=
                CategoryProductsFragmentArgs.fromBundle(getArguments()).getCatId();
        Observable<List<Product>> observable=mViewModel.requestToServerForSpecificCatProduct(catId);

        observable.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(productList -> {
                    setupAdapter(productList);
                    mBinding.animLoading.setVisibility(View.GONE);
                    mBinding.recyclerView.setVisibility(View.VISIBLE);
                }, throwable ->new Exception("CategoryRepository : An error occurred when receive categories "));
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