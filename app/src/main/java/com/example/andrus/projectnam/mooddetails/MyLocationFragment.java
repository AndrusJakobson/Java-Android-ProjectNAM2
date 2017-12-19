package com.example.andrus.projectnam.mooddetails;

import android.location.Location;
import android.support.v4.app.Fragment;

import com.example.andrus.projectnam.MainActivity;

public abstract class MyLocationFragment extends Fragment implements MyLocationListener {
    LocationTracker locationTracker;

    @Override
    public void onResume() {
        super.onResume();
        locationTracker = LocationTracker.getInstance((MainActivity) getActivity());
        locationTracker.addListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        locationTracker.removeListener(this);
    }

    @Override
    public void onLocationUpdate(Location location) {
        onLocationChange(location);
    }

    public abstract void onLocationChange(Location location);
}
