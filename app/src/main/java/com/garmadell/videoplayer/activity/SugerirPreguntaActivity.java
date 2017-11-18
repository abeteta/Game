package com.garmadell.videoplayer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.garmadell.videoplayer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 11/18/17.
 */

public class SugerirPreguntaActivity extends AppCompatActivity {

    private Spinner spnCurso;
    private Spinner spnDificulta;
    private Button btnAceptar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugerirpregunta);

        spnCurso = (Spinner) findViewById(R.id.spinner_curso);
        spnDificulta = (Spinner) findViewById(R.id.spinner_Dificultad);
        btnAceptar = (Button) findViewById(R.id.btnAceptar);

        List<String> list = new ArrayList<String>();
        list.add("curso 1");
        list.add("curso 2");
        list.add("curso 3");

        List<String> list2 = new ArrayList<String>();
        list2.add("dificulta 1");
        list2.add("dificulta 2");
        list2.add("dificulta 3");


        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, list);
        spnCurso.setAdapter(adapter);

        ArrayAdapter adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, list2);
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
}
