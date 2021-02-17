package com.example.onlinemarket.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.ProductSearchAdapter;
import com.example.onlinemarket.databinding.FragmentFilterBinding;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.view.OpenProductPage;
import com.example.onlinemarket.viewModel.FilterViewModel;

import java.util.ArrayList;
import java.util.List;


public class FilterFragment extends Fragment{
    public static final int REQUEST_CODE_FILTER_BOTTOM_SHEET = 1;
    public static final int REQUEST_CODE_FILTER_MORE_BOTTOM_SHEET =2;
    public static final String TAG_FILTER_FRAGMENT = "Filter Fragment";
    private FilterViewModel mViewModel;
    private FragmentFilterBinding mBinding;

    private ProductSearchAdapter mAdapter;

    private OpenProductPage mCallback;

    public FilterFragment() {
        // Required empty public constructor
    }

    public static FilterFragment newInstance() {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViewModel();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK && data == null)
            return;
        if (requestCode == REQUEST_CODE_FILTER_BOTTOM_SHEET) {
            List<Integer> filterIds = mViewModel.getFilterIds();

            int attributeId = data.getIntExtra
                    (FilterItemBottomSheetFragment.EXTRA_FILTER_ATTRIBUTE_ID, 0);
            if (attributeId == 3)
                mViewModel.requestToServerForReceiveFilterProductsOnAttributeTerm(filterIds, "pa_color");
            else
                mViewModel.requestToServerForReceiveFilterProductsOnAttributeTerm(filterIds, "pa_size");

            setupAdapter(new ArrayList<>());
            setupVisebility();


        }else if(requestCode==REQUEST_CODE_FILTER_MORE_BOTTOM_SHEET) {
            String orderby = data.getStringExtra(FilterProductBottomSheetFragment.EXTRA_ORDER_BY);
            String order = data.getStringExtra(FilterProductBottomSheetFragment.EXTRA_ORDER);
            mViewModel.requestToServerForReceiveFilterProductsOnMore(orderby, order);
            setupVisebility();
            setupAdapter(new ArrayList<>());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate
                (inflater,
                        R.layout.fragment_filter,
                        container,
                        false);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        setupAdapter(mViewModel.getFilterProducts());

        mBinding.animLoading.setVisibility(View.GONE);
    }

    private void setupViewModel() {
        mViewModel = new ViewModelProvider(getActivity()).get(FilterViewModel.class);
        mViewModel.setOnFilterAttributeClickListener(new FilterViewModel.OnBtnClickListener() {
            @Override
            public void onAttributeSelected(int attributeId) {
                FilterItemBottomSheetFragment filterItemBottomSheetFragment =
                        FilterItemBottomSheetFragment.newInstance(attributeId);

                //create parent-child relationship between FilterFragment and FilterItemBottomSheetFragment
                filterItemBottomSheetFragment.
                        setTargetFragment(FilterFragment.this,
                                REQUEST_CODE_FILTER_BOTTOM_SHEET);

                filterItemBottomSheetFragment.show(FilterFragment.this.getParentFragmentManager(),
                        TAG_FILTER_FRAGMENT);
            }

            @Override
            public void onFilterBtnClickListener() {
                FilterProductBottomSheetFragment filterProductBottomSheetFragment =
                        FilterProductBottomSheetFragment.newInstance();

                //create parent-child relationship between FilterFragment and FilterProductBottomSheetFragment
                filterProductBottomSheetFragment.
                        setTargetFragment(FilterFragment.this,
                                REQUEST_CODE_FILTER_MORE_BOTTOM_SHEET);

                filterProductBottomSheetFragment.show(FilterFragment.this.getParentFragmentManager(),
                        TAG_FILTER_FRAGMENT);
            }
        });
    }

    private void setupAdapter(List<Product> productList) {
        mAdapter = new ProductSearchAdapter(getContext(), productList);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.setAdapter(mAdapter);

        mAdapter.setCallback(new ProductSearchAdapter.ProductSearchAdapterCallback() {
            @Override
            public void onProductSelected(int productId) {
                NavController navController=
                        Navigation.findNavController(getActivity(),R.id.nav_host_fragment);

                FilterFragmentDirections.ActionNavFilterToNavProductInfo
                        actionNavFilterToNavLoadingProduct=
                        FilterFragmentDirections.actionNavFilterToNavProductInfo(productId);

                actionNavFilterToNavLoadingProduct.setProductId(productId);
                navController.navigate(actionNavFilterToNavLoadingProduct);
            }
        });

        setupEmpty(productList);
    }

    private void setupEmpty(List<Product> productList) {
        if (productList.size() == 0) {
            mBinding.animEmpty.setVisibility(View.VISIBLE);
            mBinding.tvEmpty.setVisibility(View.VISIBLE);
            mBinding.animLoading.setVisibility(View.GONE);
        } else {
            mBinding.animEmpty.setVisibility(View.GONE);
            mBinding.tvEmpty.setVisibility(View.GONE);
        }
    }

    private void setupVisebility() {
        mBinding.animLoading.setVisibility(View.VISIBLE);
        mBinding.animEmpty.setVisibility(View.GONE);
        mBinding.tvEmpty.setVisibility(View.GONE);
    }
}