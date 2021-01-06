package com.example.onlinemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.ItemViewBinding;
import com.example.onlinemarket.model.Product;
import com.example.onlinemarket.viewModel.ProductViewModel;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    private ItemViewBinding mBinding;
    private List<Product> mProducts;
    private Context mContext;

    private ProductAdapterCallback mCallback;

    public void setCallback(ProductAdapterCallback callback) {
        mCallback = callback;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
    }

    public ProductAdapter(Context context, List<Product> productList) {
        mContext = context;
        mProducts=productList;

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
            mBinding.setProduct(product);
        }

    }

    public interface ProductAdapterCallback{
        void onProductSelected(Product product);
    }
}
