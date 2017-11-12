package com.garmadell.videoplayer.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.garmadell.videoplayer.R;
import com.garmadell.videoplayer.view.bean.Pregunta;
import com.garmadell.videoplayer.view.services.CursoService;

import java.util.List;


/**
 * Created by alex on 11/1/17.
 */

public class PreguntasRespuestasActivity extends AppCompatActivity {

    boolean [] checkedStatus;
    FragmentTransaction transaction;

    private TextView lbl_pregunta;
    private TextView lbl_respuesta1;
    private TextView lbl_respuesta2;
    private TextView lbl_respuesta3;
    private TextView lbl_respuesta4;
    private Button miFicha;
    private List<Pregunta> listadoPreguntas;
    private ImageButton btn_confirmar;
    private Handler handler = new Handler();
    private Integer indice = 0;

    CursoService cursoService = CursoService.retrofit.create(CursoService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntasyrespuestas);
        lbl_pregunta = (TextView) findViewById(R.id.lbl_pregunta);
        lbl_respuesta1 = (TextView) findViewById(R.id.lbl_respuesta1);
        lbl_respuesta2 = (TextView) findViewById(R.id.lbl_respuesta2);
        lbl_respuesta3 = (TextView) findViewById(R.id.lbl_respuesta3);
        lbl_respuesta4 = (TextView) findViewById(R.id.lbl_respuesta4);
        btn_confirmar = (ImageButton) findViewById(R.id.btn_confirmar);
        miFicha = (Button) findViewById(R.id.miFicha);

        btn_confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indice++;
                actualizaCampos(indice);
            }
        });

        if (getIntent().getExtras() != null) {
            listadoPreguntas = (List<Pregunta>) getIntent().getExtras().getSerializable("parametro1");

            lbl_pregunta.setText(listadoPreguntas.get(indice).getDesc_pregunta());
            lbl_respuesta1.setText(listadoPreguntas.get(indice).getRespuestas().get(0).getDesc_respuesta());
            lbl_respuesta2.setText(listadoPreguntas.get(indice).getRespuestas().get(1).getDesc_respuesta());
            lbl_respuesta3.setText(listadoPreguntas.get(indice).getRespuestas().get(2).getDesc_respuesta());
            // lbl_respuesta4.setText(listadoPreguntas.get(0).getRespuestas().get(3).getDesc_respuesta());

      /*    lbl_pregunta.setText(getIntent().getStringExtra("parametro1"));
            lbl_respuesta1.setText(getIntent().getStringExtra("parametro2"));
            lbl_respuesta2.setText(getIntent().getStringExtra("parametro3"));
            lbl_respuesta3.setText(getIntent().getStringExtra("parametro4"));
            lbl_respuesta4.setText(getIntent().getStringExtra("parametro5"));
            */
        }
    }

        public void actualizaCampos(final int indice) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    lbl_pregunta.setText(listadoPreguntas.get(indice).getDesc_pregunta());
                    lbl_respuesta1.setText(listadoPreguntas.get(indice).getRespuestas().get(0).getDesc_respuesta());
                    lbl_respuesta2.setText(listadoPreguntas.get(indice).getRespuestas().get(1).getDesc_respuesta());
                    lbl_respuesta3.setText(listadoPreguntas.get(indice).getRespuestas().get(2).getDesc_respuesta());

                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(getPx(30), getPx(70));
                    //lp.setMarginStart(getPx(57));
                    lp.setMargins(getPx(65),getPx(10),0,0);
                    miFicha.setLayoutParams(lp);
                }
            });
        }

        public int getPx(int dimensionDp) {
            float density = getResources().getDisplayMetrics().density;
            return (int) (dimensionDp * density + 0.5f);
        }

    }



