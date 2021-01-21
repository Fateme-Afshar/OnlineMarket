package com.example.onlinemarket.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.FragmentCustomerInfoBinding;
import com.example.onlinemarket.model.CustomerLocation;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;
import com.example.onlinemarket.viewModel.CustomerInfoViewModel;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerInfoFragment extends Fragment {
    public static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    public static final int REQUEST_CHECK_LOCATION_SETTING = 1;
    private FragmentCustomerInfoBinding mBinding;
    private CustomerInfoFragmentCallback mCallback;

    private FusedLocationProviderClient mFusedLocation;

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
                getCustomerLocation();
            }
        });

        mFusedLocation=LocationServices.getFusedLocationProviderClient(getActivity());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkHasLocationPermission() {
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            getCustomerLocation();
        }else if (getActivity().
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
            AlertDialog alertDialog=new AlertDialog.Builder(getContext()).
                    setView(R.layout.location_permission_dialog).
                    setPositiveButton("بله", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    requestPermissions
                            (new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_CODE_LOCATION_PERMISSION);
                }
            }).setNegativeButton( "خیر" ,null)
                    .create();

            alertDialog.show();
        }else {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_LOCATION_PERMISSION:
                if (grantResults.length == 0)
                    return;
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getCustomerLocation();
                   mCallback.getMapFragment();
                }
        }
    }

    private void getCustomerLocation() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder=
                new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient client= LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task=client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                LocationCallback locationCallback=new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        Location location=locationResult.getLocations().get(0);

                        CustomerLocation customerLocation=
                                new CustomerLocation(location.getLatitude(),location.getLongitude());
                    if(OnlineShopSharePref.getCustomerLastedLocation(getActivity())==null)
                        OnlineShopSharePref.setCustomerLastedLocation(getActivity(),customerLocation);
                    }
                };

                mFusedLocation.requestLocationUpdates
                        (locationRequest,
                                locationCallback,
                                Looper.getMainLooper());

                mCallback.getMapFragment();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(getActivity(), REQUEST_CHECK_LOCATION_SETTING);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode!= Activity.RESULT_OK && data==null)
            return;
        if (requestCode==REQUEST_CHECK_LOCATION_SETTING){
            getCustomerLocation();
        }
    }

    public interface CustomerInfoFragmentCallback{
        void getMapFragment();
    }


}