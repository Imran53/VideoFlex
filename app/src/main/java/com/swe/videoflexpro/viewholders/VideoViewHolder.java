package com.swe.videoflexpro.viewholders;


import android.app.Application;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Log;
import com.swe.videoflexpro.R;

import java.util.Collections;


public class VideoViewHolder extends RecyclerView.ViewHolder {

    SimpleExoPlayer exoplayer;
    PlayerView playerView;
    public VideoViewHolder(@NonNull View itemView) {

        super(itemView);
        //delete data
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view,getAdapterPosition());

            }
        });
        //delete data
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view,getAdapterPosition());
                return false;
            }
        });


    }

    public void setExoplayer(Application application, String name, String Videourl){
        TextView textView = itemView.findViewById(R.id.itemnameTV);
        playerView = itemView.findViewById(R.id.exoplayerItem);

        textView.setText(name);

        try{
          /*  BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter.Builder(application).build();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoplayer = (SimpleExoPlayer) ExoPlayerFactory.newSimpleInstance(getApplicationContext());
            Uri video = Uri.parse(Videourl);
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(video, dataSourceFactory, extractorsFactory, null, null);

            playerView.setPlayer(exoplayer);
          //  exoplayer.prepare(mediaSource);
            exoplayer.setPlayWhenReady(false); */
            SimpleExoPlayer simpleExoPlayer = new SimpleExoPlayer.Builder(application).build();
            playerView.setPlayer(simpleExoPlayer);
            MediaItem mediaItem = MediaItem.fromUri(Videourl);
            simpleExoPlayer.addMediaItems(Collections.singletonList(mediaItem));
            simpleExoPlayer.prepare();
            simpleExoPlayer.setPlayWhenReady(false);

        }catch(Exception e){
            Log.e("VideoViewHolder","exoplayer error"+e.toString());
        }
    }

    //delete data
    private VideoViewHolder.Clicklistener mClickListener;

    public interface Clicklistener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(VideoViewHolder.Clicklistener clicklistener){
        mClickListener=clicklistener;
    }
}

