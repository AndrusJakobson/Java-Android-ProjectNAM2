package com.example.andrus.projectnam;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.andrus.projectnam.mooddetails.MyLocationListener;
import com.example.andrus.projectnam.moodgrid.MoodGridFragment;
import com.google.android.gms.common.api.ResolvableApiException;
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

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_FINE_LOCATION = 1;
    private FusedLocationProviderClient locationClient;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Location currentLocation;
    private List<MyLocationListener> myListeners = new ArrayList<>();
    private String TAG = "PROJECT NAM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showMainScreenFragment();
    }

    private void showMainScreenFragment() {
        MoodGridFragment moodGridFragment = new MoodGridFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.replace(R.id.mainActivity_frameLayout, moodGridFragment);

        transaction.commit();
    }

    public void requestFineLocation() {

        if (currentLocation != null) {
            for (MyLocationListener locationListener : myListeners) {
                locationListener.onLocationUpdate(currentLocation);
            }
        }

        if (locationClient == null) {
            locationClient = LocationServices.getFusedLocationProviderClient(this);
        }

        if (settingsClient == null) {
            settingsClient = LocationServices.getSettingsClient(this);
        }

        LocationRequest locationRequest = getLocationRequest();
        LocationSettingsRequest settingsRequest = createSettingsRequest(locationRequest);
        LocationCallback locationCallback = getLocationCallback();

        trackLocation(settingsRequest, locationRequest, locationCallback);
    }

    protected LocationRequest getLocationRequest() {
        if (locationRequest == null) {
            locationRequest = new LocationRequest();
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(5000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }
        return locationRequest;
    }

    private LocationSettingsRequest createSettingsRequest(LocationRequest locationRequest) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        return builder.build();
    }

    private LocationCallback getLocationCallback() {
        if (locationCallback == null) {
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    for (Location location : locationResult.getLocations()) {
                        currentLocation = location;
                        notifyLocationListeners(location);
                    }
                }
            };
        }
        return locationCallback;
    }

    public void trackLocation(LocationSettingsRequest settingsRequest, final LocationRequest locationRequest, final LocationCallback locationCallback) {
        final boolean hasFineLocationPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        final boolean usersFirstTimeSeeingThis = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION);
        settingsClient.checkLocationSettings(settingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        if (hasFineLocationPermission) {
                            locationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                        } else {
                            if (usersFirstTimeSeeingThis) {
                                showDialog();
                            } else {
                                requestLocationPermission();
                            }
                        }
                    }
                });

        settingsClient.checkLocationSettings(settingsRequest)
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "onFailure: " + e);
                        if (e instanceof ResolvableApiException) {
                            ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                            try {
                                resolvableApiException.startResolutionForResult(MainActivity.this, 0);
                            } catch (IntentSender.SendIntentException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void notifyLocationListeners(Location location) {
        for (MyLocationListener myLocationListener : myListeners) {
            myLocationListener.onLocationUpdate(location);
        }
    }

    private void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Location access")
                .setMessage("To get applications guidance to location, location access is necessary!")
                .setPositiveButton("I understand!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestLocationPermission();
                    }
                })
                .setNegativeButton("Don't need it.", null)
                .show();
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_LOCATION);
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
                    locationClient.requestLocationUpdates(getLocationRequest(), getLocationCallback(), Looper.myLooper());
                }
            }
        }
    }

    public void addLocationListener(MyLocationListener listener) {
        if (!myListeners.contains(listener)) {
            myListeners.add(listener);
        }
    }

    public void removeLocationListener(MyLocationListener listener) {
        if (myListeners.contains(listener)) {
            myListeners.remove(listener);
        }
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }
}
