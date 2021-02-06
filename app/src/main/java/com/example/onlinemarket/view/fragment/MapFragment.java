package com.example.onlinemarket.view.fragment;

import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.onlinemarket.R;
import com.example.onlinemarket.model.CustomerLocation;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;
import com.example.onlinemarket.viewModel.MapViewModel;
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

    private MapViewModel mViewModel;

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
        mViewModel=new ViewModelProvider(this).get(MapViewModel.class);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        /*        mMap.setMinZoomPreference(6.0f);
        mMap.setMaxZoomPreference(14.0f);*/
        mViewModel.setMap(googleMap);

        CustomerLocation customerLocation =
                OnlineShopSharePref.getCustomerLastedLocation(getActivity());
        if (customerLocation==null)
            return;

        LatLng latLng = new LatLng(customerLocation.getLatitude(), customerLocation.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(latLng).title("my location").draggable(true));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));

        googleMap.setIndoorEnabled(false);

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                mViewModel.setMarker(point);
                mViewModel.getAddressFromGeocoder(point);
                mViewModel.saveLocationOnSharePref();

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