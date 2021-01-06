package com.example.onlinemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.ItemFilterBinding;
import com.example.onlinemarket.model.AttributeInfo;

import java.util.List;

public class FilterItemAdapter extends RecyclerView.Adapter<FilterItemAdapter.Holder> {
    private List<AttributeInfo> mAttributeInfoList;
    private Context mContext;

    public FilterItemAdapter(List<AttributeInfo> attributeInfoList, Context context) {
        mAttributeInfoList = attributeInfoList;
        mContext = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFilterBinding binding= DataBindingUtil.inflate
                (LayoutInflater.from(mContext),
                        R.layout.item_filter,
                        parent,
                        false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(mAttributeInfoList.get(position));
    }

    @Override
    public int getItemCount() {
        return mAttributeInfoList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private ItemFilterBinding mBinding;

        public Holder(@NonNull ItemFilterBinding binding) {
            super(binding.getRoot());
            mBinding=binding;
        }

        public void bind(AttributeInfo attributeInfo){
            mBinding.setAttributeInfo(attributeInfo);
        }
    }
}
