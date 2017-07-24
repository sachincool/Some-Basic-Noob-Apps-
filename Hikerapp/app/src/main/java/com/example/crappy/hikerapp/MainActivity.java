package com.example.crappy.hikerapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends Activity implements LocationListener {
    LocationManager locationManager;
    String provider = "";
    TextView lattv, lngtv, accuracytv, speedvt, bearingtv, addresstv;
    Double lat, lng;
    Float speed, bearing, accuracy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
       locationManager.requestLocationUpdates(provider, 0, 0, this);
        Location location = locationManager.getLastKnownLocation(provider);
        lattv = (TextView) findViewById(R.id.latitude);
        lngtv = (TextView) findViewById(R.id.longitude);
        accuracytv = (TextView) findViewById(R.id.Accuracy);
        speedvt = (TextView) findViewById(R.id.speed);
        bearingtv = (TextView) findViewById(R.id.Bearing);
        addresstv = (TextView) findViewById(R.id.address);
     /*  Log.i("Latitude", lat.toString());
        Log.i("Longtitude", lng.toString());
        Log.i("accuitude", accuracy.toString());
        Log.i("speeditude", speed.toString());
        Log.i("beartude", bearing.toString());
       */ onLocationChanged(location);
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();

        bearing = location.getBearing();
        speed = location.getSpeed();
        accuracy = location.getAccuracy();

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(lat, lng, 1);
            if (addressList != null && addressList.size() > 0) {
                String addressholder = "";


                for (int i = 0; i <= addressList.get(0).getMaxAddressLineIndex(); i++) {
                    addressholder += addressList.get(0).getAddressLine(i) + "\n";
                }
                addresstv.setText("Address:\n" + addressholder);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        lattv.setText("Latitude:" + lat.toString());
        lngtv.setText("Longitude:" + lng.toString());
        accuracytv.setText("Accuracy:" + String.valueOf(accuracy) + "m");
        speedvt.setText("Speed:" + String.valueOf(speed) + "m/s");
        bearingtv.setText("Bearing:" + bearing.toString());


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
