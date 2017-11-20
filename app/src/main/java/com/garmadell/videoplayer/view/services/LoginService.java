package com.garmadell.videoplayer.view.services;


import com.garmadell.videoplayer.view.bean.UsuarioPassword;
import com.garmadell.videoplayer.view.bean.UsuarioRegistrado;


import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Michael Estrada on 11/12/17.
 */

public interface LoginService {

    String ROOT_URL = "http://192.168.0.16:8080";

    /*
        Retrofit get annotation with our URL
        And our method that will return us the List of Video
    */

    @POST("/usuario/usuarioRegistrado")
    Call<UsuarioRegistrado> usuarioRegistrado(@Body UsuarioPassword usuarioPassword);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
