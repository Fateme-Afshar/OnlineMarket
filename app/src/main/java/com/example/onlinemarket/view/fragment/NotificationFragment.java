package com.example.onlinemarket.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.FragmentNotificationBinding;
import com.example.onlinemarket.view.IOnBackPress;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment implements IOnBackPress {
    private FragmentNotificationBinding mBinding;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_notification,
                container,
                false);
        mBinding.numberPickerHours.setMaxValue(24);
        mBinding.numberPickerHours.setMinValue(1);

        mBinding.numberPickerHours.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //TODO: get value and setup in view model
            }
        });
        return mBinding.getRoot();
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}