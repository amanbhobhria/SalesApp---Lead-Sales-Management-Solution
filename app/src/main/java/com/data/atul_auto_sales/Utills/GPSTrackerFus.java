package com.data.atul_auto_sales.Utills;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GPSTrackerFus extends Service implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private final Context mContext;
    Location location;
    public double latitude;
    public double longitude;
    private FusedLocationProviderClient fusedLocationClient;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private LocationInterface locationInterface;

    public GPSTrackerFus(Context context, LocationInterface locationInterface) {
        this.mContext = context;
        this.locationInterface = locationInterface;
        buildGoogleApiClient();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);

    }

    @SuppressLint("MissingPermission")
    public void getLocation() {
        try {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(1000);
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            if (ContextCompat.checkSelfPermission((Activity) mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationRequest = new LocationRequest();
                mLocationRequest.setInterval(1000);
                mLocationRequest.setFastestInterval(1000);
                mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                if (ContextCompat.checkSelfPermission((Activity) mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        this.location = location;
        locationInterface.locationReceived(location);
    }


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener((Activity) mContext, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            onLocationChanged(location);
                        } else {
                            getLocation();
                        }
                    }
                });


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public static List<Address> getGeocoderAddress(Context context, Location location) {
        if (location != null) {
            Locale lHebrew = new Locale("en");
            Geocoder geocoder = new Geocoder(context, lHebrew);
//            Geocoder geocoder = new Geocoder(context,Locale.ENGLISH);

            try {
                /**
                 * Geocoder.getFromLocation - Returns an array of Addresses
                 * that are known to describe the area immediately surrounding the given latitude and longitude.
                 */
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                return addresses;
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }

        return null;
    }

    public static String getAddressLine(Context context, Location location) {
        List<Address> addresses = getGeocoderAddress(context, location);

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String addressLine = address.getAddressLine(0);

            return addressLine;
        } else {
            return null;
        }
    }

    /**
     * Try to get Locality
     *
     * @return null or locality
     */
    public static String getLocality(Context context, Location location) {
        List<Address> addresses = getGeocoderAddress(context, location);

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String locality = address.getLocality();

            Log.d("getAddressLine: ",locality);
            return locality;
        } else {
            return null;
        }
    }

    /**
     * Try to get Postal Code
     *
     * @return null or postalCode
     */
    public static String getPostalCode(Context context, Location location) {
        List<Address> addresses = getGeocoderAddress(context, location);

        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String postalCode = address.getPostalCode();

            return postalCode;
        } else {
            return null;
        }
    }

    /**
     * Try to get CountryName
     *
     * @return null or postalCode
     */
    public static String getCountryName(Context context, Location location) {
        List<Address> addresses = getGeocoderAddress(context, location);
        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);
            String countryName = address.getCountryName();

            return countryName;
        } else {
            return null;
        }
    }

    public interface LocationInterface {

        public void locationReceived(Location location);
    }
}
