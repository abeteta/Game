package com.garmadell.videoplayer.view.services;

import com.garmadell.videoplayer.view.bean.Curso;
import com.garmadell.videoplayer.view.bean.Pregunta;
import com.garmadell.videoplayer.view.bean.UsuarioPassword;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by alex on 11/12/17.
 */

public interface LoginService {

    String ROOT_URL = "http://192.168.0.16:8080";

    /*
        Retrofit get annotation with our URL
        And our method that will return us the List of Video
    */

    @POST("/usuario/usuarioRegistrado")
    Call<Boolean> usuarioRegistrado(@Body UsuarioPassword usuarioPassword);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
