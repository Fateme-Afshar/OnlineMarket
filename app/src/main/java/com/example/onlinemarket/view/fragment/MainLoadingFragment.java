package com.example.onlinemarket.view.fragment;

import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.MainLoadingViewBinding;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.utils.LoadingUtils;
import com.example.onlinemarket.utils.Titles;
import com.example.onlinemarket.viewModel.HomeViewModel;
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
    private HomeViewModel mHomeViewModel;

    private MainLoadingViewBinding mBinding;

    private boolean[] flags=new boolean[4];

    public MainLoadingFragment() {
        // Required empty public constructor
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
        mHomeViewModel=
                new ViewModelProvider(getActivity()).get(HomeViewModel.class);
        mNetworkTaskViewModel=
                new ViewModelProvider(this).get(NetworkTaskViewModel.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       mBinding= DataBindingUtil.inflate(
               inflater,
               R.layout.main_loading_view,
               container,
               false);
       setupLoadData();
       return mBinding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setupLoadData() {
        if (LoadingUtils.checkHasInternet(getActivity().getSystemService(ConnectivityManager.class))){
            setupVisibility(View.VISIBLE,View.GONE);
            setupProducts();
        }else {
            setupVisibility(View.GONE,View.VISIBLE);
        }
    }

    private void setupProducts() {
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
                        mHomeViewModel.setNewestProductList(products);

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
                        mHomeViewModel.setBestProductList(products);

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
                        mHomeViewModel.setPopulateProductList(products);
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
                        mHomeViewModel.setSpecialProductList(productList);
                        flags[3]=true;
                        completeLoadingData();
                    }
                });

            default:
                break;
        }
    }

    private void completeLoadingData(){
        if (flags[0] && flags[1] && flags[2] && flags[3]){
            NavController navController=
                    Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
            navController.navigate(R.id.nav_home);
        }
    }

    private void setupVisibility(int gone, int visible) {
        mBinding.animLoading.setVisibility(gone);

        mBinding.animNoInternet.setVisibility(visible);
        mBinding.tvNoInternet.setVisibility(visible);
        mBinding.btnRefresh.setVisibility(visible);
    }
}