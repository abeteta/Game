package com.garmadell.videoplayer.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.garmadell.videoplayer.R;
import com.garmadell.videoplayer.view.bean.Video;
import com.garmadell.videoplayer.view.fragment.ListVideoFragment;
import com.garmadell.videoplayer.view.services.VideoService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Video video;
    private Integer id;
    private Long position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Restore preferences
        /*
        SharedPreferences settings = getSharedPreferences(getResources().getString(R.string.sharedPreferences), 0);
        id = settings.getInt("id", 0);
        position = settings.getLong("position", 0L);

        if (id > 0) {
            recuperaVideo(id, position);

        } else {
            ListaVideos();
        }
        */

        Intent intent = new Intent(this, CursoActivity.class);
        startActivity(intent);

    }

    private void recuperaVideo(Integer id, final Long position) {
        VideoService videoService = VideoService.retrofit.create(VideoService.class);
        Call<Video> call = videoService.getVideo(id);

        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                setVideo(response.body());
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                Log.i("onFailure", "onFailure");
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_rest), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void llamaIntent(Long position) {
        Intent intent = new Intent(this, VideoActivity.class);
        Bundle extras = new Bundle();
        extras.putInt("id", video.getId());
        extras.putString("nombre", video.getNombre());
        extras.putString("urlVideo", video.getUrlVideo());
        extras.putInt("tipoVideo", video.getTipoVideo());

        ImageView imgPortada = new ImageView(this);
        Picasso.with(this.getApplicationContext()).load(video.getUrlPortada()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imgPortada);
        imgPortada.buildDrawingCache();
        Bitmap image = imgPortada.getDrawingCache();
        extras.putParcelable("imgPortada", image);
        extras.putLong("position", position);
        intent.putExtras(extras);
        startActivity(intent);
    }

    private void ListaVideos() {
        /* Primer Fragment */
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        final ListVideoFragment fragment = new ListVideoFragment();


        VideoService videoService = VideoService.retrofit.create(VideoService.class);
        Call<List<Video>> call = videoService.getVideoList();

        call.enqueue(new Callback<List<Video>>() {

            @Override
            public void onResponse(Call<List<Video>> call, Response<List<Video>> response) {
                if (response.code() == 200) {
                    List<Video> beans = response.body();
                    fragment.setBeans(beans);
                    commitTransaction(transaction);
                } else {
                    commitTransaction(transaction);
                    Log.i("Else", "Else");
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_rest), Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<Video>> call, Throwable t) {
                commitTransaction(transaction);
                Log.i("onFailure", "onFailure");
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_rest), Toast.LENGTH_LONG).show();
                finish();
            }
        });

        transaction.replace(R.id.content_fragment_list_video, fragment);
        //   transaction.commit();
    }

    public void commitTransaction(FragmentTransaction transaction) {
        transaction.commit();
    }


    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
        AlertDialog alert = new AlertDialog.Builder(this).create();

        alert.setTitle(video.getNombre());
        alert.setMessage("Desea continuar la reproducción?");

        alert.setButton(AlertDialog.BUTTON_POSITIVE, "aceptar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                llamaIntent(position);
            }
        });

        alert.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ListaVideos();
                dialog.dismiss();
            }
        });

        alert.show();
    }

}
