package com.example.onlinemarket.viewModel;

import android.app.Application;
import android.location.Address;
import android.location.Geocoder;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.onlinemarket.OnlineShopApplication;
import com.example.onlinemarket.model.CustomerLocation;
import com.example.onlinemarket.repository.CustomerLocationRepository;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapViewModel extends AndroidViewModel {
    private CustomerLocationRepository mLocationRepository;
    private GoogleMap mMap;
    private CustomerLocation mLocation;

    public MapViewModel(@NonNull Application application) {
        super(application);
        mLocationRepository= OnlineShopApplication.getLocationRepository();
    }

    public void saveLocationOnSharePref() {
        OnlineShopSharePref.setCustomerLastedLocation(getApplication(), mLocation);
    }

    public void setMarker(LatLng point) {
        MarkerOptions marker =
                new MarkerOptions().
                        position(new LatLng(point.latitude, point.longitude)).
                        title("second location").
                        draggable(true);

        mMap.addMarker(marker).showInfoWindow();
    }

    public void getAddressFromGeocoder(LatLng point) {
        Geocoder geocoder=new Geocoder(getApplication());
        try {
            List<Address> addresses=geocoder.getFromLocation(point.latitude,point.longitude,1);
            String address=addresses.get(0).getAddressLine(0)+"," +addresses.get(0).getLocality();
            mLocation= new CustomerLocation(point.latitude,point.longitude,address);

            mLocationRepository.insert(mLocation);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void setMap(GoogleMap map) {
        mMap = map;
    }
}
