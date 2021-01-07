package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.ProductAdapter;
import com.example.onlinemarket.adapter.ProductSearchAdapter;
import com.example.onlinemarket.databinding.FragmentSearchBinding;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.viewModel.NetworkTaskViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private FragmentSearchBinding mBinding;
    private ProductSearchAdapter mAdapter;

    private NetworkTaskViewModel mViewModel;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel=new ViewModelProvider(this).get(NetworkTaskViewModel.class);
    }

    private void setupSearchView() {
        mBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mViewModel.requestToServerForSearchProducts("name",query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length()==0) {
                    setupAdapter(new ArrayList<>());
                    mBinding.animEmpty.setVisibility(View.GONE);
                    mBinding.tvEmpty.setVisibility(View.GONE);
                }
                return false;
            }
        });

        mViewModel.getSearchProductLiveData().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                setupAdapter(productList);
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
        setupEmpty(productList);
    }

    private void setupEmpty(List<Product> productList) {
        if (productList.size() == 0) {
            mBinding.animEmpty.setVisibility(View.VISIBLE);
            mBinding.tvEmpty.setVisibility(View.VISIBLE);
        } else {
            mBinding.animEmpty.setVisibility(View.GONE);
            mBinding.tvEmpty.setVisibility(View.GONE);
        }
    }
}