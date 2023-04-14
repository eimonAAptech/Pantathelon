package com.pentathlon.pentathlon.adapter;

import android.annotation.SuppressLint;
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
import com.pentathlon.pentathlon.models.homepage.NewproductsItem;
import com.pentathlon.pentathlon.util.Util;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class NewArrivalAdapter extends RecyclerView.Adapter<NewArrivalAdapter.ViewHolder> {
    List<NewproductsItem> list;
    Activity activity;

    public NewArrivalAdapter(List<NewproductsItem> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public NewArrivalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewproductsitemBinding binding = NewproductsitemBinding.inflate(LayoutInflater.from(activity), parent, false);
        return new NewArrivalAdapter.ViewHolder(binding);
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull NewArrivalAdapter.ViewHolder holder, int i) {
        NewproductsItem item = list.get(i);
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
            //   String newValue = Double.toString(Math.floor(val1));
            ;

            holder.binding.txtDiscountPrice.setText(Util.getPrice(item.getPrice()));
            holder.binding.txtActualPrice.setVisibility(View.GONE);
        } else {
            double val1 = Double.parseDouble(item.getPrice());
            //  String newValue1 = Double.toString(Math.floor(val1));

            double val2 = Double.parseDouble(item.getSpecialPrice());
            //  String newValue2 = Double.toString(Math.floor(val2));

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
