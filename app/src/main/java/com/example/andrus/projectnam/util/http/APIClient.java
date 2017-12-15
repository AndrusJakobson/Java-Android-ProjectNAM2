package com.example.andrus.projectnam.util.http;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static APIService service;
    private static APIClient instance;

    private APIClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mocky.io/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(APIService.class);
    }

    public static APIClient getInstance() {
        if (instance == null) {
            instance = new APIClient();
        }

        return instance;
    }

    public APIService getService() {
        return service;
    }
}
