package com.example.andrus.projectnam.mooddetails;

import com.example.andrus.projectnam.models.DetailMood;
import com.example.andrus.projectnam.util.http.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class MoodDetailRequester {

    static void getResponse(final MoodDetailPresenter presenter) {
        Call<DetailMood> detailMoodList = APIClient
                .getInstance()
                .getService()
                .getThirstyCategoryItems();

        detailMoodList.enqueue(new Callback<DetailMood>() {
            @Override
            public void onResponse(Call<DetailMood> call, Response<DetailMood> response) {
                presenter.successfulResponse(response.body());
            }

            @Override
            public void onFailure(Call<DetailMood> call, Throwable t) {
                presenter.failureResponse(t);
            }
        });
    }
}