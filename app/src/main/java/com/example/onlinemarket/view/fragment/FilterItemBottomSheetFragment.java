package com.example.onlinemarket.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.FilterItemAdapter;
import com.example.onlinemarket.databinding.FragmentFilterBottomSheetBinding;
import com.example.onlinemarket.model.AttributeInfo;
import com.example.onlinemarket.view.activity.MainActivity;
import com.example.onlinemarket.viewModel.FilterViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterItemBottomSheetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterItemBottomSheetFragment extends BottomSheetDialogFragment {
    public static final String ARG_ATTRIBUTE_ID = "Attribute Id";
    public static final String EXTRA_FILTER_ITEM_IDS =
            "com.example.onlinemarket.FilterItemIds";
    public static final String EXTRA_FILTER_ATTRIBUTE_ID =
            "com.example.onlinemarket.FilterAttributeId";
    @Inject
    FilterViewModel mViewModel;
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MainActivity)getActivity()).getActivityComponent().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAttributeId=getArguments().getInt(ARG_ATTRIBUTE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_filter_bottom_sheet,
                container,
                false);

        mBinding.btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment=FilterItemBottomSheetFragment.this.getTargetFragment();
                int fragmentTargetCode=FilterItemBottomSheetFragment.this.getTargetRequestCode();
                Intent intent=new Intent();
                intent.putExtra(EXTRA_FILTER_ITEM_IDS,mAdapter.getFilterItemIds().toArray());
                intent.putExtra(EXTRA_FILTER_ATTRIBUTE_ID,mAttributeId);
                fragment.onActivityResult(fragmentTargetCode,Activity.RESULT_OK,intent);
                dismiss();
            }
        });
        setupAttributeList();
        return mBinding.getRoot();
    }

    private void setupAttributeList() {
        if (mAttributeId==3)
            setupAdapter(mViewModel.getColorAttributeInfoList());
        else if (mAttributeId==4)
            setupAdapter(mViewModel.getSizeAttributeInfoList());
    }

    private void setupAdapter(List<AttributeInfo> attributeInfoList) {
        mAdapter = new FilterItemAdapter(getContext(), attributeInfoList,mViewModel);
        mBinding.filterRecycler.setAdapter(mAdapter);
        mBinding.filterRecycler.setLayoutManager(new GridLayoutManager(getContext(),2));
    }
}