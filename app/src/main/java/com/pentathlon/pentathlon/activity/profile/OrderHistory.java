package com.pentathlon.pentathlon.activity.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.adapter.OrderhistoryAdapter;
import com.pentathlon.pentathlon.databinding.ActivityAddBinding;
import com.pentathlon.pentathlon.databinding.ActivityOrderHistoryBinding;

public class OrderHistory extends AppCompatActivity {
    ActivityOrderHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.recyclerOrderHistory.setLayoutManager(new LinearLayoutManager(this));
        OrderhistoryAdapter adapter = new OrderhistoryAdapter(this);
        binding.recyclerOrderHistory.setAdapter(adapter);


        binding.llFilter.setOnClickListener(view -> {
            startActivity(new Intent(this, ReviewActivity.class));
        });
    }
}