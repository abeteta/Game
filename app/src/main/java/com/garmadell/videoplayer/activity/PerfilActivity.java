package com.garmadell.videoplayer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.garmadell.videoplayer.R;
import com.garmadell.videoplayer.view.bean.Perfil;
import com.garmadell.videoplayer.view.bean.Usuario;
import com.garmadell.videoplayer.view.bean.Versus;
import com.garmadell.videoplayer.view.services.StudyService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alex on 11/17/17.
 */

public class PerfilActivity extends AppCompatActivity {

    StudyService studyService = StudyService.retrofit.create(StudyService.class);

    private Integer idUsuario;
    private TextView textNombre;
    private TextView textApellido;
    private TextView textEmail;
    private TextView textTipoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        textNombre = (TextView) findViewById(R.id.txt_nombre);
        textApellido = (TextView) findViewById(R.id.txt_apellido);
        textEmail = (TextView) findViewById(R.id.txt_email);
        textTipoUsuario = (TextView) findViewById(R.id.txt_tipousuario);

        if (getIntent().getExtras() != null) {
            idUsuario = (Integer) getIntent().getExtras().getSerializable("idUsuario");

            Usuario request = new Usuario();
            request.setId_user(idUsuario);

            Call<Perfil> call = studyService.datosUsuario(request);

            call.enqueue(new Callback<Perfil>() {

                @Override
                public void onResponse(Call<Perfil> call, Response<Perfil> response) {
                    if (response.code() == 200) {

                        textNombre.setText(response.body().getNombre());
                        textApellido.setText(response.body().getApellido());
                        textEmail.setText(response.body().getEmail());
                        textTipoUsuario.setText(response.body().getDesc_usertype());

                    } else {
                        Log.i("Else", "Else");
                        Toast.makeText(getApplicationContext(), "Error al buscar Perfil", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Perfil> call, Throwable t) {
                    Log.i("onFailure", "onFailure");
                    Toast.makeText(getApplicationContext(), "Error al buscar Perfil", Toast.LENGTH_LONG).show();
                    finish();
                }
            });

        }

    }
}
