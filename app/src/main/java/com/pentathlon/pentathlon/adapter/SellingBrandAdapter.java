package com.pentathlon.pentathlon.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.databinding.NewproductsitemBinding;
import com.pentathlon.pentathlon.databinding.SellingbranditemBinding;
import com.pentathlon.pentathlon.models.homepage.BestsellingbrandsItem;
import com.pentathlon.pentathlon.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SellingBrandAdapter extends RecyclerView.Adapter<SellingBrandAdapter.ViewHolder> {
    Activity activity;
    List<BestsellingbrandsItem> list;

    public SellingBrandAdapter(Activity activity, List<BestsellingbrandsItem> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public SellingBrandAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SellingbranditemBinding binding = SellingbranditemBinding.inflate(LayoutInflater.from(activity), parent, false);
        return new SellingBrandAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SellingBrandAdapter.ViewHolder holder, int position) {
        BestsellingbrandsItem item = list.get(position);
        Picasso.with(activity)
                .load(Util.imgUrl + item.getImage())
                .placeholder(activity.getDrawable(R.drawable.demoimg))
                .into(holder.binding.imgBrand);
        Picasso.with(activity)
                .load(Util.imgUrl + item.getIcon())
                .placeholder(activity.getDrawable(R.drawable.demoimg))
                .into(holder.binding.imgBraidLogo);

        holder.binding.txtOffer.setText(item.getTitle());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SellingbranditemBinding binding;

        public ViewHolder(SellingbranditemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
