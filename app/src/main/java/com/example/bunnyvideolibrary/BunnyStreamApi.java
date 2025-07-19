package com.example.bunnyvideolibrary;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface BunnyStreamApi {
    @GET("library/{libraryId}/videos")
    Call<List<BunnyVideo>> getVideos(
            @Path("libraryId") String libraryId,
            @Header("AccessKey") String accessKey
    );
} 