package com.example.andrus.projectnam.mooddetails;

import android.util.Log;

import com.example.andrus.projectnam.models.MapsResponse;

public class LocationPresenter {
    private LocationInterface locationInterface;

    LocationPresenter(LocationInterface locationInterface) {
        this.locationInterface = locationInterface;
    }

    public void getMapsResponse(String link) {
        LocationRequester.getMapsData(this, link);
    }

    void onCallSuccess(MapsResponse mapsResponse) {
        Log.i("locationPresenter", "onCallSuccess: "+mapsResponse);
        locationInterface.setDurationText(mapsResponse);
    }

    void onCallFailure(Throwable t) {
        Log.i("locationPresenter", "onCallFailure: "+t);
        locationInterface.setDurationText(null);
    }
}
