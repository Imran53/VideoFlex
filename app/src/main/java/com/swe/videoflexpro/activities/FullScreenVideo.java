package com.swe.videoflexpro.activities;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.swe.videoflexpro.R;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FullScreenVideo extends AppCompatActivity {

    private SimpleExoPlayer player;
    private PlayerView playerView;
    TextView textView;
    boolean fullscreen = false;
    ImageView fullscreenButton;
    private String url;
    private boolean playwhenready = false;
    private int currentWindow=0;
    private long playbackposition=0;
    TextView exoDurationTextView; // Added TextView for displaying video duration
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_video);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Details");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        playerView = findViewById(R.id.exoplayer_fullscreen);
        textView = findViewById(R.id.fullscreenTV);
        fullscreenButton= playerView.findViewById(R.id.exoplayer_fullscreen_icon);
        exoDurationTextView = findViewById(R.id.exo_duration);

        Intent intent = getIntent();
        url= intent.getExtras().getString("url");
        String title = intent.getExtras().getString("nam");

        textView.setText(title);

        fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fullscreen){
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(FullScreenVideo.this,R.drawable.ic_baseline_fullscreen_expand));
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    if(getSupportActionBar()!=null){
                        getSupportActionBar().show();
                    }
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    LinearLayout.LayoutParams params =(LinearLayout.LayoutParams)playerView.getLayoutParams();
                    params.width=params.MATCH_PARENT;
                    params.height= (int) (200 * getApplicationContext().getResources().getDisplayMetrics().density);
                    playerView.setLayoutParams(params);
                    fullscreen=false;
                    //textView.setVisibility(View.VISIBLE);
                }else{
                    fullscreenButton.setImageDrawable(ContextCompat.getDrawable(FullScreenVideo.this,R.drawable.ic_baseline_fullscreen_shrink));
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    if(getSupportActionBar()!=null){
                        getSupportActionBar().hide();
                    }
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    LinearLayout.LayoutParams params =(LinearLayout.LayoutParams)playerView.getLayoutParams();
                    params.width=params.MATCH_PARENT;
                    params.height= params.MATCH_PARENT;
                    playerView.setLayoutParams(params);
                    fullscreen=true;
                    //textView.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    private MediaSource buildMediaSource(Uri uri){
        DataSource.Factory datasourcefactory =
                new DefaultHttpDataSourceFactory("freevideo");
        return new ProgressiveMediaSource.Factory(datasourcefactory)
                .createMediaSource(uri);
    }

    private void initializeplayer() {
        player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        Uri uri = Uri.parse(url);
        MediaSource mediaSource = buildMediaSource(uri);
        player.setPlayWhenReady(playwhenready);
        player.seekTo(currentWindow, playbackposition);
        player.prepare(mediaSource);

        player.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_READY) {
                    long duration = player.getDuration();
                    String durationString = formatDuration(duration);
                    exoDurationTextView.setText(durationString);
                }
            }
        });

        playerView.setControllerVisibilityListener(new PlayerControlView.VisibilityListener() {
            @Override
            public void onVisibilityChange(int visibility) {
                if (visibility == View.VISIBLE) {
                    playerView.showController(); // Ensure the controller remains visible
                }
            }
        });
    }

    private String formatDuration(long duration) {
        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) % 60;

        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Util.SDK_INT >= 24) {
            initializeplayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Util.SDK_INT < 24 || player == null) {
            initializeplayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
            playwhenready = player.getPlayWhenReady();
            playbackposition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }


    private void showSpeedMenu(View anchorView) {
        PopupMenu popupMenu = new PopupMenu(this, anchorView);
        popupMenu.inflate(R.menu.menu_speed);

        // Set menu item click listener
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle menu item click event
                switch (item.getItemId()) {
                    case R.id.speed_0_75x:
                        setPlaybackSpeed(0.75f);
                        return true;
                    case R.id.speed_1x:
                        setPlaybackSpeed(1.0f);
                        return true;
                    case R.id.speed_1_25x:
                        setPlaybackSpeed(1.25f);
                        return true;
                    case R.id.speed_1_5x:
                        setPlaybackSpeed(1.5f);
                        return true;
                    case R.id.speed_1_75x:
                        setPlaybackSpeed(1.75f);
                        return true;
                    case R.id.speed_2x:
                        setPlaybackSpeed(2.0f);
                        return true;
                    default:
                        return false;
                }
            }
        });

        popupMenu.show();
    }

    private void setPlaybackSpeed(float speed) {
        // Set the playback speed of the player
        if (player != null) {
            PlaybackParameters parameters = new PlaybackParameters(speed);
            player.setPlaybackParameters(parameters);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_full_screenfreevideo, menu);

        MenuItem speedMenuItem = menu.findItem(R.id.menu_speed);
        View speedActionView = speedMenuItem.getActionView();
        ImageButton speedButton = speedActionView.findViewById(R.id.button_speed);

        speedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpeedMenu(speedButton);
            }
        });

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        releasePlayer();

        final Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
