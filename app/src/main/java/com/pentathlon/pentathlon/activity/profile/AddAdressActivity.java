package com.pentathlon.pentathlon.activity.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.databinding.ActivityAddAdressBinding;
import com.pentathlon.pentathlon.databinding.ActivityAddBinding;
import com.pentathlon.pentathlon.databinding.PrimarybackgroundToobarBinding;

public class AddAdressActivity extends AppCompatActivity {
    ActivityAddAdressBinding binding;
    PrimarybackgroundToobarBinding toobarBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddAdressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toobarBinding = binding.llToobar;
        toobarBinding.imgBack.setOnClickListener(view -> {
            finish();
        });
        toobarBinding.pageTitle.setText("My Addresses");
    }
}