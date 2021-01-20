package com.example.onlinemarket.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.onlinemarket.utils.ProgramUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapTestFragment extends SupportMapFragment implements OnMapReadyCallback {
    public static final int REQUEST_CODE_LOCATION_PERMISSION = 1;

    private FusedLocationProviderClient mFusedLocation;

    GoogleMap mMap;

    public MapTestFragment() {
        // Required empty public constructor
    }

    public static MapTestFragment newInstance() {
        MapTestFragment fragment = new MapTestFragment();
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
        getMapAsync(this);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(35.9098277, 50.6451725);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}