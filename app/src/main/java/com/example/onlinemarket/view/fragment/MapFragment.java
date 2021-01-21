package com.example.onlinemarket.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlinemarket.R;
import com.example.onlinemarket.model.CustomerLocation;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private GoogleMap mMap;

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
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap=googleMap;
/*        mMap.setMinZoomPreference(6.0f);
        mMap.setMaxZoomPreference(14.0f);*/

        CustomerLocation customerLocation=
                OnlineShopSharePref.getCustomerLastedLocation(getActivity());

        LatLng latLng = new LatLng(customerLocation.getLatitude(), customerLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title("my location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,12.0f));
    }
}