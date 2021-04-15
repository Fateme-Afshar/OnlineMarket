package com.example.onlinemarket.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.FragmentFilterProductBottomSheetBinding;
import com.example.onlinemarket.view.activity.MainActivity;
import com.example.onlinemarket.viewModel.FilterProductOnMoreViewModel;
import com.example.onlinemarket.viewModel.FilterViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import javax.inject.Inject;

public class FilterProductBottomSheetFragment extends BottomSheetDialogFragment {
    public static final String EXTRA_ORDER_BY = "com.example.onlinemarket.OrderBy";
    public static final String EXTRA_ORDER = "com.example.onlinemarket.order";
    private FragmentFilterProductBottomSheetBinding mBinding;
    @Inject
    FilterProductOnMoreViewModel mViewModel;

    public FilterProductBottomSheetFragment() {
        // Required empty public constructor
    }


    public static FilterProductBottomSheetFragment newInstance() {
        FilterProductBottomSheetFragment fragment = new FilterProductBottomSheetFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        ((MainActivity)getActivity()).getActivityComponent().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel.setOnBtnClickListener(new FilterProductOnMoreViewModel.FilterProductOnMoreViewModelCallback() {
            @Override
            public void onFilterBtnClickListener(String orderby, String order) {
                Fragment fragment=FilterProductBottomSheetFragment.this.getTargetFragment();
                int requestCode=FilterProductBottomSheetFragment.this.getTargetRequestCode();
                Intent intent=new Intent();
                intent.putExtra(EXTRA_ORDER_BY,orderby);
                intent.putExtra(EXTRA_ORDER,order);
                fragment.onActivityResult(requestCode, Activity.RESULT_OK,intent);
                dismiss();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate
                (inflater,
                        R.layout.fragment_filter_product_bottom_sheet,
                        container,
                        false);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }
}