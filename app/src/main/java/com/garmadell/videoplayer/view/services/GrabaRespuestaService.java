package com.garmadell.videoplayer.view.services;

import com.garmadell.videoplayer.view.bean.RespuestaRequest;
import com.garmadell.videoplayer.view.bean.RespuestaResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Michael Estrada on 11/12/17.
 */

public interface GrabaRespuestaService {

    String ROOT_URL = "http://192.168.0.16:8080";

    /*
        Retrofit get annotation with our URL
        And our method that will return us the List of Video
    */

    @POST("/historicoVersus/grabaRespuesta")
    Call<RespuestaResponse> grabaRespuesta(@Body RespuestaRequest respuestaRequest);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
