package com.example.onlinemarket.view.fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.ProductAdapter;
import com.example.onlinemarket.databinding.FragmentHomePageBinding;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.repository.OnlineMarketRepository;
import com.example.onlinemarket.viewModel.NetworkTaskViewModel;
import com.example.onlinemarket.viewModel.ProductViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFragment extends Fragment {
    private FragmentHomePageBinding mBinding;

    private NetworkTaskViewModel mNetworkTaskViewModel;

    private ProductAdapter mAdapter;

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

        Map<String,String> queryMap=new HashMap<>();
        queryMap.put("orederby","date");

        if (mNetworkTaskViewModel.checkNetworkState()){
            mNetworkTaskViewModel.requestToServerForReceiveProducts(queryMap);

            mNetworkTaskViewModel.getProductLiveData().observe(this, new Observer<List<Product>>() {
                @Override
                public void onChanged(List<Product> products) {
                    setupAdapter(products);
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_home_page,
                container,
                false);
        return mBinding.getRoot();
    }

    private void setupAdapter(List<Product> productList) {
        mAdapter = new ProductAdapter(getContext());
        mAdapter.setProducts(productList);

        mBinding.recyclerViewNewestProduct.setLayoutManager
                (new LinearLayoutManager(getContext(),
                        RecyclerView.HORIZONTAL,
                        true));
        mBinding.recyclerViewNewestProduct.setAdapter(mAdapter);
    }
}