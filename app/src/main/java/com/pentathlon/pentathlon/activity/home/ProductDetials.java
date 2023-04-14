package com.pentathlon.pentathlon.activity.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.adapter.ProductsImageAdapter;

import com.pentathlon.pentathlon.databinding.ActivityProductDetialsBinding;
import com.pentathlon.pentathlon.util.CirclePagerIndicatorDecoration;

import java.util.ArrayList;
import java.util.List;

public class ProductDetials extends AppCompatActivity {
    ActivityProductDetialsBinding binding;
    List<Drawable> list;
    String sku = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetialsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sku = getIntent().getStringExtra("sku");
        list = new ArrayList<>();
        list.add(getDrawable(R.drawable.footbarhome));
        list.add(getDrawable(R.drawable.demoimg));
        list.add(getDrawable(R.drawable.footbarhome));
        binding.recyclerProductImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerProductImage.setAdapter(new ProductsImageAdapter(this, list));
        binding.recyclerProductImage.addItemDecoration(new CirclePagerIndicatorDecoration(getColor(R.color.white)));

        PagerSnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(binding.recyclerProductImage);
        binding.recyclerProductImage.setItemAnimator(null);

    }
}