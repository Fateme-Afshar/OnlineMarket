package com.example.onlinemarket.view.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.example.onlinemarket.model.Titles;
import com.example.onlinemarket.utils.NetworkParams;
import com.example.onlinemarket.viewModel.NetworkTaskViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.onlinemarket.model.Titles.NEWEST_PRODUCT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFragment extends Fragment {
    private FragmentHomePageBinding mBinding;

    private NetworkTaskViewModel mNetworkTaskViewModel;
    private HomePageFragmentCallbacks mCallbacks;

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

        if (context instanceof HomePageFragmentCallbacks)
            mCallbacks=(HomePageFragmentCallbacks) context;
        else
            throw new ClassCastException(
                    "Must implement HomePageFragmentCallbacks interface");

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNetworkTaskViewModel = new ViewModelProvider(this).get(NetworkTaskViewModel.class);
        //receive newest products
        Map<String, String> queryMapNewest = new HashMap<>();
        queryMapNewest.put("orderby", "date");
        queryMapNewest.put("order", NetworkParams.ORDER_ASC);
        getProducts(queryMapNewest, NEWEST_PRODUCT);

        //receive best products
        Map<String, String> queryMapBest = new HashMap<>();
        queryMapBest.put("orderby", "rating");
        queryMapBest.put("order", NetworkParams.ORDER_ASC);
        getProducts(queryMapBest, Titles.BEST_PRODUCT);

        //receive most review products
        Map<String, String> queryMapPopulate = new HashMap<>();
        queryMapPopulate.clear();
        queryMapPopulate.put("onSale", "true");
        getProducts(queryMapPopulate, Titles.MORE_REVIEWS_PRODUCT);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_home_page,
                container,
                false);
        int[] images={R.drawable.digital,R.drawable.market,R.drawable.hygianics};

        for (int image : images) {
            flipperImages(image);
        }
        return mBinding.getRoot();
    }

    private void flipperImages(int image){
        ImageView imageView=new ImageView(getContext());
        imageView.setBackgroundResource(image);

        mBinding.vfSliderProduct.addView(imageView);
        mBinding.vfSliderProduct.setFlipInterval(4000);
        mBinding.vfSliderProduct.setAutoStart(true);

        mBinding.vfSliderProduct.setInAnimation(getContext(), android.R.anim.slide_in_left);
        mBinding.vfSliderProduct.setInAnimation(getContext(), android.R.anim.slide_out_right);
    }

    private void setupAdapter(List<Product> productList, RecyclerView recyclerView) {
        ProductAdapter productAdapter = new ProductAdapter(getContext());
        productAdapter.setProducts(productList);
        productAdapter.setCallback(new ProductAdapter.ProductAdapterCallback() {
            @Override
            public void onProductSelected(Product product) {
                    mCallbacks.onClickMoreBtn(product);
            }
        });

        recyclerView.setLayoutManager
                (new LinearLayoutManager(getContext(),
                        RecyclerView.HORIZONTAL,
                        true));
        recyclerView.setAdapter(productAdapter);
    }

    private void getProducts(Map<String, String> queryMap, Titles title) {
        mNetworkTaskViewModel.requestToServerForReceiveProducts(queryMap,title);

        switch (title) {
            case NEWEST_PRODUCT:
                LiveData<List<Product>> productNewestLiveData =
                        mNetworkTaskViewModel.geNewestProductLiveData();
                productNewestLiveData.observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        setupAdapter(products,mBinding.recyclerViewNewestProduct);
                    }
                });

                break;
            case BEST_PRODUCT:
                LiveData<List<Product>> productBestLiveData=
                        mNetworkTaskViewModel.getBestProductLiveData();
                productBestLiveData.observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        setupAdapter(products,mBinding.recyclerViewBestProduct);
                    }
                });

                break;
            case MORE_REVIEWS_PRODUCT:

                LiveData<List<Product>> productMostReviewLiveData =
                        mNetworkTaskViewModel.getPopulateProductLiveData();
                productMostReviewLiveData.observe(this, new Observer<List<Product>>() {
                    @Override
                    public void onChanged(List<Product> products) {
                        setupAdapter(products,mBinding.recyclerViewPopulateProduct);
                    }
                });

                break;
            default:
                break;
        }
    }
    public interface HomePageFragmentCallbacks{
        void onStartCategoryFragment();
        void onClickBestProduct();
        void onClickMoreBtn(Product product);
    }
}