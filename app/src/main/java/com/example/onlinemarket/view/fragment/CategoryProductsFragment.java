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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.ProductSearchAdapter;
import com.example.onlinemarket.databinding.FragmentCategoryDetailBinding;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.view.OpenProductPage;
import com.example.onlinemarket.viewModel.NetworkTaskViewModel;

import java.util.List;

public class CategoryProductsFragment extends Fragment{
    private FragmentCategoryDetailBinding mBinding;
    private ProductSearchAdapter mAdapter;

    private int mCategoryId;

    private NetworkTaskViewModel mNetworkTaskViewModel;
    private OpenProductPage mCallback;
    
    public CategoryProductsFragment() {
        // Required empty public constructor
    }

    public static CategoryProductsFragment newInstance() {
       CategoryProductsFragment fragment = new CategoryProductsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OpenProductPage)
            mCallback=(OpenProductPage) context;
        else
            throw new ClassCastException(
                    "Must implement OpenProductPage interface");

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryId = CategoryProductsFragmentArgs.fromBundle(getArguments()).getCatId();

        mNetworkTaskViewModel =
                new ViewModelProvider(this).get(NetworkTaskViewModel.class);

        mNetworkTaskViewModel.requestToServerForSpecificCatProduct(mCategoryId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate(inflater,
                R.layout.fragment_category_detail,
                container,
                false);

        return mBinding.getRoot();
    }

    private void setupAdapter(List<Product> models) {
            mAdapter = new ProductSearchAdapter(getContext(),models);
            mAdapter.setCallback(new ProductSearchAdapter.ProductSearchAdapterCallback() {
                @Override
                public void onProductSelected(Product product) {
                    mCallback.onItemClickListener(product);
                }
            });
            mBinding.recyclerView.setAdapter(mAdapter);
            mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void updateUI(List<Product> models) {
        if (mAdapter == null)
            setupAdapter(models);
        else {
            mAdapter.setProducts(models);
            mAdapter.notifyDataSetChanged();
        }
    }
}