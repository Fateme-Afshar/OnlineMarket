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
import androidx.recyclerview.widget.RecyclerView;

import com.example.digikalaapp.R;
import com.example.digikalaapp.adapter.ProductAdapter;
import com.example.digikalaapp.databinding.FragmentCategoryDetailBinding;
import com.example.digikalaapp.model.ProductModel;
import com.example.digikalaapp.viewModel.NetworkTaskViewModel;
import com.example.onlinemarket.adapter.ProductAdapter;
import com.example.onlinemarket.databinding.FragmentCategoryDetailBinding;
import com.example.onlinemarket.viewModel.NetworkTaskViewModel;

import java.util.List;

public class CategoryProductsFragment extends Fragment {
    public static final String ARGS_CATEGORY_ID = "Category Id";
    private FragmentCategoryDetailBinding mBinding;
    private ProductAdapter mAdapter;

    private int mCategoryId;

    private NetworkTaskViewModel mNetworkTaskViewModel;
    private CategoryProductsFragmentCallback mCallback;
    
    public CategoryProductsFragment() {
        // Required empty public constructor
    }

    public static CategoryProductsFragment newInstance(int catId) {
        CategoryProductsFragment fragment = new CategoryProductsFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_CATEGORY_ID,catId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof CategoryProductsFragmentCallback)
            mCallback=(CategoryProductsFragmentCallback) context;
        else
            throw new ClassCastException(
                    "Must implement CategoryProductsFragmentCallback interface");

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryId = getArguments().getInt(ARGS_CATEGORY_ID);

        mNetworkTaskViewModel =
                new ViewModelProvider(this).get(NetworkTaskViewModel.class);

        if (mNetworkTaskViewModel.checkNetworkState()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Product> productModels=
                            mNetworkTaskViewModel.requestToServerForSpecificCatProduct(mCategoryId);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateUI(productModels);
                        }
                    });
                }
            }).start();
        }
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

    private void setupAdapter(List<ProductModel> models) {
            mAdapter = new ProductAdapter(getContext());
            mAdapter.setProductModels(models);
            mAdapter.setCallback(new ProductAdapter.ProductAdapterCallback() {
                @Override
                public void onProductSelected(ProductModel productModel) {
                    mCallback.onSelectedMoreInfoBtn(productModel);
                }
            });
            mBinding.recyclerView.setAdapter(mAdapter);
            mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false));
    }

    private void updateUI(List<ProductModel> models) {
        if (mAdapter == null)
            setupAdapter(models);
        else {
            mAdapter.setProductModels(models);
            mAdapter.notifyDataSetChanged();
        }
    }

    public interface CategoryProductsFragmentCallback {
        void onSelectedMoreInfoBtn(ProductModel productModel);
    }
}