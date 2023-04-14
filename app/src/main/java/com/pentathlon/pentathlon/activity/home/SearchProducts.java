package com.pentathlon.pentathlon.activity.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pentathlon.pentathlon.databinding.ActivityProductDetialsBinding;
import com.pentathlon.pentathlon.databinding.ActivitySearchProductsBinding;

public class SearchProducts extends AppCompatActivity {
    ActivitySearchProductsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}