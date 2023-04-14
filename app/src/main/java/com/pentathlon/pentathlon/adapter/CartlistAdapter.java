package com.pentathlon.pentathlon.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pentathlon.pentathlon.databinding.CartlistitemBinding;
import com.pentathlon.pentathlon.databinding.WishlistitemBinding;

public class CartlistAdapter extends RecyclerView.Adapter<CartlistAdapter.ViewHolder> {
    Activity activity;

    public CartlistAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public CartlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartlistitemBinding binding = CartlistitemBinding.inflate(LayoutInflater.from(activity), parent, false);
        return new CartlistAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartlistAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CartlistitemBinding binding;

        public ViewHolder(CartlistitemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
