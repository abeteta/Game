package com.garmadell.videoplayer.activity;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.garmadell.videoplayer.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.AdaptiveMediaSourceEventListener;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;

/**
 * Created by garmaDell on 8/10/2017.
 */

public class VideoActivity extends AppCompatActivity {

    private SimpleExoPlayer player;
    private SimpleExoPlayerView simpleExoPlayerView;
    private Handler mainHandler;
    private TrackSelection.Factory videoTrackSelectionFactory;
    private TrackSelector trackSelector;
    private DataSource.Factory dataSourceFactory;
    private MediaSource videoSource;
    private Uri uri;
    private String userAgent;
    private String urlVideo;
    private Integer tipoVideo;
    private static final DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
    private ImageView imgPortada;
    private TextView nombrePelicula;
    private Long position;
    private Integer idPelicula;

    // Activity onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        imgPortada = (ImageView) findViewById(R.id.exo_controller_portada);
        nombrePelicula = (TextView) findViewById(R.id.exo_controller_nombre_pelicula);

        if (getIntent().getExtras() != null) {
            Bundle extras = getIntent().getExtras();
            idPelicula = extras.getInt("id");
            nombrePelicula.setText((extras.getString("nombre")));
            setUrlVideo(extras.getString("urlVideo"));
            setTipoVideo(extras.getInt("tipoVideo"));
            Bitmap bmp = extras.getParcelable("imgPortada");
            imgPortada.setImageBitmap(bmp);
            position = extras.getLong("position");
        }

        simpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);
        userAgent = Util.getUserAgent(this, this.getResources().getString(R.string.app_name));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_SYSTEM_NAVIGATION_UP ||
                keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            simpleExoPlayerView.showController();

        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN ||
                keyCode == KeyEvent.KEYCODE_SYSTEM_NAVIGATION_DOWN) {
            simpleExoPlayerView.hideController();
        }
        return super.onKeyDown(keyCode, event);
    }

    // Create TrackSelection Factory, Track Selector, Handler, Load Control, and ExoPlayer Instance
    public void createPlayer() {
        mainHandler = new Handler();
        videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
    }

    // Set player to SimpleExoPlayerView
    public void attachPlayerView() {
        simpleExoPlayerView.setPlayer(player);
    }

    // Build Data Source Factory, Dash Media Source, and Prepare player using videoSource
    public void preparePlayer() {
        uriParse();
        dataSourceFactory = buildDataSourceFactory(bandwidthMeter);

        switch (tipoVideo) {
            case 0:
                videoSource = new DashMediaSource(uri, dataSourceFactory, new DefaultDashChunkSource.Factory(dataSourceFactory), null, null);
                break;
            case 1:
                videoSource = new HlsMediaSource(uri, dataSourceFactory, null, null);
                break;
            case 2:
                videoSource = new ExtractorMediaSource(uri, dataSourceFactory, new DefaultExtractorsFactory(), null, null);
                break;
        }

        player.prepare(videoSource);

    }

    // Parse VIDEO_URI and Save at uri variable
    public void uriParse() {
        uri = Uri.parse(getUrlVideo());
    }

    // Build Data Source Factory using DefaultBandwidthMeter and HttpDataSource.Factory
    private DataSource.Factory buildDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultDataSourceFactory(this, bandwidthMeter, buildHttpDataSourceFactory(bandwidthMeter));
    }

    // Build Http Data Source Factory using DefaultBandwidthMeter
    private HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultHttpDataSourceFactory(userAgent, bandwidthMeter);
    }

    @Override
    public void onResume() {
        super.onResume();

        createPlayer();
        attachPlayerView();
        preparePlayer();

        // Restore preferences
        SharedPreferences settings = getSharedPreferences(getResources().getString(R.string.sharedPreferences), 0);
        position = settings.getLong("position", 0L);

        if (null != position && position > 0L) {
            player.seekTo(position);
        }
        player.setPlayWhenReady(true);

    }

    // Activity onStop, player must be release because of memory saving
    @Override
    public void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
        player.release();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(getResources().getString(R.string.sharedPreferences), 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("id", idPelicula);
        editor.putLong("position", player.getContentPosition());

        // Commit the edits!
        editor.commit();
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public Integer getTipoVideo() {
        return tipoVideo;
    }

    public void setTipoVideo(Integer tipoVideo) {
        this.tipoVideo = tipoVideo;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

}
