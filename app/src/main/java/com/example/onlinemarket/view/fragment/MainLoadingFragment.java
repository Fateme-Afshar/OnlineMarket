package com.example.onlinemarket.view.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
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

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.MainLoadingViewBinding;
import com.example.onlinemarket.model.AttributeInfo;
import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.utils.LoadingUtils;
import com.example.onlinemarket.utils.Titles;
import com.example.onlinemarket.viewModel.MainLoadingViewModel;
import com.example.onlinemarket.viewModel.NetworkTaskViewModel;

import java.util.List;
import java.util.Map;

import static com.example.onlinemarket.utils.Titles.NEWEST_PRODUCT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainLoadingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainLoadingFragment extends Fragment {
    private NetworkTaskViewModel mNetworkTaskViewModel;
    private MainLoadingViewModel mMainLoadingViewModel;

    private MainLoadingViewBinding mBinding;

    private MainLoadingFragmentCallback mCallback;

    private boolean[] flags=new boolean[7];

    public MainLoadingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof MainLoadingFragmentCallback)
            mCallback=(MainLoadingFragmentCallback) context;
        else
            throw new ClassCastException
                    ("Mast be implement MainLoadingFragmentCallback");

    }

    public static MainLoadingFragment newInstance() {
        MainLoadingFragment fragment = new MainLoadingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainLoadingViewModel =
                new ViewModelProvider(getActivity()).get(MainLoadingViewModel.class);
        mNetworkTaskViewModel=
                new ViewModelProvider(this).get(NetworkTaskViewModel.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       mBinding = DataBindingUtil.inflate(
               inflater,
               R.layout.main_loading_view,
               container,
               false);
        setupLoadingData();
       return mBinding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setupLoadingData() {
        if (LoadingUtils.checkHasInternet(getActivity().getSystemService(ConnectivityManager.class))){
            setupVisibility(View.VISIBLE,View.GONE);
            setupLoadingProducts();
            setupLoadingCategories();
            setupLoadingAttributes();
        }else {
            setupVisibility(View.GONE,View.VISIBLE);
            mBinding.btnRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setupLoadingData();
                }
            });
        }
    }

    private void setupLoadingAttributes() {
        mNetworkTaskViewModel.requestToServerForReceiveInfoColorAttribute();
        mNetworkTaskViewModel.requestToServerForReceiveInfoSizeAttribute();

        mNetworkTaskViewModel.getColorAttributeInfoList().observe(getActivity(), new Observer<List<AttributeInfo>>() {
            @Override
            public void onChanged(List<AttributeInfo> attributeInfoList) {
                mMainLoadingViewModel.setColorAttributeInfoList(attributeInfoList);

                flags[6]=true;
                completeLoadingData();
            }
        });

        mNetworkTaskViewModel.getSizeAttributeInfoList().observe(getActivity(), new Observer<List<AttributeInfo>>() {
            @Override
            public void onChanged(List<AttributeInfo> attributeInfoList) {
                mMainLoadingViewModel.setSizeAttributeInfoList(attributeInfoList);
                flags[5]=true;
                completeLoadingData();
            }
        });

    }

    private void setupLoadingCategories() {
        mNetworkTaskViewModel.requestToServerForCategories();
        mNetworkTaskViewModel.getCategoryLiveData().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                mMainLoadingViewModel.setCategories(categories);
                flags[4]=true;
                completeLoadingData();
            }
        });
    }

    private void setupLoadingProducts() {
        getProducts(mNetworkTaskViewModel.getQueryMapNewest(), NEWEST_PRODUCT);
        getProducts(mNetworkTaskViewModel.getQueryMapBest(), Titles.BEST_PRODUCT);
        getProducts(mNetworkTaskViewModel.getQueryMapPopulate(), Titles.MORE_REVIEWS_PRODUCT);
        getProducts(mNetworkTaskViewModel.getQueryMapSpecial(), Titles.SPECIAL_PRODUCT);
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
                        mMainLoadingViewModel.setNewestProductList(products);

                        flags[0]=true;
                        completeLoadingData();
                    }
                });

                break;
            case BEST_PRODUCT:
                LiveData<List<Product>> productBestLiveData=
                        mNetworkTaskViewModel.getBestProductLiveData();
                productBestLiveData.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        mMainLoadingViewModel.setBestProductList(products);

                        flags[1]=true;
                        completeLoadingData();
                    }
                });

                break;
            case MORE_REVIEWS_PRODUCT:

                LiveData<List<Product>> productMostReviewLiveData =
                        mNetworkTaskViewModel.getPopulateProductLiveData();
                productMostReviewLiveData.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        mMainLoadingViewModel.setPopulateProductList(products);
                        flags[2]=true;
                        completeLoadingData();
                    }
                });
                break;
            case SPECIAL_PRODUCT:

                LiveData<List<Product>> specialProductLiveData=
                        mNetworkTaskViewModel.getSpecialProductLiveData();
                specialProductLiveData.observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> productList) {
                        mMainLoadingViewModel.setSpecialProductList(productList);
                        flags[3]=true;
                        completeLoadingData();
                    }
                });

            default:
                break;
        }
    }

    private void completeLoadingData(){
        if (flags[0] && flags[1] && flags[2] &&
                flags[3] && flags[4] && flags[5] && flags[6]){
                mCallback.onStartMainActivity();
        }
    }

    private void setupVisibility(int gone, int visible) {
        mBinding.animLoading.setVisibility(gone);

        mBinding.animNoInternet.setVisibility(visible);
        mBinding.tvNoInternet.setVisibility(visible);
        mBinding.btnRefresh.setVisibility(visible);

        mBinding.tvOnlineShop.setVisibility(gone);
        mBinding.tvOnlineShopEn.setVisibility(gone);
        mBinding.imvOnlineMarket.setVisibility(gone);
    }

    public interface MainLoadingFragmentCallback{
        void onStartMainActivity();
    }
}