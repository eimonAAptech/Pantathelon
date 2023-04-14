package com.pentathlon.pentathlon.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.models.getFilterModel.ValuesItem;

import java.util.ArrayList;
import java.util.List;

public class FilterChildAdapter extends RecyclerView.Adapter<FilterChildAdapter.ViewHolder> {
    Activity activity;
    List<ValuesItem> list;
    boolean ischecked = false;

    public FilterChildAdapter(Activity activity) {
        list = new ArrayList<>();
        this.activity = activity;
    }

    @NonNull
    @Override
    public FilterChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filer_child_item, parent, false);
        return new FilterChildAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterChildAdapter.ViewHolder holder, int position) {

        holder.text.setText(list.get(position).getDisplay());
        if (list.get(position).isSelected()) {
            holder.text.setTextColor(activity.getColor(R.color.orange));
            holder.img_check.setImageDrawable(activity.getDrawable(R.drawable.checkedfilter));
        } else {
            holder.text.setTextColor(activity.getColor(R.color.primarycolor));
            holder.img_check.setImageDrawable(activity.getDrawable(R.drawable.uncheckfilter));
        }

        holder.ll_check.setOnClickListener(view -> {
            if (list.get(position).isSelected()) {
                list.get(position).setSelected(false);
            } else {
                list.get(position).setSelected(true);
            }
            notifyDataSetChanged();

        });
//        if (ischecked) {
//
//        }

//        holder.text.setTextColor(activity.getColor(R.color.primarycolor));
//        holder.img_check.setImageDrawable(activity.getDrawable(R.drawable.uncheckfilter));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void swap(List<ValuesItem> list1) {
        list = new ArrayList<>();
        list.clear();
        list.addAll(list1);
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_check;
        TextView text;
        LinearLayout ll_check;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_check = itemView.findViewById(R.id.img_check);
            text = itemView.findViewById(R.id.text);
            ll_check = itemView.findViewById(R.id.ll_check);


        }
    }
}
