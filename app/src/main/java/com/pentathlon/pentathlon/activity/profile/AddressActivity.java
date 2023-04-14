package com.pentathlon.pentathlon.activity.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.activity.home.SubCategory;
import com.pentathlon.pentathlon.databinding.ActivityAddBinding;
import com.pentathlon.pentathlon.databinding.PrimarybackgroundToobarBinding;
import com.pentathlon.pentathlon.util.DataClass;

import java.io.Serializable;

public class AddressActivity extends AppCompatActivity {
    ActivityAddBinding binding;
    PrimarybackgroundToobarBinding toobarBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        toobarBinding = binding.toolbar;
        toobarBinding.imgBack.setOnClickListener(view -> {
            finish();
        });



        toobarBinding.pageTitle.setText("My Addresses");
        binding.addNewAddress.setOnClickListener(view -> {
            startActivity(new Intent(this, AddAdressActivity.class));
        });
    }
}