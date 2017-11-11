package com.garmadell.videoplayer.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.garmadell.videoplayer.R;
import com.garmadell.videoplayer.view.bean.Curso;
import com.garmadell.videoplayer.view.bean.Pregunta;
import com.garmadell.videoplayer.view.bean.Video;
import com.garmadell.videoplayer.view.fragment.CursoFragment;
import com.garmadell.videoplayer.view.fragment.ListVideoFragment;
import com.garmadell.videoplayer.view.services.CursoService;
import com.garmadell.videoplayer.view.services.VideoService;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.garmadell.videoplayer.R.string.error_rest;

/**
 * Created by alex on 10/21/17.
 */

public class CursoActivity extends AppCompatActivity {

    boolean[] checkedStatus;
    FragmentTransaction transaction;
    CursoFragment fragment;
    CursoService cursoService = CursoService.retrofit.create(CursoService.class);
    List<Pregunta> listadoPreguntas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curso);

        listaCurso();

    }

    private void listaCurso() {
        /* Primer Fragment */
        transaction = getSupportFragmentManager().beginTransaction();
        fragment = new CursoFragment();

        Call<List<Curso>> call = cursoService.getList();

        call.enqueue(new Callback<List<Curso>>() {

            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {
                if (response.code() == 200) {
                    List<Curso> beans = response.body();
                    fragment.setBeans(beans);
                    commitTransaction(transaction);
                } else {
                    commitTransaction(transaction);
                    Log.i("Else", "Else");
                    Toast.makeText(getApplicationContext(), getResources().getString(error_rest), Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<Curso>> call, Throwable t) {
                commitTransaction(transaction);
                Log.i("onFailure", "onFailure");
                Toast.makeText(getApplicationContext(), getResources().getString(error_rest), Toast.LENGTH_LONG).show();
                finish();
            }
        });

        transaction.replace(R.id.content_fragment_list_curso, fragment);
        //   transaction.commit();

        AppCompatButton button = (AppCompatButton) findViewById(R.id.btn_confirmar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedStatus = fragment.getmAdapter().getCheckedStatus();
                llamaIntent();
                Log.i("prueba onClick", "Continuar");
            }
        });
    }

    private void llamaIntent() {
        List<Curso> listCursos = fragment.getBeans();

        List<Integer> idsSeleccionados = new ArrayList<>();

        for (int index = 0; index < checkedStatus.length; index++) {
            if (checkedStatus[index]) {
                idsSeleccionados.add(listCursos.get(index).getId_curso());
            }
        }

        Call<List<Pregunta>> call = cursoService.getSeleccionado(idsSeleccionados);

        call.enqueue(new Callback<List<Pregunta>>() {

            @Override
            public void onResponse(Call<List<Pregunta>> call, Response<List<Pregunta>> response) {
                if (response.code() == 200) {
                    listadoPreguntas = response.body();

                    presentaPreguntas();

                } else {
                    Log.i("Else", "Else");
                    Toast.makeText(getApplicationContext(), getResources().getString(error_rest), Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<Pregunta>> call, Throwable t) {
                Log.i("onFailure", "onFailure");
                Toast.makeText(getApplicationContext(), getResources().getString(error_rest), Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    private void presentaPreguntas() {


        Intent intent = new Intent(this, PreguntasRespuestasActivity.class);
        intent.putExtra("parametro1", (Serializable) listadoPreguntas);
        //     intent.putExtra("parametro2", listadoPreguntas.get(0).getRespuestas().get(0));
        //     intent.putExtra("parametro3", listadoPreguntas.get(0).getRespuestas().get(1));
        //     intent.putExtra("parametro4", listadoPreguntas.get(0).getRespuestas().get(2));
//        intent.putExtra("parametro5",respuestas.getRespuestas().get(3));
        startActivity(intent);

 /*       for (Pregunta respuestas : listadoPreguntas) {

           // Intent intent = new Intent(this, PreguntasRespuestasActivity.class);
            intent.putExtra("parametro1", respuestas.getDesc_pregunta());
            intent.putExtra("parametro2", respuestas.getRespuestas().get(0));
            intent.putExtra("parametro3", respuestas.getRespuestas().get(1));
            intent.putExtra("parametro4", respuestas.getRespuestas().get(2));
//        intent.putExtra("parametro5",respuestas.getRespuestas().get(3));
            startActivity(intent);

            try {
                Thread.sleep(5000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            }
            */

    }

    public void commitTransaction(FragmentTransaction transaction) {
        transaction.commit();
    }

}
