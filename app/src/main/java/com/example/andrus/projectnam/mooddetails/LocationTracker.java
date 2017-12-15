package com.example.andrus.projectnam.mooddetails;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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

public class LocationTracker extends AppCompatActivity {
    public static boolean tracking = false;
    private static final String TAG = "simple";
    LocationSettingsRequest locationSettingRequest;
    FusedLocationProviderClient fusedLocation;
    LocationInterface locationInterface;
    LocationCallback locationCallback;
    LocationRequest locationRequest;
    SettingsClient settingsClient;
    MainActivity mainActivity;

    public LocationTracker(MainActivity mainActivity, LocationInterface locationInterface) {
        this.mainActivity = mainActivity;
        this.locationInterface = locationInterface;
    }

    public void initialize() {
        tracking = true;

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
                    locationInterface.locationCallBack(location);
                }
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void trackLocation() {
        settingsClient.checkLocationSettings(locationSettingRequest)
                .addOnSuccessListener(mainActivity, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        if (ActivityCompat.checkSelfPermission(mainActivity.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        }
                        fusedLocation.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    }
                });

        settingsClient.checkLocationSettings(locationSettingRequest)
                .addOnFailureListener(mainActivity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "onFailure: Failure");
                    }
                });
    }
}
