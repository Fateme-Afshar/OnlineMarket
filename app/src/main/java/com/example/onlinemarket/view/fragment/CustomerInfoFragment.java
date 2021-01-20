package com.example.onlinemarket.view.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.FragmentCustmerBinding;
import com.example.onlinemarket.databinding.FragmentCustomerInfoBinding;
import com.example.onlinemarket.view.OpenProductPage;
import com.example.onlinemarket.viewModel.CustomerInfoViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerInfoFragment extends Fragment {
    public static final int REQUEST_CODE_location_permission = 1;
    private FragmentCustomerInfoBinding mBinding;
    private CustomerInfoFragmentCallback mCallback;

    private CustomerInfoViewModel mViewModel;

    public CustomerInfoFragment() {
        // Required empty public constructor
    }


    public static CustomerInfoFragment newInstance() {
        CustomerInfoFragment fragment = new CustomerInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof CustomerInfoFragmentCallback)
            mCallback=(CustomerInfoFragmentCallback) context;
        else
            throw new ClassCastException(
                    "Must implement CustomerInfoFragmentCallback interface");

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel=new ViewModelProvider(this).get(CustomerInfoViewModel.class);

        mViewModel.setCallback(new CustomerInfoViewModel.CustomerInfoViewModelCallback() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onMapButtonClickListener() {
                checkHasLocationPermission();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkHasLocationPermission() {
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){

        }else if (getActivity().
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
            AlertDialog alertDialog=new AlertDialog.Builder(getContext()).
                    setView(R.layout.location_permission_dialog).
                    setPositiveButton("بله", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    requestPermissions
                            (new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_CODE_location_permission);
                }
            }).setNegativeButton( "خیر" ,null)
                    .create();

            alertDialog.show();
        }else {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_location_permission);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_location_permission:
                if (grantResults.length == 0)
                    return;
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    //TODO: get User Location.
                   mCallback.getMapFragment();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_customer_info,
                container,
                false);

        mBinding.setViewModel(mViewModel);

        return mBinding.getRoot();
    }

    public interface CustomerInfoFragmentCallback{
        void getMapFragment();
    }
}