package com.example.onlinemarket.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.FragmentEditReviewBinding;
import com.example.onlinemarket.model.Review;
import com.example.onlinemarket.view.activity.MainActivity;
import com.example.onlinemarket.viewModel.EditReviewViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditReviewBottomSheetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditReviewBottomSheetFragment extends BottomSheetDialogFragment {
    public static final String ARG_REVIEW = "com.example.onlinemarket.Review";
    public static final String EXTRA_REVIEW_ID = "ReviewId";
    private FragmentEditReviewBinding mBinding;
    @Inject
    EditReviewViewModel mViewModel;

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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        ((MainActivity)getActivity()).getActivityComponent().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel.setReview((Review) getArguments().getSerializable(ARG_REVIEW));

        mViewModel.setCallback(new EditReviewViewModel.EditReviewViewModelCallback() {
            @Override
            public void onEditBtnClickListener(Review review) {
                mViewModel.updateReview(review.getId(),review);
                sendData(review.getId());
                dismiss();
            }

            @Override
            public void onCancelBtnClickListener() {
                dismiss();
            }
        });

    }

    private void sendData(int reviewId) {
        int requestCode= EditReviewBottomSheetFragment.this.getTargetRequestCode();
        Fragment fragment=EditReviewBottomSheetFragment.this.getTargetFragment();
        Intent intent=new Intent();
        intent.putExtra(EXTRA_REVIEW_ID,reviewId);
        fragment.onActivityResult(requestCode, Activity.RESULT_OK,intent);
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