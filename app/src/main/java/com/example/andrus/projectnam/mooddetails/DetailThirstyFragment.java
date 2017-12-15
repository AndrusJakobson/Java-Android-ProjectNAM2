package com.example.andrus.projectnam.mooddetails;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andrus.projectnam.MainActivity;
import com.example.andrus.projectnam.R;
import com.example.andrus.projectnam.models.DetailMood;
import com.example.andrus.projectnam.models.OfferListByCategory;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailThirstyFragment extends Fragment implements LocationInterface {
    @BindView(R.id.fragmentDetailThirsty_header)
    TextView detailHeader;
    @BindView(R.id.fragmentDetailThirsty_deal)
    TextView detailDeal;
    @BindView(R.id.fragmentDetailThirsty_advertisement)
    TextView detailAdvertisement;

    private Location destination = new Location("destination");
    final public static String TAG = "location";
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
        if (!LocationTracker.tracking) {
            new LocationTracker((MainActivity) getActivity(), this).initialize();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setDetailText(DetailMood detailList) {
        this.detailList = detailList;
        setTextToView();
    }

    @Override
    public void locationCallBack(Location location) {
        this.location = location;
        setTextToView();
    }

    private void setTextToView() {
        if (location != null && detailList != null) {
            OfferListByCategory offerList = detailList.OfferListByCategory.get(0);

            float distance = getDistance(offerList, location);

            detailHeader.setText(detailList.offerTitle);
            detailDeal.setText(offerList.offerDescription);
            detailAdvertisement.setText(Float.toString(distance));
        }

    }

    private float getDistance(OfferListByCategory category, Location location) {
        double destLat = category.locationLat;
        double destLong = category.locationLong;
        destination.setLatitude(destLat);
        destination.setLongitude(destLong);
        return location.distanceTo(destination); //Meters
    }
}