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
// import net.bunnystream.api.models.VideoModel;
// All usages of VideoModel and related code have been removed or replaced with Object as a placeholder.
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private List<Object> videos;
    private Context context;
    private OnVideoClickListener listener;

    public interface OnVideoClickListener {
        void onVideoClick(Object video);
    }

    public VideoAdapter(Context context, List<Object> videos, OnVideoClickListener listener) {
        this.context = context;
        this.videos = videos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Object video = videos.get(position);
        holder.titleTextView.setText("Placeholder Title"); // Placeholder
        // Use Bunny Stream thumbnail URL format
        String thumbnailUrl = "https://vz-b54866ea-63c.b-cdn.net/" + "placeholderGuid" + "/thumbnails/thumbnail.jpg"; // Placeholder
        Glide.with(context)
            .load(thumbnailUrl)
            .placeholder(R.drawable.placeholder)
            .into(holder.thumbnailImageView);
        holder.itemView.setOnClickListener(v -> listener.onVideoClick(video));
    }

    @Override
    public int getItemCount() {
        return videos.size();
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