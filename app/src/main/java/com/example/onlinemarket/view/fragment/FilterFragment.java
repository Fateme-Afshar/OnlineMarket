package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.FragmentFilterBinding;
import com.example.onlinemarket.model.AttributeInfo;
import com.example.onlinemarket.view.IOnBackPress;
import com.example.onlinemarket.viewModel.FilterViewModel;

import java.util.List;


public class FilterFragment extends Fragment implements IOnBackPress {
    public static final int REQUEST_CODE_FILTER_BOTTOM_SHEET = 1;
    public static final String TAG_FILTER_FRAGMENT = "Filter Fragment";
    private FilterViewModel mViewModel;
    private FragmentFilterBinding mBinding;

    public FilterFragment() {
        // Required empty public constructor
    }

    public static FilterFragment newInstance() {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel=new ViewModelProvider(getActivity()).get(FilterViewModel.class);
        mViewModel.setOnBtnClickListener(new FilterViewModel.OnBtnClickListener() {
            @Override
            public void onAttributeSelected(int attributeId) {
                FilterItemBottomSheetFragment filterItemBottomSheetFragment=
                        FilterItemBottomSheetFragment.newInstance(attributeId);

                //create parent-child relationship between FilterFragment and FilterItemBottomSheetFragment
                filterItemBottomSheetFragment.
                        setTargetFragment(FilterFragment.this,
                                REQUEST_CODE_FILTER_BOTTOM_SHEET);

                filterItemBottomSheetFragment.show(getParentFragmentManager(),
                        TAG_FILTER_FRAGMENT);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate
                (inflater,
                        R.layout.fragment_filter,
                        container,
                        false);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}