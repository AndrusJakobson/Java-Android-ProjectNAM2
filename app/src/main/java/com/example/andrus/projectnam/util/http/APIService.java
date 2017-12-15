package com.example.andrus.projectnam.util.http;


import com.example.andrus.projectnam.models.Category;
import com.example.andrus.projectnam.models.DetailMood;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("5a29408e2e00000f1ea09b40")
    Call<List<Category>> getGridList();

    @GET("5a2e5f1b2e00000214278d8c")
    Call<DetailMood> getThirstyCategoryItems();

}
