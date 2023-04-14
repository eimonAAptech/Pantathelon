package com.pentathlon.pentathlon.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.widget.Toast;

import com.pentathlon.pentathlon.R;
import com.pentathlon.pentathlon.activity.Logins.SignupActivity;
import com.pentathlon.pentathlon.models.SignUpResponse.SignUpResponse;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;

public class Util {
    private static Dialog dialog;
    public static String imgUrl = "http://dev.dapl.ml/";

    public static void addloaded(Activity activity) {
        dialog = new Dialog(activity);
//       if(isConnected(activity)){
        dialog.setContentView(R.layout.transparent_dialog);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        show();
        //    }

    }
    public static void errorMessage( Response response,Activity activity){
        try {
            if(response.code()==500){
                Toast.makeText(activity, "Server Error", Toast.LENGTH_SHORT).show();

            }else {
                String error = response.errorBody().string();
                JSONObject object = new JSONObject(error);
                String msg = object.optString("message");
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getPrice(String val) {
        double val2 = Double.valueOf(val);

        return new DecimalFormat("#").format(val2);
    }

    public static void createAlertDialog(Activity activity, String msg, Class destinationclass) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(msg);
        builder.setPositiveButton("OK", (dialog, which) -> {
            // When the user click yes button then app will close
            activity.startActivity(new Intent(activity, destinationclass));
        });
        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();


    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void show() {
        dialog.show();
    }

    public static void dismiss() {
        dialog.dismiss();

    }
}
