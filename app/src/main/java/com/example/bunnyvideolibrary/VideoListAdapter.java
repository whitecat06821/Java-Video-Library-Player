package com.example.bunnyvideolibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoViewHolder> {

    public interface OnPlayClickListener {
        void onPlayClick(VideoItem video);
    }

    private List<VideoItem> videoList;
    private Context context;
    private OnPlayClickListener playClickListener;

    public VideoListAdapter(Context context, List<VideoItem> videoList, OnPlayClickListener playClickListener) {
        this.context = context;
        this.videoList = videoList;
        this.playClickListener = playClickListener;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoItem video = videoList.get(position);
        holder.titleTextView.setText(video.getTitle());
        Glide.with(context)
                .load(video.getThumbnailUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnailImageView);

        holder.playButton.setOnClickListener(v -> {
            if (playClickListener != null) {
                playClickListener.onPlayClick(video);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnailImageView;
        TextView titleTextView;
        ImageButton playButton;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.thumbnailImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            playButton = itemView.findViewById(R.id.playButton);
        }
    }
} 