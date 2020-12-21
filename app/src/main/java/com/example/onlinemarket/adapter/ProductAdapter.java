package com.example.onlinemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.ItemViewBinding;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.viewModel.ProductViewModel;


import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    private ItemViewBinding mBinding;
    private List<Product> mProducts;
    private Context mContext;

    private ProductViewModel mViewModel;

    private ProductAdapterCallback mCallback;

    public void setCallback(ProductAdapterCallback callback) {
        mCallback = callback;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
    }

    public ProductAdapter(Context context) {
        mContext = context;

        mViewModel=new ProductViewModel();
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding= DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.item_view,
                parent,
                false);

        return new ProductHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
            holder.bind(mProducts.get(position));
            mViewModel.setProduct(mProducts.get(position));

            holder.mBinding.btnProductMoreInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onProductSelected(mProducts.get(position));
                }
            });
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    class ProductHolder extends RecyclerView.ViewHolder {
        ItemViewBinding mBinding;
        public ProductHolder(@NonNull ItemViewBinding binding) {
            super(binding.getRoot());
            mBinding=binding;
        }

        public void bind(Product product){

            Glide.with(mContext).
                    load(product.getImgUrls().get(0)).
                    placeholder(R.drawable.img_place_holder).
                    into(mBinding.imgProduct);
        }

    }

    public interface ProductAdapterCallback{
        void onProductSelected(Product product);
    }
}
