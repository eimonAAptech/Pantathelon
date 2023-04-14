package com.pentathlon.pentathlon.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pentathlon.pentathlon.databinding.ProductimageitemBinding;
import com.pentathlon.pentathlon.databinding.WishlistitemBinding;

import java.util.List;

public class ProductsImageAdapter extends RecyclerView.Adapter<ProductsImageAdapter.ViewHolder> {
    Activity activity;
    List<Drawable> list;

    public ProductsImageAdapter(Activity activity, List<Drawable> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public ProductsImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductimageitemBinding binding = ProductimageitemBinding.inflate(LayoutInflater.from(activity), parent, false);
        return new ProductsImageAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsImageAdapter.ViewHolder holder, int position) {

        holder.binding.img.setImageDrawable(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ProductimageitemBinding binding;

        public ViewHolder(ProductimageitemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
