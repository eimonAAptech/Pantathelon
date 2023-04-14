package com.pentathlon.pentathlon.util;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.pentathlon.pentathlon.models.userDetails.UserDetailsResponse;

public class PreferenceManager {
    Activity activity;
    SharedPreferences sharedPreferencesLogin;
    String loginPref_name = "loginpreference";
    String PREFS_NAME = "sharedpref";
    SharedPreferences sharedPref;


    public PreferenceManager(Activity activity) {
        this.activity = activity;
        sharedPref = activity.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        sharedPreferencesLogin = activity.getSharedPreferences(loginPref_name, MODE_PRIVATE);
    }

    public void saveData(UserDetailsResponse Data) {
        sharedPref.edit().putString("CustomerData", new Gson().toJson(Data)).apply();
    }

    public UserDetailsResponse getData() {
        String data = sharedPref.getString("CustomerData", null);
        if (data == null) return null;
        else
            return new Gson().fromJson(data, UserDetailsResponse.class);
    }

    public void saveToken(String token, String decive_id) {
        sharedPref.edit().putString("token", "Bearer " + token).apply();
        sharedPref.edit().putString("device_id", decive_id).apply();
    }

    public String getDeviceId() {
        return sharedPref.getString("device_id", "");
    }

    public boolean checkkey(String key) {
        return sharedPref.contains(key);
    }

    public String getToken() {
        return sharedPref.getString("token", "");
    }

    public void clearData() {
        sharedPref.edit().clear().apply();
    }


}
