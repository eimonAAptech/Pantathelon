package com.pentathlon.pentathlon.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.activity.home.ProductDetials;
import com.pentathlon.pentathlon.databinding.NewproductsitemBinding;
import com.pentathlon.pentathlon.models.homepage.BestsellerItem;
import com.pentathlon.pentathlon.models.homepage.NewproductsItem;
import com.pentathlon.pentathlon.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BestSellingProductAdapter extends RecyclerView.Adapter<BestSellingProductAdapter.ViewHolder> {

    List<BestsellerItem> list;

    public BestSellingProductAdapter(List<BestsellerItem> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    Activity activity;

    @NonNull
    @Override
    public BestSellingProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewproductsitemBinding binding = NewproductsitemBinding.inflate(LayoutInflater.from(activity), parent, false);
        return new BestSellingProductAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BestSellingProductAdapter.ViewHolder holder, int i) {

        BestsellerItem item = list.get(i);
        String url = Util.imgUrl + item.getImage();
        Picasso.with(activity)
                .load(url)
                .placeholder(activity.getDrawable(R.drawable.demoimg))
                .into(holder.binding.imgProduct);
        holder.binding.productName.setText(item.getName());

        if (Double.parseDouble(item.getRating()) <= 0) {
            holder.binding.txtStar.setVisibility(View.GONE);
        } else {
            holder.binding.txtStar.setText(activity.getResources().getString(R.string.star) + item.getRating());
            holder.binding.txtStar.setVisibility(View.VISIBLE);
        }
        if (item.getSpecialPrice().isEmpty()) {
            holder.binding.txtDiscountPrice.setText(Util.getPrice(item.getPrice()));
            holder.binding.txtActualPrice.setVisibility(View.GONE);
        } else {
            holder.binding.txtActualPrice.setText(Util.getPrice(item.getPrice()));
            holder.binding.txtDiscountPrice.setText(Util.getPrice(item.getSpecialPrice()));
            holder.binding.txtActualPrice.setVisibility(View.VISIBLE);
            holder.binding.txtActualPrice.setPaintFlags
                    (holder.binding.txtActualPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.binding.rlLayout.setOnClickListener(view -> {
            activity.startActivity(new Intent(activity, ProductDetials.class)
                    .putExtra("sku", item.getSku()));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        NewproductsitemBinding binding;

        public ViewHolder(NewproductsitemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
