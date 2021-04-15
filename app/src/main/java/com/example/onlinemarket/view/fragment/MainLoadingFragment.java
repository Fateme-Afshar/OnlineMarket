package com.example.onlinemarket.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlinemarket.OnlineShopApplication;
import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.MainLoadingViewBinding;
import com.example.onlinemarket.model.AttributeInfo;
import com.example.onlinemarket.utils.LoadingUtils;
import com.example.onlinemarket.utils.ProgramUtils;
import com.example.onlinemarket.utils.Titles;
import com.example.onlinemarket.view.activity.LoadingActivity;
import com.example.onlinemarket.view.activity.MainActivity;
import com.example.onlinemarket.viewModel.MainLoadingViewModel;
import com.example.onlinemarket.viewModel.NetworkTaskViewModel;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.onlinemarket.utils.Titles.NEWEST_PRODUCT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainLoadingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainLoadingFragment extends Fragment {
    @Inject
    NetworkTaskViewModel mNetworkTaskViewModel;
    @Inject
    MainLoadingViewModel mMainLoadingViewModel;

    private MainLoadingViewBinding mBinding;

    private MainLoadingFragmentCallback mCallback;

    private boolean[] flags = new boolean[4];

    public MainLoadingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((LoadingActivity)getActivity()).getActivityComponent().inject(this);
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
        mNetworkTaskViewModel.isCompleteLoadingProducts().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                flags[0] = true;
                completeLoadingData();
            }
        });
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

    @SuppressLint("CheckResult")
    private void setupLoadingAttributes() {
        Observable<List<AttributeInfo>> observable=
                mNetworkTaskViewModel.requestToServerForReceiveInfoColorAttribute();

        observable.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(attributeInfoList -> {
                    mMainLoadingViewModel.setColorAttributeInfoList(attributeInfoList);
                    flags[1]=true;
                    completeLoadingData();
                },throwable -> {
                    Log.e(ProgramUtils.TAG, "FilterRepository :Fail Receive Attributes");});

        Observable<List<AttributeInfo>> sizeObservable=
                mNetworkTaskViewModel.requestToServerForReceiveInfoSizeAttribute();

        sizeObservable.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(attributeInfoList -> {
                    mMainLoadingViewModel.setSizeAttributeInfoList(attributeInfoList);
                    flags[2]=true;
                    completeLoadingData();
                },throwable -> {
                    Log.e(ProgramUtils.TAG, "FilterRepository :Fail Receive Attributes");});

    }

    private void setupLoadingCategories() {
        mNetworkTaskViewModel.requestToServerForCategories();
        mNetworkTaskViewModel.isCompleteLoadingCategories().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                flags[3] = true;
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
    }

    private void completeLoadingData(){
        if (flags[0] && flags[1] && flags[2] && flags[3]) {
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