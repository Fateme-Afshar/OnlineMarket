package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.FragmentNotificationBinding;
import com.example.onlinemarket.viewModel.NotificationViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment{
    private FragmentNotificationBinding mBinding;
    private NotificationViewModel mViewModel;
    public NotificationFragment() {
        // Required empty public constructor
    }

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel=new ViewModelProvider(this).get(NotificationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_notification,
                container,
                false);
        //setupNumberPicker();
        mBinding.setViewModel(mViewModel);

        setupNotificationText();

        return mBinding.getRoot();
    }

    private void setupNotificationText() {
                    if (mViewModel.isFlag()){
                        mBinding.tvStateNotification.setText(R.string.on_notification);
                    }else {
                        mBinding.tvStateNotification.setText(R.string.off_notification);
                    }
                    mBinding.notifyChange();
                }
    }

    /*private void setupNumberPicker() {
        mBinding.numberPickerHours.setMaxValue(24);
        mBinding.numberPickerHours.setMinValue(1);

        mBinding.numberPickerHours.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                PollWorkManager.enqueue(getContext(),newVal,false);
            }
        });
    }*/