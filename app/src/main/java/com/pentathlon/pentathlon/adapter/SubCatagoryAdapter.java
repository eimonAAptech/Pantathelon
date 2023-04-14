package com.pentathlon.pentathlon.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.databinding.SubcategoryItemBinding;
import com.pentathlon.pentathlon.models.homecategory.ChildrenDataItem;
import com.pentathlon.pentathlon.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubCatagoryAdapter extends RecyclerView.Adapter<SubCatagoryAdapter.ViewHolder> {
    Activity activity;
    List<ChildrenDataItem> categoryList;
    LayoutClick click;

    public SubCatagoryAdapter(Activity activity, List<ChildrenDataItem> categoryList) {
        this.activity = activity;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public SubCatagoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SubcategoryItemBinding binding = SubcategoryItemBinding.inflate(LayoutInflater.from(activity), parent, false);
        return new SubCatagoryAdapter.ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull SubCatagoryAdapter.ViewHolder holder, int position) {
        String url = Util.imgUrl + categoryList.get(position).getMenuIcon();
        Picasso.with(activity)
                .load(url)
                .fit()
                .placeholder(activity.getDrawable(R.drawable.demoimg))
                .into(holder.binding.imgSubCat);

        holder.binding.subcatName.setText(categoryList.get(position).getName());
        holder.binding.llLayout.setOnClickListener(view -> {
            click.onSubCatClick(categoryList, position);
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SubcategoryItemBinding binding;

        public ViewHolder(SubcategoryItemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }

    public interface LayoutClick {
        void onSubCatClick(List<ChildrenDataItem> categoryList, int position);
    }

    public void onClick(LayoutClick click) {
        this.click = click;
    }
}
