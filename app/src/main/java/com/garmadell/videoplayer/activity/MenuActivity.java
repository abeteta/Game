package com.garmadell.videoplayer.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.garmadell.videoplayer.R;
import com.garmadell.videoplayer.view.bean.CambioEstadoUsuario;
import com.garmadell.videoplayer.view.bean.Curso;
import com.garmadell.videoplayer.view.bean.Dificultad;
import com.garmadell.videoplayer.view.bean.EsperandoOponente;
import com.garmadell.videoplayer.view.bean.Pregunta;
import com.garmadell.videoplayer.view.bean.Versus;
import com.garmadell.videoplayer.view.bean.VersusCursos;
import com.garmadell.videoplayer.view.services.CursoService;
import com.garmadell.videoplayer.view.services.StudyService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.garmadell.videoplayer.R.string.error_rest;
import static com.garmadell.videoplayer.R.string.error_user_password;

/**
 * Created by Michael Estrada on 11/15/17.
 */

public class MenuActivity extends AppCompatActivity {

    StudyService studyService = StudyService.retrofit.create(StudyService.class);
    CursoService cursoService = CursoService.retrofit.create(CursoService.class);

    private Button btnNewQuiz;
    private Button btnPerfil;
    private Button btnSugPregunta;
    private Integer idUsuario;
    private Integer idVersus;
    private Integer numeroJugador;
    private Integer idOponente;
    List<Curso> listadoCursos;;
    List<Dificultad> listadoDificultad;
    private Boolean unaVez = true;
    private Switch swcEstado;
    Boolean esMiTurno = false;
    List<VersusCursos> listCursosVersus;
    List<Integer> listCursosV = new ArrayList<>();
    List<Pregunta> listadoPreguntas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnNewQuiz = (Button) findViewById(R.id.btnNewQuiz);
        btnPerfil = (Button) findViewById(R.id.btnPerfil);
        btnSugPregunta = (Button) findViewById(R.id.btnSugPregunta);
        swcEstado = (Switch) findViewById(R.id.swcEstado);
        //swcEstado.setOnCheckedChangeListener(this);



        if (getIntent().getExtras() != null) {
            idUsuario = (Integer) getIntent().getExtras().getSerializable("idUsuario");
        }

        if(unaVez) {
            llenaCursos();
            llenaDificultad();
            unaVez = false;
        }

        btnNewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamaListadoCursos();
            }
        });

        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llamaPerfil();
            }
        });

        btnSugPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listadoCurso();
                llamaSugerirPregunta();
            }
        });

        swcEstado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                cambioEstadoUsuario();
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

                    //llamaListadoCursos();

