package com.pentathlon.pentathlon.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pentathlon.pentathlon.databinding.OderhistoryitemBinding;
import com.pentathlon.pentathlon.databinding.SellingbranditemBinding;

public class OrderhistoryAdapter extends RecyclerView.Adapter<OrderhistoryAdapter.ViewHolder> {
    Activity activity;

    public OrderhistoryAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public OrderhistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OderhistoryitemBinding binding = OderhistoryitemBinding.inflate(LayoutInflater.from(activity), parent, false);
        return new OrderhistoryAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderhistoryAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        OderhistoryitemBinding binding;

        public ViewHolder(@NonNull OderhistoryitemBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
