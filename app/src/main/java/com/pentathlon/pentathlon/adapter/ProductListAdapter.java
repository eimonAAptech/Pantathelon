package com.pentathlon.pentathlon.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pentathlon.pentathlon.databinding.NewproductsitemBinding;
import com.pentathlon.pentathlon.databinding.ProductListItemBinding;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    Activity activity;

    @NonNull
    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductListItemBinding binding = ProductListItemBinding.inflate(LayoutInflater.from(activity), parent, false);
        return new ProductListAdapter.ViewHolder(binding);    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ProductListItemBinding binding;
        public ViewHolder(@NonNull ProductListItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;        }
    }
}
