package com.garmadell.videoplayer.view.services;

import com.garmadell.videoplayer.view.bean.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by garmaDell on 7/10/2017.
 */

public interface VideoService {

    String ROOT_URL = "http://192.168.0.14:8080";

    /*
        Retrofit get annotation with our URL
        And our method that will return us the List of Video
    */
    @GET("/video/list")
    Call<List<Video>> getVideoList();

    @GET("/video/{id}")
    Call<Video> getVideo(@Path("id") Integer id);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