/*                    if(request.getEstado_versus().equals(1)){
                        buscaOponente();
                    }  else {
                        llamaListadoCursos();
                    }*/

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

    // Listado de Cursos para enviarlos a la pantalla de suguerir pregunta y mostarlos elementos en el spinner.

    private void llenaCursos() {

        Call<List<Curso>> call = cursoService.getList();

        call.enqueue(new Callback<List<Curso>>() {

            @Override
            public void onResponse(Call<List<Curso>> call, Response<List<Curso>> response) {
                if (response.code() == 200) {
                    listadoCursos = response.body();

                } else {

                    Log.i("Else", "Else");
                    Toast.makeText(getApplicationContext(), getResources().getString(error_rest), Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<Curso>> call, Throwable t) {
                Log.i("onFailure", "onFailure");
                Toast.makeText(getApplicationContext(), getResources().getString(error_rest), Toast.LENGTH_LONG).show();
                //finish();
            }
        });
    }

    // Listado de dificultad para enviarlos a la pantalla de suguerir pregunta y mostarlos elementos en el spinner.

    private void llenaDificultad() {

        Call<List<Dificultad>> call = studyService.getList();

        call.enqueue(new Callback<List<Dificultad>>() {

            @Override
            public void onResponse(Call<List<Dificultad>> call, Response<List<Dificultad>> response) {
                if (response.code() == 200) {
                    listadoDificultad = response.body();

                } else {

                    Log.i("Else", "Else");
                    Toast.makeText(getApplicationContext(), "Error al cargar Listado Dificultad", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<Dificultad>> call, Throwable t) {
                Log.i("onFailure", "onFailure");
                Toast.makeText(getApplicationContext(), "Error en cargar Listado Dificultad", Toast.LENGTH_LONG).show();
                //finish();
            }
        });
    }

    private void llamaSugerirPregunta() {
        Intent intent = new Intent(this, SugerirPreguntaActivity.class);
        intent.putExtra("idUsuario", (Serializable) idUsuario);
        intent.putExtra("listadoCursos", (Serializable) listadoCursos);
        intent.putExtra("listadoDificultad", (Serializable) listadoDificultad);
        startActivity(intent);
    }

    private void cambioEstadoUsuario() {

        final CambioEstadoUsuario request = new CambioEstadoUsuario();

        request.setId_usuario(idUsuario);

        Call<Boolean> call = studyService.cambioEstadoUsuario(request);

        call.enqueue(new Callback<Boolean>() {

            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 200) {
                    esperandoOponente();
                    //Toast.makeText(getApplicationContext(), "Cambio Aplicado", Toast.LENGTH_SHORT).show();

                } else {
                    Log.i("Else", "Else");
                    Toast.makeText(getApplicationContext(), "Error en Cambio de Estado", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.i("onFailure", "onFailure");
                Toast.makeText(getApplicationContext(), "Error en Cambio de Estado", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    private void esperandoOponente() {

        final EsperandoOponente request = new EsperandoOponente();

        request.setId_jugador_secundario(idUsuario);
        request.setEstado_versus(1);


        Call<List<VersusCursos>> call = studyService.esperandoOponente(request);

        call.enqueue(new Callback<List<VersusCursos>>() {

            @Override
            public void onResponse(Call<List<VersusCursos>> call, Response<List<VersusCursos>> response) {
                if (response.code() == 200) {

                    listCursosVersus = response.body();

                    if(!listCursosVersus.equals(null) && listCursosVersus.size()>0 && swcEstado.isChecked() ){
                        Toast.makeText(getApplicationContext(), "Listo para Jugar", Toast.LENGTH_SHORT).show();

                        for (Integer i = 0 ; i < listCursosVersus.size() ; i++){
                            idVersus = listCursosVersus.get(i).getId_versus();
                            Integer tversus = listCursosVersus.get(i).getId_curso();
                            listCursosV.add(tversus);
                        }

                        buscaTurnoSegundoJugador();

                    } else
                    {
                        if(swcEstado.isChecked()){
                            esperandoOponente();
                        }
                    }

                } else {
                    Log.i("Else", "Else");
                    Toast.makeText(getApplicationContext(), "Error al esperar oponente", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<VersusCursos>> call, Throwable t) {
                Log.i("onFailure", "onFailure");
                Toast.makeText(getApplicationContext(), "Error al esperar oponente", Toast.LENGTH_LONG).show();
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

        Call<List<Pregunta>> call = cursoService.getSeleccionado(listCursosV);

        call.enqueue(new Callback<List<Pregunta>>() {

            @Override
            public void onResponse(Call<List<Pregunta>> call, Response<List<Pregunta>> response) {
                if (response.code() == 200) {
                    listadoPreguntas = response.body();
                    if(listadoPreguntas.size()>0) {
                        presentaPreguntas(idUsuario, idVersus, numeroJugador);
                    } else {
                        Toast.makeText(getApplicationContext(), "No hay Preguntas y Respuestas", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Log.i("Else", "Else");
                    Toast.makeText(getApplicationContext(), "Error al presentar Preguntas y Respuestas", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<Pregunta>> call, Throwable t) {
                Log.i("onFailure", "onFailure");
                Toast.makeText(getApplicationContext(), "Error al presentar Preguntas y Respuestas", Toast.LENGTH_LONG).show();
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
        startActivity(intent);


    }

}
