package com.example.andrus.projectnam.moodgrid;

import com.example.andrus.projectnam.models.Category;
import com.example.andrus.projectnam.util.http.APIClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class MoodGridRequester {

    static void getServerResponse(final MoodGridPresenter presenter) {
        Call<List<Category>> response = APIClient
                .getInstance()
                .getService()
                .getGridList();

        response.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                presenter.successfulRetrieval(response.body());
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                presenter.failedRetrieval(t);
            }
        });
    }
}
