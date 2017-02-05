package com.example.android.sunshine.app.controler;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrador on 25/01/2017.
 */

public class RestClient {

    private static Retrofit retrofit = null;

    public static Retrofit getRestClient(String BASE_URL) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
