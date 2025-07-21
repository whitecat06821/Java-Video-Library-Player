package com.example.bunnyvideolibrary;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;
import android.widget.ImageButton;
import android.view.View;
import android.widget.ProgressBar;
import com.google.android.exoplayer2.Player;

public class VideoPlayerActivity extends AppCompatActivity {
    private ExoPlayer player;
    private PlayerView playerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        playerView = findViewById(R.id.playerView);
        ImageButton backButton = findViewById(R.id.backButton);
        ProgressBar loadingIndicator = findViewById(R.id.loadingIndicator);
        backButton.setOnClickListener(v -> {
            // TODO: Add logic here to cast to Samsung Smart TV using Smart View SDK or DIAL protocol
            finish();
        });
        String videoUrl = getIntent().getStringExtra("VIDEO_URL");

        // TODO: Organize player logic for future casting integration (e.g., pause local playback when casting)
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(videoUrl));
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();

        // Show loading indicator while buffering/loading
        loadingIndicator.setVisibility(View.VISIBLE);
        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_BUFFERING || state == Player.STATE_IDLE) {
                    loadingIndicator.setVisibility(View.VISIBLE);
                } else {
                    loadingIndicator.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
        }
    }
} 