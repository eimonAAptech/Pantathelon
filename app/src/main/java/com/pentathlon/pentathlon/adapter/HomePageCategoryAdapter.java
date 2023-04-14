package com.pentathlon.pentathlon.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.activity.home.ProductListActivity;
import com.pentathlon.pentathlon.activity.home.SubCategory;
import com.pentathlon.pentathlon.databinding.HomecategoryitemBinding;
import com.pentathlon.pentathlon.models.homecategory.ChildrenDataItem;
import com.pentathlon.pentathlon.util.Util;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class HomePageCategoryAdapter extends RecyclerView.Adapter<HomePageCategoryAdapter.ViewHolder> {

    List<ChildrenDataItem> categoryList;
    Activity activity;

    public HomePageCategoryAdapter(List<ChildrenDataItem> categoryList, Activity activity) {
        this.categoryList = categoryList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public HomePageCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HomecategoryitemBinding binding = HomecategoryitemBinding.inflate(LayoutInflater.from(activity), parent, false);
        return new HomePageCategoryAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomePageCategoryAdapter.ViewHolder holder, int position) {
        String url = Util.imgUrl + categoryList.get(position).getMenuIcon();

        Picasso.with(activity)
                .load(url)
                .fit()
                .placeholder(activity.getDrawable(R.drawable.demoimg))
                .into(holder.binding.imgDp);
        holder.binding.txtName.setText(categoryList.get(position).getName());

        holder.binding.llChangeDp.setOnClickListener(view -> {
            if (categoryList.get(position).getChildrenData().size() > 0) {
                Intent intent = new Intent(activity, SubCategory.class);
                intent.putExtra("list", (Serializable) categoryList.get(position).getChildrenData());
                activity.startActivity(intent);
//                activity.startActivity(new Intent(activity, SubCategory.class)
//                        .putExtra("list",categoryList.get(position).getChildrenData()));
            } else {
                activity.startActivity(new Intent(activity, ProductListActivity.class)
                        .putExtra("ID", categoryList.get(position).getId())
                        .putExtra("header", categoryList.get(position).getName()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        HomecategoryitemBinding binding;

        public ViewHolder(HomecategoryitemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
