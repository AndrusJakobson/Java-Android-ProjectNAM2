package com.example.andrus.projectnam.mooddetails;

import com.example.andrus.projectnam.models.DetailMood;
import com.example.andrus.projectnam.models.MapsResponse;
import com.example.andrus.projectnam.models.OfferListByCategory;

class OfferDetailPresenter {
    private OfferDetailView view;
    private DetailMood details;

    OfferDetailPresenter(OfferDetailView view) {
        this.view = view;
    }

    void getDetails() {
        MoodDetailRequester.getOfferDetails(this);
    }

    void onDetailsResponseSuccess(DetailMood detailMood) {
        this.details = detailMood;
        view.setDetailText(detailMood);
        view.setOfferTitle(detailMood.offerTitle);
        view.setOfferText(detailMood.getFirstOfferListCategory().offerDescription);
        if (view.hasDeviceLocation()) {
            view.requestWalkingTime();
        }
    }

    void onDetailResponseFailure(Throwable t) {
        
    }

    public void getWalkingTime(double myLatitude, double myLongitude) {
        if (details != null) {
            String link = generatePathLink(myLatitude, myLongitude);
            LocationRequester.getWalkingTimeFromMaps(this, link);
        }
    }

    void onWalkingTimeRequestSuccess(MapsResponse mapsResponse) {
        view.setDurationText(mapsResponse.getCertifiedDuration());
    }

    void onWalkingTimeRequestFailure(Throwable t) {
        view.setDurationText(null);
    }

    private String generatePathLink(double myLatitude, double myLongitude) {
        OfferListByCategory offerList = details.getFirstOfferListCategory();
        String urlBase = "https://maps.googleapis.com/maps/api/distancematrix/json?";
        String urlOrigin = "origins=";
        String originCoord = String.valueOf(myLatitude) + "," + String.valueOf(myLongitude);
        String urlDestination = "&destinations=";
        String destCoord = String.valueOf(offerList.locationLat) + "," + String.valueOf(offerList.locationLong);
        String modeOfTransport = "&mode=walking";
        String urlKey = "&key=AIzaSyCDs0OO07j5fJsosn2kuUK7A4s6Z2Y4zSw";

        return urlBase + urlOrigin + originCoord + urlDestination + destCoord + modeOfTransport + urlKey;
    }
}
