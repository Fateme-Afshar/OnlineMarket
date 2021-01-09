package com.example.onlinemarket.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.ProductSearchAdapter;
import com.example.onlinemarket.databinding.FragmentCartBinding;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.view.IOnBackPress;
import com.example.onlinemarket.view.OpenProductPage;
import com.example.onlinemarket.viewModel.CartViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment implements IOnBackPress {
    private FragmentCartBinding mBinding;
    private CartViewModel mViewModel;
    private ProductSearchAdapter mAdapter;

    private OpenProductPage mCallback;
    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OpenProductPage)
            mCallback=(OpenProductPage) context;
        else
            throw new ClassCastException(
                    "Must implement OpenProductPage interface");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel=new ViewModelProvider(this).get(CartViewModel.class);
        mViewModel.getProductRegisteredForPurchase().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                setupAdapter(productList);
                int totalPrice=0;
                for (int i = 0; i < productList.size(); i++) {
                    totalPrice+=productList.get(i).getRegularPrice();
                }
                mViewModel.setTotalPrice(totalPrice);
                mBinding.tvTotalPrice.setText("قیمت کل : "+totalPrice);

                mViewModel.setProductList(productList);
            }
        });

        mViewModel.setLifecycleOwner(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate
                (inflater,
                        R.layout.fragment_cart,
                        container,
                        false);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    private void setupAdapter(List<Product> productList){
        mAdapter=new ProductSearchAdapter(getContext(),productList);
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter.setCallback(new ProductSearchAdapter.ProductSearchAdapterCallback() {
            @Override
            public void onProductSelected(Product product) {
                mCallback.onItemClickListener(product);
            }
        });

        setupEmpty(productList);
    }

    private void setupEmpty(List<Product> productList) {
        if(productList.size()==0) {
            mBinding.animEmpty.setVisibility(View.VISIBLE);
            mBinding.tvEmpty.setVisibility(View.VISIBLE);
            mBinding.tvTotalPrice.setVisibility(View.GONE);
            mBinding.btnFinalizePurchase.setVisibility(View.GONE);
        }
        else {
            mBinding.animEmpty.setVisibility(View.GONE);
            mBinding.tvEmpty.setVisibility(View.GONE);
            mBinding.tvTotalPrice.setVisibility(View.VISIBLE);
            mBinding.btnFinalizePurchase.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}