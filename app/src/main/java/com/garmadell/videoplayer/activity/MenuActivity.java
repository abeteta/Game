package com.garmadell.videoplayer.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.garmadell.videoplayer.R;
import com.garmadell.videoplayer.view.bean.Versus;
import com.garmadell.videoplayer.view.services.StudyService;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.garmadell.videoplayer.R.string.error_rest;
import static com.garmadell.videoplayer.R.string.error_user_password;

/**
 * Created by alex on 11/15/17.
 */

public class MenuActivity extends AppCompatActivity {

    StudyService studyService = StudyService.retrofit.create(StudyService.class);

    private Button btnNewQuiz;
    private Button btnPerfil;
    private Button btnSugPregunta;
    private Button btnRevPregunta;
    private Integer idUsuario;
    private Integer idVersus;
    private Integer numeroJugador;
    private Integer idOponente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnNewQuiz = (Button) findViewById(R.id.btnNewQuiz);
        btnPerfil = (Button) findViewById(R.id.btnPerfil);


        if (getIntent().getExtras() != null) {
            idUsuario = (Integer) getIntent().getExtras().getSerializable("idUsuario");
        }

        btnNewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscaQuiz();
            }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamaPerfil();
            }
        });

    }

    private void buscaQuiz() {

        final Versus request = new Versus();

        request.setEstado_versus(1);
        request.setId_jugador_primario(idUsuario);

        Call<Versus> call = studyService.buscaVersus(request);

        call.enqueue(new Callback<Versus>() {

            @Override
            public void onResponse(Call<Versus> call, Response<Versus> response) {
                if (response.code() == 200) {

                    idVersus = response.body().getId_versus();
                    numeroJugador = (response.body().getId_jugador_primario()==idUsuario) ? 1 : 2;
                    buscaOponente();

                } else {
                    Log.i("Else", "Else");
                    Toast.makeText(getApplicationContext(), getResources().getString(error_rest), Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Versus> call, Throwable t) {
                Log.i("onFailure", "onFailure");
                Toast.makeText(getApplicationContext(), getResources().getString(error_rest), Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }


    private void buscaOponente() {

        final Versus request = new Versus();

        request.setId_versus(idVersus);
        request.setId_jugador_primario(idUsuario);

        Call<Integer> call = studyService.buscarOponente(request);

        call.enqueue(new Callback<Integer>() {

            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.code() == 200) {

                    idOponente = response.body();

                    if(idOponente!=0){

                        Toast.makeText(getApplicationContext(), "Selecciona los Cursos", Toast.LENGTH_SHORT).show();
                        llamaListadoCursos();
                    } else {
                        //Toast.makeText(getApplicationContext(), "Buscando Oponente", Toast.LENGTH_SHORT).show();
                        buscaOponente();
                    }

                } else {
                    Log.i("Else", "Else");
                    Toast.makeText(getApplicationContext(), "Error al buscar Oponente", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.i("onFailure", "onFailure");
                Toast.makeText(getApplicationContext(), "Error al buscar Oponente", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    private void llamaListadoCursos() {
        Intent intent = new Intent(this, CursoActivity.class);
        intent.putExtra("idUsuario", (Serializable) idUsuario);
        intent.putExtra("idVersus", (Serializable) idVersus);
        intent.putExtra("numeroJugador", (Serializable) numeroJugador);
        startActivity(intent);

    }

    private void llamaPerfil() {
        Intent intent = new Intent(this, PerfilActivity.class);
        intent.putExtra("idUsuario", (Serializable) idUsuario);
        startActivity(intent);

    }


}
