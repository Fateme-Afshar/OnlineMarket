package com.example.onlinemarket.viewModel;

import androidx.lifecycle.ViewModel;

public class FilterProductOnMoreViewModel extends ViewModel {
    private FilterProductOnMoreViewModelCallback mOnBtnClickListener;

    public void onMoreFilterBtnClickListener(String orderby,String order){
        mOnBtnClickListener.onFilterBtnClickListener(orderby,order);
    }

    public void setOnBtnClickListener(FilterProductOnMoreViewModelCallback onBtnClickListener) {
        mOnBtnClickListener = onBtnClickListener;
    }

    public interface FilterProductOnMoreViewModelCallback{
        void onFilterBtnClickListener(String orderby,String order);
    }
}
