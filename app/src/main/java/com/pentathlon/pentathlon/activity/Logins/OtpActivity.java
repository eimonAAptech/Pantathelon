package com.pentathlon.pentathlon.activity.Logins;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.pentathlon.pentathlon.Retrofit.RetrofitManager;
import com.pentathlon.pentathlon.activity.home.HomePageActivity;
import com.pentathlon.pentathlon.databinding.ActivityOtpBinding;
import com.pentathlon.pentathlon.models.userDetails.UserDetailsResponse;
import com.pentathlon.pentathlon.util.GenericTextWatcher;
import com.pentathlon.pentathlon.util.PreferenceManager;
import com.pentathlon.pentathlon.util.Util;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpActivity extends AppCompatActivity {
    ActivityOtpBinding binding;
    String phno = "";
    String userToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        phno = getIntent().getExtras().getString("number");

        EditText[] edit = {binding.otpEditBox1, binding.otpEditBox2, binding.otpEditBox3,
                binding.otpEditBox4, binding.otpEditBox5, binding.otpEditBox6};
        binding.otpEditBox1.addTextChangedListener(new GenericTextWatcher(binding.otpEditBox1, edit));
        binding.otpEditBox2.addTextChangedListener(new GenericTextWatcher(binding.otpEditBox2, edit));
        binding.otpEditBox3.addTextChangedListener(new GenericTextWatcher(binding.otpEditBox3, edit));
        binding.otpEditBox4.addTextChangedListener(new GenericTextWatcher(binding.otpEditBox4, edit));
        binding.otpEditBox5.addTextChangedListener(new GenericTextWatcher(binding.otpEditBox5, edit));
        binding.otpEditBox6.addTextChangedListener(new GenericTextWatcher(binding.otpEditBox6, edit));

        binding.editNumber.setOnClickListener(view -> finish());
        binding.submitotp.setOnClickListener(view -> {
            String otp = binding.otpEditBox1.getText().toString()
                    + binding.otpEditBox2.getText().toString()
                    + binding.otpEditBox3.getText().toString()
                    + binding.otpEditBox4.getText().toString()
                    + binding.otpEditBox5.getText().toString()
                    + binding.otpEditBox6.getText().toString();


            if (otp.isEmpty()) {
                Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
            } else {
                submitOTP(otp);
            }
        });
    }

    private void submitOTP(String otp) {
        @SuppressLint("HardwareIds") String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        if (RetrofitManager.isConnected(this)) {
            RetrofitManager.getInstance().getMyApi(true, this)
                    .verifyOTP("1234567890", "1234", android_id, "")
                    .enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Util.dismiss();

                            try {
                                JSONObject object = new JSONObject(response.body().toString());
                                if (object.optString("success").equalsIgnoreCase("true")) {
                                    userToken = object.getString("token");
                                    new PreferenceManager(OtpActivity.this).saveToken(userToken,android_id);

                                    getUserDetails();
                                }
                            } catch (Exception e) {

                            }
                        }
                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Util.dismiss();

                        }
                    });
        }
    }

    private void getUserDetails() {
        RetrofitManager.getInstance().getMyApi(false, this)
                .getCustomerDetails(new PreferenceManager(this).getToken())
                .enqueue(new Callback<UserDetailsResponse>() {
                    @Override
                    public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {
                        Util.dismiss();
                        if (response.code() == 200) {
                            new PreferenceManager(OtpActivity.this).saveData(response.body());
                            startActivity(new Intent(OtpActivity.this, HomePageActivity.class));
                            finishAffinity();
                        } else {
                            Util.errorMessage(response, OtpActivity.this);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                        Util.dismiss();
                    }
                });
    }
}