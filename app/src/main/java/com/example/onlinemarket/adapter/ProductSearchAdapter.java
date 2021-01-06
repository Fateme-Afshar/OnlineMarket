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
import com.example.onlinemarket.databinding.ItemSearchViewBinding;
import com.example.onlinemarket.model.Product;

import java.util.List;

public class ProductSearchAdapter extends RecyclerView.Adapter<ProductSearchAdapter.Holder>{
    private List<Product> mProducts;
    private Context mContext;

    private ItemSearchViewBinding mBinding;

    private ProductAdapterCallback mCallback;

    public void setCallback(ProductAdapterCallback callback) {
        mCallback = callback;
    }

    public void setProducts(List<Product> products) {
        mProducts = products;
    }

    public ProductSearchAdapter(Context context,List<Product> products) {
        mContext = context;
        mProducts = products;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding= DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.item_search_view,
                parent,
                false);

        return new Holder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
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

    class Holder extends RecyclerView.ViewHolder {
        private ItemSearchViewBinding mBindingView;

        public Holder(@NonNull ItemSearchViewBinding itemSearchViewBinding) {
            super(itemSearchViewBinding.getRoot());
            mBindingView=itemSearchViewBinding;
        }

        public void bind(Product product) {
            mBinding.setProduct(product);
        }
    }

    public interface ProductAdapterCallback{
        void onProductSelected(Product product);
    }
}