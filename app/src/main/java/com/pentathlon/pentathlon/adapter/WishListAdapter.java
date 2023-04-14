package com.pentathlon.pentathlon.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pentathlon.pentathlon.databinding.SellingbranditemBinding;
import com.pentathlon.pentathlon.databinding.WishlistitemBinding;


public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {
    Activity activity;

    public WishListAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public WishListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WishlistitemBinding binding = WishlistitemBinding.inflate(LayoutInflater.from(activity), parent, false);
        return new WishListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        WishlistitemBinding binding;

        public ViewHolder(WishlistitemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
