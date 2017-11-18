package com.garmadell.videoplayer.view.services;

import com.garmadell.videoplayer.view.bean.Curso;
import com.garmadell.videoplayer.view.bean.Perfil;
import com.garmadell.videoplayer.view.bean.Pregunta;
import com.garmadell.videoplayer.view.bean.Usuario;
import com.garmadell.videoplayer.view.bean.Versus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by alex on 11/15/17.
 */

public interface StudyService {

    String ROOT_URL = "http://192.168.0.16:8080";


    @POST("/versus/buscarQuiz")
    Call<Versus> buscaVersus(@Body Versus versus);

    @POST("/versus/buscarOponente")
    Call<Integer> buscarOponente(@Body Versus versus);

    @POST("/versus/buscarTurnoPrimerJugador")
    Call<Boolean> buscaTurnoPrimerJugador(@Body Versus versus);

    @POST("/versus/buscarTurnoSegundoJugador")
    Call<Boolean> buscaTurnoSegundoJugador(@Body Versus versus);

 //   @GET("/versus/cambioDeTurno")
//    Call<Boolean> cambioDeTurno(Integer idVersus);

    @POST("/versus/cambioDeTurno")
    Call<Boolean> cambioDeTurno(@Body Versus versus);


    /// PERFIL
    @POST("/usuario/datosUsuario")
    Call<Perfil> datosUsuario(@Body Usuario usuario);




    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ROOT_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
