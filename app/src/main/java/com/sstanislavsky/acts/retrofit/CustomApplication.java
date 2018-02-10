package com.sstanislavsky.acts.retrofit;

import android.app.Application;

import com.sstanislavsky.acts.retrofit.ApiMorpher;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by stanislav on 2/8/18.
 */

public class CustomApplication extends Application {
    private static ApiMorpher apiMorpher;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://ws3.morpher.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiMorpher = retrofit.create(ApiMorpher.class);
    }

    public static ApiMorpher getApi() {
        return apiMorpher;
    }
}
