package com.garmadell.videoplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.garmadell.videoplayer.R;
import com.garmadell.videoplayer.view.bean.Curso;
import com.garmadell.videoplayer.view.bean.EstadoUsuario;
import com.garmadell.videoplayer.view.bean.Pregunta;
import com.garmadell.videoplayer.view.bean.UsuariosActivos;
import com.garmadell.videoplayer.view.bean.Versus;
import com.garmadell.videoplayer.view.bean.VersusCursos;
import com.garmadell.videoplayer.view.fragment.UsuariosActivosFragment;
import com.garmadell.videoplayer.view.services.CursoService;
import com.garmadell.videoplayer.view.services.StudyService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.garmadell.videoplayer.R.string.error_rest;

/**
 * Created by alex on 11/19/17.
 */

public class UsuariosActivosActivity extends AppCompatActivity {

    boolean[] checkedStatus;
    FragmentTransaction transaction;
    UsuariosActivosFragment fragment;
    CursoService cursoService = CursoService.retrofit.create(CursoService.class);
    StudyService studyService = StudyService.retrofit.create(StudyService.class);
    List<Pregunta> listadoPreguntas;
    Integer idUsuario = 0;
    Integer idVersus = 0;
    Integer numeroJugador = 0;
    Boolean esMiTurno = false;
    List<Integer> idsCursosSeleccionados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        if(getIntent().getExtras() != null){
            idUsuario = (Integer) getIntent().getExtras().getSerializable("idUsuario");
            idVersus = (Integer) getIntent().getExtras().getSerializable("idVersus");
            numeroJugador = (Integer) getIntent().getExtras().getSerializable("numeroJugador");
            idsCursosSeleccionados = (List<Integer>) getIntent().getExtras().getSerializable("listadoCursosSeleccionados");
        }

