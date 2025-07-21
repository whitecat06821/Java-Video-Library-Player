package com.example.bunnyvideolibrary;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import net.bunnystream.player.ui.BunnyVideoPlayer;

public class VideoPlayerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        String videoId = getIntent().getStringExtra("video_id");
        BunnyVideoPlayer player = findViewById(R.id.videoPlayer);
        player.playVideo(videoId);
    }
} 