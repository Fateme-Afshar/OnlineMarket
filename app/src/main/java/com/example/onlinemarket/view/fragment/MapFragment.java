package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.onlinemarket.R;
import com.example.onlinemarket.model.CustomerLocation;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private GoogleMap mMap;

    private Marker mMarkers;
    private LatLng mPoints;

    int markerNumber;

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
        mMap = googleMap;
/*        mMap.setMinZoomPreference(6.0f);
        mMap.setMaxZoomPreference(14.0f);*/

        CustomerLocation customerLocation =
                OnlineShopSharePref.getCustomerLastedLocation(getActivity());

        LatLng latLng = new LatLng(customerLocation.getLatitude(), customerLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(latLng).title("my location").draggable(true));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));

        mMap.setIndoorEnabled(false);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                MarkerOptions marker =
                        new MarkerOptions().
                                position(new LatLng(point.latitude, point.longitude)).
                                title("second location").
                                draggable(true);

                mMap.addMarker(marker).showInfoWindow();
                System.out.println(point.latitude + "---" + point.longitude);
                mPoints = new LatLng(point.latitude, point.longitude);

                CustomerLocation location =
                        new CustomerLocation(mPoints.latitude, mPoints.longitude);

                OnlineShopSharePref.setCustomerLastedLocation(getActivity(), location);

                setupNavigateToCustomerFragment();
            }
        });

    }

    private void setupNavigateToCustomerFragment() {
        NavOptions navOptions=new NavOptions.Builder().
                setEnterAnim(R.anim.fade_in).
                build();

        Navigation.findNavController(
                    getActivity(),
                    R.id.nav_host_fragment).
                    navigate(R.id.nav_customer_page,null,navOptions);

        Toast.makeText
                (getActivity(),"آدرس با موفقیت ثبت شد"
                        , Toast.LENGTH_LONG).
                show();
    }
}