        listaUsuariosActivos(idUsuario, idVersus, numeroJugador);

    }

    private void listaUsuariosActivos(final Integer idUsuario, final Integer idVersus, final Integer numeroJugador) {
        /* Primer Fragment */
        transaction = getSupportFragmentManager().beginTransaction();
        fragment = new UsuariosActivosFragment();
        EstadoUsuario estadoUsuario = new EstadoUsuario();
        estadoUsuario.setEstado(1);

        Call<List<UsuariosActivos>> call = studyService.listadoUsuariosActivos(estadoUsuario);

        call.enqueue(new Callback<List<UsuariosActivos>>() {

            @Override
            public void onResponse(Call<List<UsuariosActivos>> call, Response<List<UsuariosActivos>> response) {

                if (response.code() == 200) {
                    List<UsuariosActivos> beans = response.body();
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
            public void onFailure(Call<List<UsuariosActivos>> call, Throwable t) {
                Log.i("onFailure", "onFailure");
                commitTransaction(transaction);
                Log.i("onFailure", "onFailure");
                Toast.makeText(getApplicationContext(), getResources().getString(error_rest), Toast.LENGTH_LONG).show();
                finish();
            }
        });

        transaction.replace(R.id.content_fragment_list_usuarios_activos, fragment);
        //transaction.commit();

        AppCompatButton button = (AppCompatButton) findViewById(R.id.btn_jugar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkedStatus = fragment.getmAdapter().getCheckedStatus();
                grabaVersus();
                Log.i("prueba onClick", "Continuar");
            }
        });
    }

    private void grabaVersus() {

        final Versus request = new Versus();

        List<UsuariosActivos> listUsuariosActivos = fragment.getBeans();
        Integer idOponente = 0;
        for (int index = 0; index < checkedStatus.length; index++) {
            if (checkedStatus[index]) {
                idOponente =(listUsuariosActivos.get(index).getId_usuario());
            }
        }
        request.setId_versus(0);
        request.setId_jugador_primario(idUsuario);
        request.setTurno_jugador_primario(true);
        request.setId_jugador_secundario(idOponente);
        request.setTurno_jugador_secundario(false);

        Call<Versus> call = studyService.grabaVersus(request);

        call.enqueue(new Callback<Versus>() {

            @Override
            public void onResponse(Call<Versus> call, Response<Versus> response) {
                if (response.code() == 200) {

                    idVersus = response.body().getId_versus();
                    numeroJugador = (response.body().getId_jugador_primario()==idUsuario) ? 1 : 2;
                    if(!idVersus.equals(0)){
                        grabaVersusCurso(idUsuario);
                    }


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

    private void grabaVersusCurso(final Integer idUsuario) {

        List<VersusCursos> versusCursosList = new ArrayList<VersusCursos>();

        for (int index = 0; index < idsCursosSeleccionados.size(); index++) {
                VersusCursos newVersusCursos = new VersusCursos();
                newVersusCursos.setId_versus(idVersus);
                newVersusCursos.setId_curso(idsCursosSeleccionados.get(index));
                versusCursosList.add(newVersusCursos);
        }

        Call<Boolean> call = studyService.grabaVersusCursos(versusCursosList);

        call.enqueue(new Callback<Boolean>() {

            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 200) {
                    Boolean respuestaOk = response.body();
                    buscaTurnoPrimerJugador();
                    //presentaPreguntas(idUsuario, idVersus, numeroJugador);

                } else {
                    Log.i("Else", "Else");
                    Toast.makeText(getApplicationContext(), "Error al grabar versus", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.i("onFailure", "onFailure");
                Toast.makeText(getApplicationContext(), "Error al grabar versus", Toast.LENGTH_LONG).show();
                finish();
            }
        });
           }

    private void buscaTurnoPrimerJugador() {

        final Versus request = new Versus();

        request.setId_versus(idVersus);
        request.setId_jugador_primario(idUsuario);

        Call<Boolean> call = studyService.buscaTurnoPrimerJugador(request);

        call.enqueue(new Callback<Boolean>() {

            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 200) {

                    esMiTurno = response.body();

                    if(esMiTurno.equals(true)){

                        Toast.makeText(getApplicationContext(), "Que gane el mejor", Toast.LENGTH_SHORT).show();
                        llamaPreguntasYRespuestas(idUsuario, idVersus, numeroJugador);
                    } else {
                        //Toast.makeText(getApplicationContext(), "Buscando Oponente", Toast.LENGTH_SHORT).show();
                        buscaTurnoPrimerJugador();
                    }

                } else {
                    Log.i("Else", "Else");
                    Toast.makeText(getApplicationContext(), "Error al buscar turno primer jugador", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.i("onFailure", "onFailure");
                Toast.makeText(getApplicationContext(), "Error al buscar turno primer Jugador", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    private void buscaTurnoSegundoJugador() {

        final Versus request = new Versus();

        request.setId_versus(idVersus);
        request.setId_jugador_secundario(idUsuario);

        Call<Boolean> call = studyService.buscaTurnoSegundoJugador(request);

        call.enqueue(new Callback<Boolean>() {

            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 200) {

                    esMiTurno = response.body();

                    if(esMiTurno.equals(true)){

                        Toast.makeText(getApplicationContext(), "Que gane el mejor", Toast.LENGTH_SHORT).show();
                        llamaPreguntasYRespuestas(idUsuario, idVersus, numeroJugador);
                    } else {
                        //Toast.makeText(getApplicationContext(), "Buscando Oponente", Toast.LENGTH_SHORT).show();
                        buscaTurnoSegundoJugador();
                    }

                } else {
                    Log.i("Else", "Else");
                    Toast.makeText(getApplicationContext(), "Error al buscar turno segundo jugador", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.i("onFailure", "onFailure");
                Toast.makeText(getApplicationContext(), "Error al buscar turno segundo Jugador", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    private void llamaPreguntasYRespuestas(final Integer idUsuario, final Integer idVersus, final Integer numeroJugador) {
/*
        List<UsuariosActivos> listUsuariosActivos = fragment.getBeans();

        List<Integer> idsSeleccionados = new ArrayList<>();

        for (int index = 0; index < checkedStatus.length; index++) {
            if (checkedStatus[index]) {
                idsSeleccionados.add(idsSeleccionados.get(index));
            }
        }
*/

        Call<List<Pregunta>> call = cursoService.getSeleccionado(idsCursosSeleccionados);

        call.enqueue(new Callback<List<Pregunta>>() {

            @Override
            public void onResponse(Call<List<Pregunta>> call, Response<List<Pregunta>> response) {
                if (response.code() == 200) {
                    listadoPreguntas = response.body();

                    presentaPreguntas(idUsuario, idVersus, numeroJugador);

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

    private void presentaPreguntas(final Integer idUsuario, final Integer idVersus, final Integer numeroJugador) {


        Intent intent = new Intent(this, PreguntasRespuestasActivity.class);
        intent.putExtra("idUsuario", (Serializable) idUsuario);
        intent.putExtra("idVersus", (Serializable) idVersus);
        intent.putExtra("numeroJugador", (Serializable) numeroJugador);
        intent.putExtra("listadoPreguntas", (Serializable) listadoPreguntas);
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
