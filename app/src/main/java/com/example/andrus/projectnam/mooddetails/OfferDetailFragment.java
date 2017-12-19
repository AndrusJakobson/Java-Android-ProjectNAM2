package com.example.andrus.projectnam.mooddetails;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andrus.projectnam.MainActivity;
import com.example.andrus.projectnam.R;
import com.example.andrus.projectnam.models.DetailMood;
import com.example.andrus.projectnam.models.MapsResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfferDetailFragment extends MyLocationFragment implements OfferDetailView {
    @BindView(R.id.fragmentDetailThirsty_header)
    TextView detailTitle;
    @BindView(R.id.fragmentDetailThirsty_deal)
    TextView detailDescription;
    @BindView(R.id.fragmentDetailThirsty_advertisement)
    TextView detailTimeToTravel;

    boolean detailListIsSet = false;
    final public static String TAG = "location";

    private OfferDetailPresenter presenter;

    public static OfferDetailFragment newInstance() {
        return new OfferDetailFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new OfferDetailPresenter(this);
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
        presenter.getDetails();
    }

    @Override
    public void onLocationChange(Location location) {
        presenter.getWalkingTime(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void setDetailText(DetailMood detailList) {
        detailListIsSet = true;
    }

    @Override
    public void setOfferTitle(String title) {
        detailTitle.setText(title);
    }

    @Override
    public void setOfferText(String text) {
        detailDescription.setText(text);
    }

    @Override
    public void setTimeToOfferLocationText(String time) {
        detailTimeToTravel.setText(time);
    }

    @Override
    public void requestWalkingTime() {
        Location location = getCurrentLocation();
        if (location != null) {
            presenter.getWalkingTime(location.getLatitude(), location.getLongitude());
        }
    }

    @Override
    public void setDurationText(String durationText) {
        detailTimeToTravel.setText(durationText);
    }

    @Override
    public boolean hasDeviceLocation() {
        return getCurrentLocation() != null;
    }
}