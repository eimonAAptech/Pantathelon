package com.pentathlon.pentathlon.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.models.getFilterModel.DataItem;
import com.pentathlon.pentathlon.models.getFilterModel.ValuesItem;

import java.util.ArrayList;
import java.util.List;

public class FilterParentAdapter extends RecyclerView.Adapter<FilterParentAdapter.ViewHolder> {
    Activity activity;
    LayoutClick click;
    int clicked_position = 0;
    List<com.pentathlon.pentathlon.models.getFilterModel.DataItem>list;

    public FilterParentAdapter(Activity activity) {
        list=new ArrayList<>();
        this.activity=activity;
    }

    @NonNull
    @Override
    public FilterParentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filer_parent_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterParentAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txt_parent.setText(list.get(position).getCode());
        holder.txt_parent.setOnClickListener(view -> {
            clicked_position = position;
            click.onparentClick(list.get(position).getValues());
            notifyDataSetChanged();
        });
        if (clicked_position == position) {
            holder.txt_parent.setBackgroundColor(activity.getColor(R.color.white));
            holder.txt_parent.setTextColor(activity.getColor(R.color.primarycolor));
        } else {
            holder.txt_parent.setBackgroundColor(activity.getColor(R.color.primarycolor));
            holder.txt_parent.setTextColor(activity.getColor(R.color.white));
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_parent = itemView.findViewById(R.id.txt_parent);

        }
    }
    public void swap(List<DataItem> list1) {
        list = new ArrayList<>();
        list.clear();
        list.addAll(list1);
        notifyDataSetChanged();

    }

    public interface LayoutClick {
        void onparentClick(List<ValuesItem> values);
    }

    public void onClick(LayoutClick click) {
        this.click = click;
    }
}
