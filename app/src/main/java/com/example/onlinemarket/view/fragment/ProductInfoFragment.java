package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.FragmentProductInfoBinding;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.view.slider.ImageSlider;
import com.example.onlinemarket.viewModel.ProductViewModel;

public class ProductInfoFragment extends Fragment{
    public static final String ARG_PRODUCT_Model = "Product Model";

    private FragmentProductInfoBinding mBinding;

    private Product mProductModel;

    private ProductViewModel mViewModel;

    private ImageSlider mImageSlider;

    public ProductInfoFragment() {
        // Required empty public constructor
    }

    public static ProductInfoFragment newInstance(Product productModel) {
        ProductInfoFragment fragment = new ProductInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT_Model, productModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: this is wrong
        mProductModel= (Product)
                getArguments().getSerializable(ARG_PRODUCT_Model);

        mViewModel=new ViewModelProvider(this).
                get(ProductViewModel.class);
        mViewModel.setProduct(mProductModel);
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
        mImageSlider.startSlider(mViewModel.getProduct().getImgUrls());

        mBinding.setViewModel(mViewModel);

        return mBinding.getRoot();
    }
}