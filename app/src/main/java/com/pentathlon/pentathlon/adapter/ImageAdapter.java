package com.pentathlon.pentathlon.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.models.homepage.BannersItem;
import com.pentathlon.pentathlon.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    List<BannersItem> bannersItemList;

    Activity activity;

    public ImageAdapter(List<BannersItem> bannersItemList, Activity activity) {
        this.bannersItemList = bannersItemList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String url=Util.imgUrl+bannersItemList.get(position).getImage();
        Picasso.with(activity)
                .load(url)
                .fit()
                .placeholder(activity.getDrawable(R.drawable.demoimg))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return bannersItemList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}

