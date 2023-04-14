package com.pentathlon.pentathlon.activity.Logins;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.databinding.ActivityForgetPassWordBinding;
import com.pentathlon.pentathlon.databinding.ActivitySignupBinding;
import com.pentathlon.pentathlon.util.Util;

public class ForgetPassWordActivity extends AppCompatActivity {
    ActivityForgetPassWordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPassWordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgBack.setOnClickListener(view -> {
            finish();
        });
        binding.singIn.setOnClickListener(view -> {
            if (binding.edemail.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
            } else if (!Util.isEmailValid(binding.edemail.getText().toString())) {
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
            }
        });


    }
}