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
import com.example.onlinemarket.databinding.CategoryViewBinding;
import com.example.onlinemarket.model.Category;
import com.example.onlinemarket.viewModel.CategoryViewModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CatHolder> {
    private CategoryViewBinding mBinding;
    private List<Category> mCategories;
    private Context mContext;

    private CatAdapterCallbacks mCallbacks;

    public void setCallbacks(CatAdapterCallbacks callbacks) {
        mCallbacks = callbacks;
    }

    public void setCategories(List<Category> categories) {
        mCategories = categories;
    }

    public CategoryAdapter(Context context,List<Category> categories) {
        mContext = context;
        mCategories=categories;
    }

    @NonNull
    @Override
    public CatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBinding= DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.category_view,
                parent,
                false);

        return new CatHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CatHolder holder, int position) {
            holder.bind(mCategories.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallbacks.OnSelectedItem(mCategories.get(position).getId());
                }
            });
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    class CatHolder extends RecyclerView.ViewHolder {
        CategoryViewBinding mBinding;
        public CatHolder(@NonNull CategoryViewBinding binding) {
            super(binding.getRoot());
            mBinding=binding;
        }

        public void bind(Category categoriesModel){
                mBinding.setCategory(categoriesModel);
        }
    }


    public interface CatAdapterCallbacks{
        void OnSelectedItem(int catId);
    }
}
