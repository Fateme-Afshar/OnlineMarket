package com.example.onlinemarket.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.ProductSearchAdapter;
import com.example.onlinemarket.databinding.FragmentSearchBinding;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.utils.ProgramUtils;
import com.example.onlinemarket.view.activity.MainActivity;
import com.example.onlinemarket.viewModel.NetworkTaskViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private FragmentSearchBinding mBinding;
    private ProductSearchAdapter mAdapter;

    @Inject
    NetworkTaskViewModel mViewModel;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        ((MainActivity)getActivity()).getActivityComponent().inject(this);
    }

    private void setupSearchView() {
        mBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("CheckResult")
            @Override
            public boolean onQueryTextSubmit(String query) {
                setupVisibility(View.GONE, View.VISIBLE);
                Observable<List<Product>> observable =
                        mViewModel.requestToServerForSearchProducts("name", query);
                observable.subscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread()).
                        subscribe(SearchFragment.this::setupAdapter, throwable -> {
                            Log.e(ProgramUtils.TAG, throwable.getMessage());
                        });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length()==0) {
                    setupAdapter(new ArrayList<>());
                    setupVisibility(View.GONE,View.GONE);
                }
                return false;
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_search,
                container,
                false);
        setupSearchView();
        return mBinding.getRoot();
    }

    private void setupAdapter(List<Product> productList) {
        mAdapter=
                new ProductSearchAdapter(getActivity(),productList);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.setAdapter(mAdapter);
        mAdapter.setCallback(new ProductSearchAdapter.ProductSearchAdapterCallback() {
            @Override
            public void onProductSelected(int productId) {
                NavController navController=
                        Navigation.findNavController(getActivity(),R.id.nav_host_fragment);

                SearchFragmentDirections.ActionNavSearchToNavProductInfo
                        actionNavSearchToNavLoadingProduct=
                        SearchFragmentDirections.actionNavSearchToNavProductInfo(productId);

                actionNavSearchToNavLoadingProduct.setProductId(productId);
                navController.navigate(actionNavSearchToNavLoadingProduct);
            }
        });
        setupEmpty(productList);
    }

    private void setupEmpty(List<Product> productList) {
        if (productList.size() == 0) {
            setupVisibility(View.VISIBLE, View.GONE);
        } else {
            mBinding.animEmpty.setVisibility(View.GONE);
            mBinding.tvEmpty.setVisibility(View.GONE);
        }
    }

    private void setupVisibility(int emptyVisibility, int loadingVisibility) {
        mBinding.animEmpty.setVisibility(emptyVisibility);
        mBinding.tvEmpty.setVisibility(emptyVisibility);
        mBinding.animLoading.setVisibility(loadingVisibility);
    }
}