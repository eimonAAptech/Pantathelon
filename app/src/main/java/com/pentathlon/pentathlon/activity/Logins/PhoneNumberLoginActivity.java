package com.pentathlon.pentathlon.activity.Logins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.pentathlon.pentathlon.Retrofit.RetrofitManager;
import com.pentathlon.pentathlon.databinding.ActivityPhoneNumberLoginBinding;
import com.pentathlon.pentathlon.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneNumberLoginActivity extends AppCompatActivity {
    ActivityPhoneNumberLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneNumberLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.getOtp.setOnClickListener(view -> {
            if (binding.edPhno.getText().toString().isEmpty() || binding.edPhno.getText().toString().length() < 10) {
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
            } else {
                getOTP();
            }

        });
        binding.signinWithEmail.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginWithEmailActivity.class));
            finish();
        });
        binding.signup.setOnClickListener(view -> {
            startActivity(new Intent(this, SignupActivity.class));
        });

    }

    private void getOTP() {
        if (RetrofitManager.isConnected(this)) {
            RetrofitManager.getInstance().getMyApi(true, this).loginwithPhone(binding.edPhno.getText().toString()).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Util.dismiss();
                    startActivity(new Intent(PhoneNumberLoginActivity.this, OtpActivity.class).putExtra("number", binding.edPhno.getText().toString()));
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Util.dismiss();
                }
            });
        }
    }
}