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

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private VideoListAdapter adapter;
    private ProgressBar progressBar;
    private List<BunnyVideo> videoList = new ArrayList<>();

    // TODO: Replace with your actual libraryId and accessKey
    private static final String LIBRARY_ID = "187537";
    private static final String ACCESS_KEY = "0fac53fb-666b-4a9a-a62fc351ec9f-9458-40ff";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoListAdapter(this, videoList, video -> {
            Intent intent = new Intent(MainActivity.this, VideoPlayerActivity.class);
            intent.putExtra("VIDEO_URL", video.getVideoUrl());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        fetchVideos();
    }

    private void fetchVideos() {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://video.bunnycdn.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BunnyStreamApi api = retrofit.create(BunnyStreamApi.class);
        api.getVideos(LIBRARY_ID, ACCESS_KEY).enqueue(new Callback<List<BunnyVideo>>() {
            @Override
            public void onResponse(Call<List<BunnyVideo>> call, Response<List<BunnyVideo>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    videoList.clear();
                    videoList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load videos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BunnyVideo>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
} 