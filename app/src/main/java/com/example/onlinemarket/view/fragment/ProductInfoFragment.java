package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.ReviewAdapter;
import com.example.onlinemarket.databinding.FragmentProductInfoBinding;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.model.Review;
import com.example.onlinemarket.view.slider.ImageSlider;
import com.example.onlinemarket.viewModel.ProductViewModel;
import com.example.onlinemarket.viewModel.ReviewViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductInfoFragment extends Fragment{
    private FragmentProductInfoBinding mBinding;

    private Product mProductModel;

    private ProductViewModel mProductViewModel;
    private ReviewViewModel mReviewViewModel;

    private ReviewAdapter mReviewAdapter;

    private ImageSlider mImageSlider;

    public ProductInfoFragment() {
        // Required empty public constructor
    }

    public static ProductInfoFragment newInstance() {
        ProductInfoFragment fragment = new ProductInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: this is wrong
        mProductModel =
                ProductInfoFragmentArgs.fromBundle(getArguments()).getProductId();

        mProductViewModel =new ViewModelProvider(this).
                get(ProductViewModel.class);
        mProductViewModel.setProduct(mProductModel);

        mReviewViewModel=new ViewModelProvider(this).
                get(ReviewViewModel.class);

        mReviewViewModel.requestToReceiveProductReview(mProductModel.getId());
        mReviewViewModel.getListLiveData().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                setupReviewAdapter(reviews);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_product_info,
                container,
                false);
        //mBinding.webView.loadUrl(mProductUrl);

        mImageSlider = new ImageSlider(mBinding.imgSlider);
        mImageSlider.startSlider(mProductViewModel.getProduct().getImgUrls());

        mBinding.setViewModel(mProductViewModel);

        return mBinding.getRoot();
    }

    private void setupReviewAdapter(List<Review> reviews) {
        mReviewAdapter=new ReviewAdapter(reviews,mProductViewModel,getActivity());

        mBinding.recyclerViewReviewer.setAdapter(mReviewAdapter);
        mBinding.recyclerViewReviewer.setLayoutManager
                (new LinearLayoutManager(getActivity(),
                        LinearLayoutManager.HORIZONTAL,
                        true));
    }
}