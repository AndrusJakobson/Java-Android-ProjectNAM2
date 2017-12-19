package com.example.andrus.projectnam.mooddetails;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.andrus.projectnam.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class LocationTracker extends AppCompatActivity {
    private Location currentLocation;
    final static int REQUEST_FINE_LOCATION = 1;
    private static final String TAG = "location";
    private static LocationTracker locationTracker;
    private List<MyLocationListener> myListeners = new ArrayList<>();
    LocationSettingsRequest locationSettingRequest;
    FusedLocationProviderClient fusedLocation;
    LocationCallback locationCallback;
    LocationRequest locationRequest;
    SettingsClient settingsClient;
    MainActivity mainActivity;

    public LocationTracker(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public static LocationTracker getInstance(MainActivity mainActivity) {
        if (locationTracker == null) {
            locationTracker = new LocationTracker(mainActivity);
            locationTracker.initialize();
        }
        return locationTracker;
    }

    public void addListener(MyLocationListener myListener) {
        if (!myListeners.contains(myListener)) {
            myListeners.add(myListener);
        }
    }

    public void removeListener(MyLocationListener myListener) {
        if (myListener != null && myListeners.contains(myListener)) {
            myListeners.remove(myListener);
        }
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }


    public void initialize() {

//        fusedLocation = LocationServices.getFusedLocationProviderClient(getActivity());
        fusedLocation = LocationServices.getFusedLocationProviderClient(mainActivity);
        settingsClient = LocationServices.getSettingsClient(mainActivity);

        createLocationRequest();
        createSettingsRequest();
        createLocationCallback();

        trackLocation();
    }

    protected void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void createSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        locationSettingRequest = builder.build();
    }

    private void createLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                for (Location location : locationResult.getLocations()) {
                    currentLocation = location;
                    for (MyLocationListener myLocationListener : myListeners) {
                        myLocationListener.onLocationUpdate(location);
                    }
                }
            }
        };
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, "onRequestPermissionsResult: Life is pointless if it doesn't get here");
        switch (requestCode) {
            case REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "onRequestPermissionsResult: hello");
                    fusedLocation.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                }
            }
        }
    }

    public void trackLocation() {
        final boolean haveFineLocationAccess = ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED;
        final boolean usersFirstTimeSeeingThis = ActivityCompat.shouldShowRequestPermissionRationale(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION);
        settingsClient.checkLocationSettings(locationSettingRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "Should be first");
                        if (haveFineLocationAccess) {
                            Log.i(TAG, "inside no-permission thing");
                            if (usersFirstTimeSeeingThis) {
                                showDialog();
                            } else {
                                ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
                            }
//                            return;
                        } else {
                            Log.i(TAG, "onSuccess: did ze go here");
                            fusedLocation.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                        }
                    }
                });

        settingsClient.checkLocationSettings(locationSettingRequest)
                .addOnFailureListener(mainActivity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "onFailure: " + e);
                    }
                });
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle("Location access")
                .setMessage("To get applications guidance to location, location access is necessary!")
                .setPositiveButton("I understand!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
                    }
                })
                .setNegativeButton("Don't need it.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

}
