package com.example.onlinemarket.viewModel;

import android.location.Address;
import android.location.Geocoder;

import androidx.lifecycle.ViewModel;

import com.example.onlinemarket.di.ContextModule;
import com.example.onlinemarket.model.CustomerLocation;
import com.example.onlinemarket.repository.CustomerLocationRepository;
import com.example.onlinemarket.sharePref.OnlineShopSharePref;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

public class MapViewModel extends ViewModel {
    private CustomerLocationRepository mLocationRepository;
    private GoogleMap mMap;
    private CustomerLocation mLocation;

    private ContextModule mContextModule;

    @Inject
    public MapViewModel(ContextModule contextModule,CustomerLocationRepository customerLocationRepository) {
       mContextModule=contextModule;
        mLocationRepository= customerLocationRepository;
    }

    public void saveLocationOnSharePref() {
        OnlineShopSharePref.setCustomerLastedLocation(mContextModule.provideContext().getApplicationContext(), mLocation);
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
        Geocoder geocoder=new Geocoder(mContextModule.provideContext().getApplicationContext());
        try {
            List<Address> addresses=geocoder.getFromLocation(point.latitude,point.longitude,1);
            String address=addresses.get(0).getAddressLine(0)+"," +addresses.get(0).getLocality();
            mLocation= new CustomerLocation(point.latitude,point.longitude,address);

            mLocationRepository.insert(mLocation);

        } catch (IOException ioException) {
            ioException.printStackTrace();

            mLocation= new CustomerLocation(point.latitude,point.longitude,"متاسفانه سرویس گوگل قادر به یافت آدرس محل مورد نظر شما نیست");

            mLocationRepository.insert(mLocation);
        }
    }

    public void setMap(GoogleMap map) {
        mMap = map;
    }
}
