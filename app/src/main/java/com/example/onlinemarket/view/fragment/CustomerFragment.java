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

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlinemarket.R;
import com.example.onlinemarket.adapter.LocationAdapter;
import com.example.onlinemarket.databinding.FragmentCustomerBinding;

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

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerFragment extends Fragment {
    public static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    public static final int REQUEST_CHECK_LOCATION_SETTING = 1;
    private FragmentCustomerBinding mBinding;
    private CustomerInfoFragmentCallback mCallback;

    private FusedLocationProviderClient mFusedLocation;

    private CustomerInfoViewModel mViewModel;

    public CustomerFragment() {
        // Required empty public constructor
    }


    public static CustomerFragment newInstance() {
        CustomerFragment fragment = new CustomerFragment();
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
        setupViewModel();
        mFusedLocation=LocationServices.getFusedLocationProviderClient(getActivity());
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_customer,
                container,
                false);
        setupAdapter();
        mBinding.setViewModel(mViewModel);
        setupBackButton();
        return mBinding.getRoot();
    }

    private void setupBackButton() {
        OnBackPressedCallback onBackPressedCallback=new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavController navController=
                        Navigation.findNavController(getActivity(),R.id.nav_host_fragment);

                navController.navigate(R.id.nav_home);
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(onBackPressedCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode!= Activity.RESULT_OK && data==null)
            return;
        if (requestCode==REQUEST_CHECK_LOCATION_SETTING){
            requestLocationUpdate();
            mCallback.getMapFragment();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_LOCATION_PERMISSION:
                if (grantResults.length == 0)
                    return;
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    setupLocationSetting();
                }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean checkHasLocationPermission() {
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            return true;
        }else if (getActivity().
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
            showExpositoryAlertDialog();
        }else {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION);
        }
        return false;
    }

    private void setupAdapter() {

        mViewModel.getCustomerLocationList().observe(getViewLifecycleOwner(), new Observer<List<CustomerLocation>>() {
            @Override
            public void onChanged(List<CustomerLocation> customerLocations) {
                LocationAdapter locationAdapter=new LocationAdapter(getContext(),customerLocations);
                mBinding.recyclerView.setAdapter(locationAdapter);
            }
        });

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupViewModel() {
        mViewModel=new ViewModelProvider(this).get(CustomerInfoViewModel.class);

        mViewModel.setCallback(new CustomerInfoViewModel.CustomerInfoViewModelCallback() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onMapButtonClickListener() {
                if (checkHasLocationPermission())
                    setupLocationSetting();
            }
        });
    }

    private void setupLocationSetting() {

        LocationSettingsRequest.Builder builder=
                new LocationSettingsRequest.Builder().addLocationRequest(getLocationRequest());

        SettingsClient client= LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task=client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                mCallback.getMapFragment();
                requestLocationUpdate();
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

    public interface CustomerInfoFragmentCallback{
        void getMapFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showExpositoryAlertDialog() {
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
    }

    private void saveFirstCustomerLocationIntoSharePref(Location location) {
        if (location==null)
            return;
        CustomerLocation customerLocation=
                new CustomerLocation(location.getLatitude(),location.getLongitude());
        if(OnlineShopSharePref.getCustomerLastedLocation(getActivity())==null)
            OnlineShopSharePref.setCustomerLastedLocation(getActivity(),customerLocation);
    }

    @SuppressLint("MissingPermission")
    private void requestLocationUpdate() {
        LocationRequest locationRequest = getLocationRequest();

        LocationCallback locationCallback=new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location=locationResult.getLocations().get(0);

                saveFirstCustomerLocationIntoSharePref(location);
            }
        };

        mFusedLocation.requestLocationUpdates
                (locationRequest,
                        locationCallback,
                        Looper.getMainLooper());
    }

    @NotNull
    private LocationRequest getLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

}