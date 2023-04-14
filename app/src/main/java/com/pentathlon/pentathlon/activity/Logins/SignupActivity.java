package com.pentathlon.pentathlon.activity.Logins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.pentathlon.pentathlon.Retrofit.RetrofitManager;
import com.pentathlon.pentathlon.databinding.ActivitySignupBinding;
import com.pentathlon.pentathlon.models.SignUpModel;
import com.pentathlon.pentathlon.models.SignUpResponse.SignUpResponse;
import com.pentathlon.pentathlon.util.Util;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //checkallField();
        binding.singIn.setOnClickListener(view -> {
            //  startActivity(new Intent(this, SignupActivity.class));
            // checkallField();
            registerUser();

        });

    }

    private void checkallField() {


        if (binding.fname.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Your First Name", Toast.LENGTH_SHORT).show();
        } else if (binding.lname.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Your Last Name", Toast.LENGTH_SHORT).show();
        } else if (binding.edemail.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
        } else if (!Util.isEmailValid(binding.edemail.getText().toString())) {
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
        } else if (binding.edPhno.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Your phone number", Toast.LENGTH_SHORT).show();
        } else if (binding.edPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
        } else if (binding.edPassword.getText().toString().length() < 4) {
            Toast.makeText(this, "Password length Must be 4 characters or more", Toast.LENGTH_SHORT).show();
        } else if (!binding.edConpass.getText().toString().equals(binding.edPassword.getText().toString())) {
            Toast.makeText(this, "Confirm Password does not match", Toast.LENGTH_SHORT).show();
        } else {
            registerUser();
        }
    }

    private void registerUser() {
//        SignUpModel.Customer customer = new SignUpModel.Customer("test2@custoer.com", "abc", "def", new SignUpModel.ExtensionAttributes("9199999998"));
//    SignUpModel model=new SignUpModel(customer,"abcd123456");
        JsonObject object = new JsonObject();
        JsonObject customer = new JsonObject();

        JsonObject phno = new JsonObject();
        customer.addProperty("email", binding.edemail.getText().toString());
        customer.addProperty("firstname", binding.fname.getText().toString());
        customer.addProperty("lastname", binding.lname.getText().toString());
        phno.addProperty("mobile", binding.edPhno.getText().toString());
        customer.add("extension_attributes", phno);
        object.add("customer", customer);
        object.addProperty("password", binding.edPassword.getText().toString().length());


        RetrofitManager.getInstance().getMyApi(true, this).createCustomer(object).enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {

                if (response.code() == 200) {
                    getOTP();
                } else if (response.code() == 400) {
                    Util.dismiss();
                    Util.errorMessage(response, SignupActivity.this);
                } else {
                    Util.dismiss();
                    Toast.makeText(SignupActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                }
                //   Log.d("response", response.body().toString());
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Util.dismiss();
                Toast.makeText(SignupActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getOTP() {
        if (RetrofitManager.isConnected(this)) {
            RetrofitManager.getInstance().getMyApi(true, this).loginwithPhone(binding.edPhno.getText().toString()).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Util.dismiss();
                    startActivity(new Intent(SignupActivity.this, OtpActivity.class).putExtra("number", binding.edPhno.getText().toString()));
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Util.dismiss();
                }
            });
        }
    }

}