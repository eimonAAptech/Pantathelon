package com.pentathlon.pentathlon.activity.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.pentathlon.pentathlon.adapter.SubCatagoryAdapter;
import com.pentathlon.pentathlon.databinding.ActivityProductDetialsBinding;
import com.pentathlon.pentathlon.databinding.ActivitySubCategoryBinding;
import com.pentathlon.pentathlon.databinding.PrimarybackgroundToobarBinding;
import com.pentathlon.pentathlon.models.homecategory.ChildrenDataItem;
import com.pentathlon.pentathlon.util.DataClass;

import java.util.HashMap;
import java.util.List;

public class SubCategory extends AppCompatActivity implements SubCatagoryAdapter.LayoutClick {
    ActivitySubCategoryBinding binding;
    PrimarybackgroundToobarBinding toobarBinding;
    List<ChildrenDataItem> categoryList;
    int level_count = 0;
    HashMap<Integer, List<ChildrenDataItem>> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        categoryList = (List<ChildrenDataItem>) getIntent().getSerializableExtra("list");
        map = new HashMap<>();

        toobarBinding = binding.toolbar;
        toobarBinding.pageTitle.setText("");
        toobarBinding.imgBack.setOnClickListener(v -> {
            if (level_count == 0) {
                super.onBackPressed();
            } else {
                SubCatagoryAdapter adapter = new SubCatagoryAdapter(this, map.get(level_count));
                adapter.onClick(this::onSubCatClick);
                binding.recyclerView.setAdapter(adapter);
                level_count -= 1;
            }
        });

        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        SubCatagoryAdapter adapter = new SubCatagoryAdapter(this, categoryList);
        adapter.onClick(this::onSubCatClick);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onSubCatClick(List<ChildrenDataItem> categoryList, int position) {
        if (categoryList.get(position).getChildrenData().size() > 0) {
            SubCatagoryAdapter adapter = new SubCatagoryAdapter(this, categoryList.get(position).getChildrenData());
            adapter.onClick(this::onSubCatClick);
            binding.recyclerView.setAdapter(adapter);
            level_count += 1;
            map.put(level_count, categoryList);

        } else {
            startActivity(new Intent(SubCategory.this, ProductListActivity.class)
                    .putExtra("ID", categoryList.get(position).getId())
                    .putExtra("header", categoryList.get(position).getName()));
        }
    }

    @Override
    public void onBackPressed() {
        if (level_count == 0) {
            super.onBackPressed();
        } else {
            SubCatagoryAdapter adapter = new SubCatagoryAdapter(this, map.get(level_count));
            adapter.onClick(this::onSubCatClick);
            binding.recyclerView.setAdapter(adapter);
            level_count -= 1;
        }
    }
}