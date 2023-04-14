package com.pentathlon.pentathlon.Retrofit;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.pentathlon.pentathlon.util.Util.addloaded;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RetrofitManager {
    private static RetrofitManager instance = null;
    private ApiList myApi;
    String baseUrl = "http://dev.dapl.ml/";//dev
    Activity activity;

    //  String baseUrl = "http://bazoki.dapl.ml/";//dev


    private RetrofitManager() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();


        myApi = retrofit.create(ApiList.class);
    }

    public static synchronized RetrofitManager getInstance() {
        if (instance == null) {
            instance = new RetrofitManager();
        }
        return instance;
    }

    public ApiList getMyApi(boolean isloadershow, Activity activity) {
        if (isloadershow) {
            addloaded(activity);
        }

        return myApi;
    }


    public static boolean isConnected(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        } else {
            Toast.makeText(activity, "NO Internet", Toast.LENGTH_SHORT).show();
            return false;
        }
        return false;
    }
}
