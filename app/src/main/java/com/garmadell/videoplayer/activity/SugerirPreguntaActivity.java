package com.garmadell.videoplayer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.garmadell.videoplayer.R;
import com.garmadell.videoplayer.view.bean.Curso;
import com.garmadell.videoplayer.view.bean.Dificultad;
import com.garmadell.videoplayer.view.fragment.CursoFragment;
import com.garmadell.videoplayer.view.services.CursoService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.garmadell.videoplayer.R.string.error_rest;

/**
 * Created by Michael Estrada on 11/18/17.
 */

public class SugerirPreguntaActivity extends AppCompatActivity {

    private Spinner spnCurso;
    private Spinner spnDificulta;
    private Button btnAceptar;
    private RadioButton rdbRespuesta1;
    private RadioButton rdbRespuesta2;
    private RadioButton rdbRespuesta3;
    private RadioButton rdbRespuesta4;
    List<String> listCursos = new ArrayList<String>();
    List<String> listDificulta = new ArrayList<String>();
    private Boolean unaVez = true;
    private Integer idUsuario;
    List<Curso> listadoCursos;
    List<Dificultad> listadoDificultad;

    CursoService cursoService = CursoService.retrofit.create(CursoService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugerirpregunta);

        spnCurso = (Spinner) findViewById(R.id.spinner_curso);
        spnDificulta = (Spinner) findViewById(R.id.spinner_Dificultad);
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        rdbRespuesta1 = (RadioButton) findViewById(R.id.rdb_Respuesta1);
        rdbRespuesta2 = (RadioButton) findViewById(R.id.rdb_Respuesta2);
        rdbRespuesta3 = (RadioButton) findViewById(R.id.rdb_Respuesta3);
        rdbRespuesta4 = (RadioButton) findViewById(R.id.rdb_Respuesta4);

        if(getIntent().getExtras() != null){
            idUsuario = (Integer) getIntent().getExtras().getSerializable("idUsuario");
            listadoCursos = (List<Curso>) getIntent().getExtras().getSerializable("listadoCursos");
            listadoDificultad = (List<Dificultad>) getIntent().getExtras().getSerializable("listadoDificultad");
        }

        if(unaVez){
            llenaSpinners();
            unaVez = false;
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, listCursos);
        spnCurso.setAdapter(adapter);

        ArrayAdapter adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, listDificulta);
        spnDificulta.setAdapter(adapter2);



        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Pregunta Guardada", Toast.LENGTH_SHORT).show();
                Integer posicion = spnCurso.getSelectedItemPosition();
                Integer posicion2 = spnDificulta.getSelectedItemPosition();
                onBackPressed();

            }
        });


    }

   /* private void llenaCursos() {
        for(Curso listc : listadoCursos){
            listCursos.add(listc.getDesc_curso());
        }
    }*/

    private void llenaSpinners() {

        for (Curso listc : listadoCursos) {
            listCursos.add(listc.getDesc_curso());
        }

        for (Dificultad listc : listadoDificultad) {
            listDificulta.add(listc.getDesc_dificultad());
        }

    }

}
