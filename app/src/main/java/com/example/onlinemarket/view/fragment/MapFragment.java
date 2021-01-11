package com.example.onlinemarket.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.onlinemarket.R;
import com.example.onlinemarket.databinding.FragmentMapBinding;
import com.example.onlinemarket.utils.ProgramUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class MapFragment extends Fragment {
    public static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private FragmentMapBinding mBinding;

    private FusedLocationProviderClient mFusedLocation;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (hasLocationAccess()) {
            Log.d(ProgramUtils.TAG, "MapFragment : has Fine and Coarse permission");
        } else {
            requestToLocationPermission();
        }

        mFusedLocation= LocationServices.getFusedLocationProviderClient(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_map,
                container,
                false);

        return mBinding.getRoot();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_LOCATION_PERMISSION:
                if (grantResults == null || grantResults.length == 0)
                    return;
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    getLocation();
                    return;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getLocation();
    }

    private boolean hasLocationAccess() {
        Log.d(ProgramUtils.TAG, "MapFragment : check Fine and Coarse permission");
        boolean isFindPermission = ActivityCompat.checkSelfPermission(
                getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;

        boolean isCoarsePermission = ActivityCompat.checkSelfPermission(
                getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
        return isFindPermission && isCoarsePermission;
    }

    private void requestToLocationPermission() {
        requestPermissions(
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_CODE_LOCATION_PERMISSION);
    }

    @SuppressLint("MissingPermission")
    private void getLocation(){
        if (!hasLocationAccess())
            return;

        LocationRequest locationRequest=LocationRequest.create();
        locationRequest.setNumUpdates(1);
        locationRequest.setInterval(0);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationCallback locationCallback=new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLocations().get(0);

                Log.d(ProgramUtils.TAG,
                        " Latitude : " + location.getLatitude()+" Longitude : "+location.getLongitude());
            }
        };
        mFusedLocation.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }
}