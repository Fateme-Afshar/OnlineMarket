package com.example.onlinemarket.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.FilterItemAdapter;
import com.example.onlinemarket.databinding.FragmentFilterBottomSheetBinding;
import com.example.onlinemarket.model.AttributeInfo;
import com.example.onlinemarket.viewModel.FilterViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterItemBottomSheetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterItemBottomSheetFragment extends BottomSheetDialogFragment {
    public static final String ARG_ATTRIBUTE_ID = "Attribute Id";
    private FilterViewModel mViewModel;
    private int mAttributeId;

    private FilterItemAdapter mAdapter;

    private FragmentFilterBottomSheetBinding mBinding;

    public FilterItemBottomSheetFragment() {
        // Required empty public constructor
    }

    public static FilterItemBottomSheetFragment newInstance(int attributeId) {
        FilterItemBottomSheetFragment fragment = new FilterItemBottomSheetFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putInt(ARG_ATTRIBUTE_ID,attributeId);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAttributeId=getArguments().getInt(ARG_ATTRIBUTE_ID);
        }
        mViewModel=new ViewModelProvider(this).get(FilterViewModel.class);
        mViewModel.requestToServerForReceiveInfoSectionAttribute(mAttributeId);
        mViewModel.getAttributeListLiveData().observe(this, new Observer<List<AttributeInfo>>() {
            @Override
            public void onChanged(List<AttributeInfo> attributeInfoList) {
                setupAdapter(attributeInfoList);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate(inflater,
                R.layout.fragment_filter_bottom_sheet,
                container,
                false);

        return mBinding.getRoot();
    }

    private void setupAdapter(List<AttributeInfo> attributeInfoList) {
        mAdapter=new FilterItemAdapter(attributeInfoList,getContext());
        mBinding.filterRecycler.setAdapter(mAdapter);
        mBinding.filterRecycler.setLayoutManager(new GridLayoutManager(getContext(),2));
    }
}