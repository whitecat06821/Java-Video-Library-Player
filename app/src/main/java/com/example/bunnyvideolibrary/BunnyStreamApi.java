package com.example.bunnyvideolibrary;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

// Add import for BunnyVideoResponse
import com.example.bunnyvideolibrary.BunnyVideoResponse;

public interface BunnyStreamApi {
    @GET("library/{libraryId}/videos")
    Call<BunnyVideoResponse> getVideos(
            @Path("libraryId") String libraryId,
            @Header("AccessKey") String accessKey
    );
} 