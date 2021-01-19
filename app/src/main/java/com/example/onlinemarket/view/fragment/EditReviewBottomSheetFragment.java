package com.example.onlinemarket.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.FragmentEditReviewBinding;
import com.example.onlinemarket.model.Review;
import com.example.onlinemarket.viewModel.EditReviewViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditReviewBottomSheetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditReviewBottomSheetFragment extends BottomSheetDialogFragment {
    public static final String ARG_REVIEW = "Review";
    private FragmentEditReviewBinding mBinding;

    private EditReviewViewModel mViewModel;

    public EditReviewBottomSheetFragment() {
        // Required empty public constructor
    }


    public static EditReviewBottomSheetFragment newInstance(Review review) {
        EditReviewBottomSheetFragment fragment = new EditReviewBottomSheetFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_REVIEW,review);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel=new ViewModelProvider(this).get(EditReviewViewModel.class);
        mViewModel.setReview((Review) getArguments().getSerializable(ARG_REVIEW));

        mViewModel.setCallback(new EditReviewViewModel.EditReviewViewModelCallback() {
            @Override
            public void onEditBtnClickListener(Review review) {
                mViewModel.updateReview(review.getId(),review);
                dismiss();
            }

            @Override
            public void onCancelBtnClickListener() {
                dismiss();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_edit_review,
                container,
                false);

        mBinding.setViewModel(mViewModel);

        return mBinding.getRoot();
    }
}