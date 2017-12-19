package com.example.andrus.projectnam.mooddetails;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andrus.projectnam.MainActivity;
import com.example.andrus.projectnam.R;
import com.example.andrus.projectnam.models.DetailMood;
import com.example.andrus.projectnam.models.MapsResponse;
import com.example.andrus.projectnam.models.OfferListByCategory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailThirstyFragment extends MyLocationFragment implements LocationInterface {
    @BindView(R.id.fragmentDetailThirsty_header)
    TextView detailHeader;
    @BindView(R.id.fragmentDetailThirsty_deal)
    TextView detailDeal;
    @BindView(R.id.fragmentDetailThirsty_advertisement)
    TextView detailAdvertisement;

    boolean detailListIsSet = false;
    final public static String TAG = "location";
    OfferListByCategory offerList;
    DetailMood detailList = null;
    Location location = null;

    public static DetailThirstyFragment newInstance() {
        return new DetailThirstyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_detail_thirsty, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        new MoodDetailPresenter(this).getViewData();
        locationTracker = LocationTracker.getInstance((MainActivity) getActivity());
        location = locationTracker.getCurrentLocation();

    }

    @Override
    public void onLocationChange(Location location) {
        this.location = location;
        setTextToView();
    }

    @Override
    public void setDetailText(DetailMood detailList) {
        this.detailList = detailList;
        detailListIsSet = true;
        setTextToView();
    }

    private void setTextToView() {
        if (detailList != null) {
            offerList = detailList.getFirstOfferListCategory();
            detailHeader.setText(detailList.offerTitle);
            detailDeal.setText(offerList.offerDescription);
        }
        if (detailListIsSet && location != null) {
            String link = generatePathLink();
            new LocationPresenter(this).getMapsResponse(link);
        }
    }


    private String generatePathLink() {
        String urlBase = "https://maps.googleapis.com/maps/api/distancematrix/json?";
        String urlOrigin = "origins=";
        String originCoord = String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude());
        String urlDestination = "&destinations=";
        String destCoord = String.valueOf(offerList.locationLat) + "," + String.valueOf(offerList.locationLong);
        String modeOfTransport = "&mode=walking";
        String urlKey = "&key=AIzaSyCDs0OO07j5fJsosn2kuUK7A4s6Z2Y4zSw";

        return urlBase + urlOrigin + originCoord + urlDestination + destCoord + modeOfTransport + urlKey;
    }

    @Override
    public void setDurationText(MapsResponse mapsResponse) {
        String duration = mapsResponse.getCertifiedDuration();
        detailAdvertisement.setText(duration);
    }
}


//    @Override
//    public void setDurationText(Location location) {
//        this.location = location;
////        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent("location_updated"));
//        setTextToView();
//    }

//    @Override
//    public void setLocationText(MapsResponse mapsResponse) {
//        boolean foundResults = false;
//        boolean foundRows = false;
//        String locationText;
//
//        if (mapsResponse != null) {
//            foundResults = !mapsResponse.status.equals("ZERO_RESULTS");
//            if (foundResults) {
//                foundRows = !mapsResponse.rows.get(0).elements.get(0).status.equals("ZERO_RESULTS");
//            }
//        }
//
//
//        if (foundResults && foundRows) {
//            locationText = detailList.companyName + " just " + mapsResponse.rows.get(0).elements.get(0).duration.text + " away!";
//        } else {
//            locationText = "Make sure you're connected to the internet!";
//        }
//        detailAdvertisement.setText(locationText);
//    }