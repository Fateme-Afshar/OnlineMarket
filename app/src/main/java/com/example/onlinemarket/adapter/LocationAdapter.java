package com.example.onlinemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.ItemLocationViewBinding;
import com.example.onlinemarket.model.CustomerLocation;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.Holder> {

    private Context mContext;
    private List<CustomerLocation> mCustomerLocationList;

    public LocationAdapter(Context context, List<CustomerLocation> customerLocationList) {
        mContext = context;
        mCustomerLocationList = customerLocationList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLocationViewBinding binding= DataBindingUtil.inflate
                (LayoutInflater.from(mContext),
                        R.layout.item_location_view,
                        parent,
                        false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.binding(mCustomerLocationList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCustomerLocationList.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        private ItemLocationViewBinding mBinding;

        public Holder(@NonNull ItemLocationViewBinding binding) {
            super(binding.getRoot());
            mBinding=binding;
        }

        public void binding(CustomerLocation location){

        }
    }
}
