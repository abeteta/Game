package com.garmadell.videoplayer.view.services;

import com.garmadell.videoplayer.view.bean.Curso;
import com.garmadell.videoplayer.view.bean.Pregunta;
import com.garmadell.videoplayer.view.bean.Video;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by alex on 10/21/17.
 */

public interface CursoService {

    String ROOT_URL = "http://192.168.0.16:8080";

    /*
        Retrofit get annotation with our URL
        And our method that will return us the List of Video
    */
    @GET("/curso/list")
    Call<List<Curso>> getList();

    @GET("/curso/{id}")
    Call<Curso> getOne(@Path("id_curso") Integer id);

    @POST("/preguntas/listadoSeleccionado")
    Call<List<Pregunta>> getSeleccionado(@Body List<Integer> listadoSeleccionado);


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
