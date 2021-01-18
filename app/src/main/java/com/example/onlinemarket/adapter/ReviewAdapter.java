package com.example.onlinemarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.ItemReviewBinding;
import com.example.onlinemarket.model.Review;
import com.example.onlinemarket.viewModel.ReviewViewModel;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private List<Review> mReviewList;
    private ReviewViewModel mViewModel;
    private Context mContext;

    public ReviewAdapter(List<Review> reviewList, ReviewViewModel viewModel, Context context) {
        mReviewList = reviewList;
        mViewModel = viewModel;
        mContext = context;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReviewBinding itemReviewBinding= DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.item_review,
                parent,
                false);

        return new ReviewHolder(itemReviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        holder.bind(mReviewList.get(position));
       holder.mBinding.setViewModel(mViewModel);
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }

    class ReviewHolder extends RecyclerView.ViewHolder{
        ItemReviewBinding mBinding;

        public ReviewHolder(@NonNull ItemReviewBinding binding) {
            super(binding.getRoot());

            mBinding=binding;
        }

        public void bind(Review review){
            mBinding.setReview(review);
        }
    }
}
