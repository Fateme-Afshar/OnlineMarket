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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.ProductAdapter;
import com.example.onlinemarket.databinding.FragmentHomePageBinding;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.services.PollWorkManager;
import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.utils.QueryParameters;
import com.example.onlinemarket.utils.Titles;
import com.example.onlinemarket.view.OpenProductPage;
import com.example.onlinemarket.view.slider.ImageSlider;
import com.example.onlinemarket.viewModel.HomeViewModel;
import com.example.onlinemarket.viewModel.NetworkTaskViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.onlinemarket.utils.Titles.NEWEST_PRODUCT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFragment extends Fragment{
    private FragmentHomePageBinding mBinding;

    private NetworkTaskViewModel mNetworkTaskViewModel;
    private HomeViewModel mHomeViewModel;

    private OpenProductPage mCallbacks;

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

        if (context instanceof OpenProductPage)
            mCallbacks=(OpenProductPage) context;
        else
            throw new ClassCastException(
                    "Must implement OpenProductPage interface");

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PollWorkManager.enqueue(getContext(),1,false);
        mNetworkTaskViewModel = new ViewModelProvider(this).get(NetworkTaskViewModel.class);
        mHomeViewModel=new ViewModelProvider(this).get(HomeViewModel.class);

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

    private void setupAdapter(List<Product> productList, RecyclerView recyclerView) {
        ProductAdapter productAdapter = new ProductAdapter(getContext(),productList);
        productAdapter.setCallback(new ProductAdapter.ProductAdapterCallback() {
            @Override
            public void onProductSelected(Product product) {
                    mCallbacks.onItemClickListener(product);
            }
        });

        recyclerView.setLayoutManager
                (new LinearLayoutManager(getContext(),
                        RecyclerView.HORIZONTAL,
                        true));
        recyclerView.setAdapter(productAdapter);
    }

    private void setupProducts() {
        getProducts(mHomeViewModel.getQueryMapNewest(), NEWEST_PRODUCT);
        getProducts(mHomeViewModel.getQueryMapBest(), Titles.BEST_PRODUCT);
        getProducts(mHomeViewModel.getQueryMapPopulate(), Titles.MORE_REVIEWS_PRODUCT);
        getProducts(mHomeViewModel.getQueryMapSpecial(), Titles.SPECIAL_PRODUCT);
    }

    private void getProducts(Map<String, String> queryMap, Titles title) {
        mNetworkTaskViewModel.requestToServerForReceiveProducts(queryMap,title);

        switch (title) {
            case NEWEST_PRODUCT:
                LiveData<List<Product>> productNewestLiveData =
                        mNetworkTaskViewModel.geNewestProductLiveData();
                productNewestLiveData.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        setupAdapter(products,mBinding.recyclerViewNewestProduct);
                        mBinding.notifyChange();
                    }
                });

                break;
            case BEST_PRODUCT:
                LiveData<List<Product>> productBestLiveData=
                        mNetworkTaskViewModel.getBestProductLiveData();
                productBestLiveData.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        setupAdapter(products,mBinding.recyclerViewBestProduct);
                        mBinding.notifyChange();
                    }
                });

                break;
            case MORE_REVIEWS_PRODUCT:

                LiveData<List<Product>> productMostReviewLiveData =
                        mNetworkTaskViewModel.getPopulateProductLiveData();
                productMostReviewLiveData.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        setupAdapter(products,mBinding.recyclerViewPopulateProduct);
                        mBinding.notifyChange();
                    }
                });
                break;
            case SPECIAL_PRODUCT:

                LiveData<List<Product>> specialProductLiveData=
                        mNetworkTaskViewModel.getSpecialProductLiveData();
                specialProductLiveData.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> productList) {
                        setupAdapter(productList,mBinding.recyclerViewSpecialProduct);
                        mBinding.notifyChange();
                    }
                });

            default:
                break;
        }
    }
}