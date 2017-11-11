package com.garmadell.videoplayer.view.services.conf;

/**
 * Created by garmaDell on 7/10/2017.
 */

import com.garmadell.videoplayer.view.services.VideoService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {
    /********
     * URLS
     *******/
    private static final String ROOT_URL = "http://192.168.1.57:8080";

    /**
     * Get Retrofit Instance
     */
    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    public static VideoService getApiService() {
        return getRetrofitInstance().create(VideoService.class);
    }
}
