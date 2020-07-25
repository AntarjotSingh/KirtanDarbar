package com.coffeewithandroid.kirtandarbar;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity {

    private Button btnplay, btnpause;
    private SimpleExoPlayer audioExoPlayer;
    private DefaultTrackSelector defaultTrackSelector;
    private DefaultLoadControl defaultLoadControl;
    private DefaultRenderersFactory defaultRenderersFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        createAudioPlayer();
        setupAudioPlayer(Constants.cv_StreamUrl);
        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioExoPlayer.setPlayWhenReady(true);
            }
        });

        btnpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioExoPlayer.setPlayWhenReady(false);
            }
        });
    }

    private void initView() {
        btnplay = (Button) findViewById(R.id.play);
        btnpause = (Button) findViewById(R.id.pause);
    }

    private void createAudioPlayer() {
        defaultTrackSelector = new DefaultTrackSelector();
        defaultLoadControl = new DefaultLoadControl();
        defaultRenderersFactory = new DefaultRenderersFactory(this);

        audioExoPlayer = ExoPlayerFactory.newSimpleInstance(
                this, defaultRenderersFactory, defaultTrackSelector, defaultLoadControl
        );
    }

    private void setupAudioPlayer(String url) {
        audioExoPlayer.prepare(createUrlMediaSource(url));
    }

    private MediaSource createUrlMediaSource(String url){
        String userAgent = Util.getUserAgent(this,
                getString(R.string.app_name));

        return new ExtractorMediaSource.Factory(new DefaultDataSourceFactory(this, userAgent))
                .setExtractorsFactory(new DefaultExtractorsFactory())
                .createMediaSource(Uri.parse(url));
    }
}