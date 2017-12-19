package com.example.andrus.projectnam.mooddetails;

import com.example.andrus.projectnam.models.MapsResponse;
import com.example.andrus.projectnam.util.http.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationRequester {
    public static void getWalkingTimeFromMaps(final OfferDetailPresenter presenter, String link) {
        Call<MapsResponse> mapsResponse = APIClient
                .getInstance()
                .getService()
                .getMapsResponse(link);

        mapsResponse.enqueue(new Callback<MapsResponse>() {
            @Override
            public void onResponse(Call<MapsResponse> call, Response<MapsResponse> response) {
                presenter.onWalkingTimeRequestSuccess(response.body());
            }

            @Override
            public void onFailure(Call<MapsResponse> call, Throwable t) {
                presenter.onWalkingTimeRequestFailure(t);

            }
        });
    }
}
