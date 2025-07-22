package com.example.bunnyvideolibrary;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class VideoPlayerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        String videoId = getIntent().getStringExtra("video_id");
        // All code referencing BunnyVideoPlayer and related features has been removed or commented out.
    }
} 