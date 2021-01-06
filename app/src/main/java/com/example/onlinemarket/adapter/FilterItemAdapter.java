package com.example.onlinemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.ItemFilterBinding;
import com.example.onlinemarket.model.AttributeInfo;
import com.example.onlinemarket.viewModel.FilterViewModel;

import java.util.ArrayList;
import java.util.List;

public class FilterItemAdapter extends RecyclerView.Adapter<FilterItemAdapter.Holder> {
    private List<AttributeInfo> mAttributeInfoList;
    private List<Integer> mFilterItemIds = new ArrayList<>();

    private Context mContext;

    private FilterViewModel mViewModel;

    public FilterItemAdapter(Context context, List<AttributeInfo> attributeInfoList,FilterViewModel filterViewModel) {
        mAttributeInfoList = attributeInfoList;
        mContext = context;
        mViewModel=filterViewModel;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFilterBinding binding = DataBindingUtil.inflate
                (LayoutInflater.from(mContext),
                        R.layout.item_filter,
                        parent,
                        false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        AttributeInfo attributeInfo = mAttributeInfoList.get(position);
        holder.bind(attributeInfo);
        holder.mBinding.checkboxItemFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mFilterItemIds.add(attributeInfo.getId());
                mViewModel.setFilterIds(mFilterItemIds);
            }
        });
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

    public List<Integer> getFilterItemIds() {
        return mFilterItemIds;
    }
}
