package com.example.andrus.projectnam.mooddetails;

import android.location.Location;
import android.support.v4.app.Fragment;

import com.example.andrus.projectnam.MainActivity;

public abstract class MyLocationFragment extends Fragment implements MyLocationListener {

    @Override
    public void onResume() {
        super.onResume();

        MainActivity activity = (MainActivity) getActivity();
        activity.addLocationListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MainActivity activity = (MainActivity) getActivity();
        activity.removeLocationListener(this);
    }

    @Override
    public void onLocationUpdate(Location location) {
        onLocationChange(location);
    }

    protected Location getCurrentLocation() {
        MainActivity activity = (MainActivity) getActivity();
        return activity.getCurrentLocation();
    }

    public abstract void onLocationChange(Location location);
}
