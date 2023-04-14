package com.pentathlon.pentathlon.activity.Logins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.Retrofit.RetrofitManager;
import com.pentathlon.pentathlon.activity.home.HomePageActivity;
import com.pentathlon.pentathlon.databinding.ActivityLoginBinding;
import com.pentathlon.pentathlon.models.userDetails.UserDetailsResponse;
import com.pentathlon.pentathlon.util.PreferenceManager;
import com.pentathlon.pentathlon.util.Util;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginWithEmailActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    boolean ischecked = false;
    String userToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.checkboxLayout.setOnClickListener(view -> {
            if (ischecked) {
                ischecked = false;
                binding.checkboxImg.setImageDrawable(getDrawable(R.drawable.unchecked));
            } else {
                ischecked = true;
                binding.checkboxImg.setImageDrawable(getDrawable(R.drawable.checked));
            }
        });
        binding.signup.setOnClickListener(view -> {


            startActivity(new Intent(this, SignupActivity.class));
        });
        binding.signinWithmobile.setOnClickListener(view -> {
            startActivity(new Intent(this, PhoneNumberLoginActivity.class));
        });
        binding.singId.setOnClickListener(view -> {
            if (binding.edEmail.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
            } else if (!Util.isEmailValid(binding.edEmail.getText().toString())) {
                Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            } else if (binding.edPass.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            } else {
                if (RetrofitManager.isConnected(this))
                    login();
            }
              //   startActivity(new Intent(this, HomePageActivity.class));


        });
        binding.forgetPassword.setOnClickListener(view -> {
            startActivity(new Intent(this, ForgetPassWordActivity.class));
        });


    }

    private void login() {
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        RetrofitManager.getInstance().getMyApi(false, this)
                .logInWithEmail(binding.edEmail.getText().toString().trim(),
                        binding.edPass.getText().toString().trim(), android_id, "")
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.d("Log", response.toString());
                   //     startActivity(new Intent(this, HomePageActivity.class));
                        Util.dismiss();

                        try {
                            JSONObject object = new JSONObject(response.body().toString());
                            if (object.optString("success").equalsIgnoreCase("true")) {
                                userToken = object.getString("token");
                                new PreferenceManager(LoginWithEmailActivity.this).saveToken(userToken,android_id);

                                getUserDetails();
                            }
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.d("Log", t.toString());

                    }
                });
    }
    private void getUserDetails() {
        RetrofitManager.getInstance().getMyApi(false, this)
                .getCustomerDetails(new PreferenceManager(this).getToken())
                .enqueue(new Callback<UserDetailsResponse>() {
                    @Override
                    public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {
                        Util.dismiss();
                        if (response.code() == 200) {
                            new PreferenceManager(LoginWithEmailActivity.this).saveData(response.body());
                            startActivity(new Intent(LoginWithEmailActivity.this, HomePageActivity.class));
                            finishAffinity();
                        } else {
                            Util.errorMessage(response, LoginWithEmailActivity.this);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                        Util.dismiss();
                        Toast.makeText(LoginWithEmailActivity.this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}