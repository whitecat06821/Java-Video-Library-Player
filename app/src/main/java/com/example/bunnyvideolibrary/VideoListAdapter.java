package com.example.bunnyvideolibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoViewHolder> {
    public interface OnItemClickListener {
        void onVideoSelected(BunnyVideo video);
    }

    private List<BunnyVideo> videoList;
    private OnItemClickListener listener;
    private Context context;

    public VideoListAdapter(Context context, List<BunnyVideo> videoList, OnItemClickListener listener) {
        this.context = context;
        this.videoList = videoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        BunnyVideo video = videoList.get(position);
        holder.titleTextView.setText(video.getTitle());
        String id = video.getId() != null ? video.getId() : "";
        int videoLibraryId = video.getVideoLibraryId();
        if (!id.isEmpty() && videoLibraryId != 0) {
            String pullZone = "vz-" + videoLibraryId;
            String thumbnailUrl = "https://" + pullZone + ".b-cdn.net/thumbnails/" + id + ".jpg";
            Glide.with(context).load(thumbnailUrl).placeholder(R.drawable.placeholder).into(holder.thumbnailImageView);
            holder.itemView.setOnClickListener(v -> listener.onVideoSelected(video));
            holder.itemView.setEnabled(true);
        } else {
            // Set a placeholder image and disable click if data is invalid
            holder.thumbnailImageView.setImageResource(R.drawable.placeholder);
            holder.itemView.setOnClickListener(null);
            holder.itemView.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnailImageView;
        TextView titleTextView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.thumbnailImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
        }
    }
} 