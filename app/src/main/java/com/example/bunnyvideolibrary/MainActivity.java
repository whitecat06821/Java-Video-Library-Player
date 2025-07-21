package com.example.bunnyvideolibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.util.Log;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private VideoListAdapter adapter;
    private ProgressBar progressBar;
    private List<BunnyVideo> videoList = new ArrayList<>();

    // TODO: Replace with your actual libraryId and accessKey
    private static final String LIBRARY_ID = "187537";
    private static final String ACCESS_KEY = "0fac53fb-666b-4a9a-a62fc351ec9f-9458-40ff";
    private static final String TAG = "MainActivity";

    private List<VideoItem> convertToVideoItems(List<BunnyVideo> bunnyVideos) {
        List<VideoItem> videoItems = new ArrayList<>();
        for (BunnyVideo bunny : bunnyVideos) {
            VideoItem item = new VideoItem();
            item.setTitle(bunny.getTitle());
            // Construct thumbnail URL using Bunny Stream format
            String guid = bunny.getId();
            item.setThumbnailUrl("https://vz-b54866ea-63c.b-cdn.net/" + guid + "/thumbnails/thumbnail.jpg");
            // HLS playlist URL
            item.setVideoUrl("https://vz-b54866ea-63c.b-cdn.net/" + guid + "/playlist.m3u8");
            videoItems.add(item);
        }
        return videoItems;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Convert BunnyVideo list to VideoItem list for the adapter
        List<VideoItem> videoItems = convertToVideoItems(videoList);
        adapter = new VideoListAdapter(this, videoItems, video -> {
            Intent intent = new Intent(MainActivity.this, VideoPlayerActivity.class);
            intent.putExtra("video_url", video.getVideoUrl());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        fetchVideos();
    }

    private void fetchVideos() {
        progressBar.setVisibility(View.VISIBLE);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://video.bunnycdn.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        BunnyStreamApi api = retrofit.create(BunnyStreamApi.class);
        api.getVideos(LIBRARY_ID, ACCESS_KEY).enqueue(new Callback<BunnyVideoResponse>() {
            @Override
            public void onResponse(Call<BunnyVideoResponse> call, Response<BunnyVideoResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().getItems() != null) {
                    videoList.clear();
                    videoList.addAll(response.body().getItems());
                    List<VideoItem> videoItems = convertToVideoItems(videoList);
                    if (videoItems.isEmpty()) {
                        Toast.makeText(MainActivity.this, "No videos found.", Toast.LENGTH_LONG).show();
                        Log.e(TAG, "No videos found in the library.");
                    } else {
                        adapter = new VideoListAdapter(MainActivity.this, videoItems, video -> {
                            Intent intent = new Intent(MainActivity.this, VideoPlayerActivity.class);
                            intent.putExtra("video_url", video.getVideoUrl());
                            startActivity(intent);
                        });
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        Log.i(TAG, "Loaded " + videoItems.size() + " videos.");
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load videos (API error)", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "API error: " + response.code() + ", message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<BunnyVideoResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, "Network error", t);
            }
        });
    }
} 