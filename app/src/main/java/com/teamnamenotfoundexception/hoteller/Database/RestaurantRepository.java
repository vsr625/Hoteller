package com.teamnamenotfoundexception.hoteller.Database;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class RestaurantRepository {
    private double lat, log;
    private static RestaurantRepository mRestRepository = null;
    private static FirebaseHelper mFirebaseHelper;
    private static ArrayList<Restaurant> mRestList = null;
    private static Context mContext;

    public ArrayList<Restaurant> getmRestList() {
        return mRestList;
    }

    private RestaurantRepository(Context c) {
        mContext = c;
        mFirebaseHelper = new FirebaseHelper(c);
        mRestList = new ArrayList<>();
    }

    public static RestaurantRepository get(Context c) {
        if (mRestRepository == null)
            mRestRepository = new RestaurantRepository(c);
        return mRestRepository;
    }

    public void initializeNearByRestaurants() {
        mRestList = new ArrayList<>();
        getGpsCoordinates();
        mRestList.addAll(mFirebaseHelper.fetchNearByRestaurants(lat, log));
    }

    private void getGpsCoordinates() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (locationManager != null) {
            Location l = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lat = l.getLatitude();
            log = l.getLongitude();
            Log.i("Debug", "onLocationChanged: First " + lat + " -- " + log);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        }
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location loc) {
            lat = loc.getLatitude();
            log = loc.getLongitude();
            Log.i("Debug", "onLocationChanged: " + lat + " -- " + log);
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }
}
