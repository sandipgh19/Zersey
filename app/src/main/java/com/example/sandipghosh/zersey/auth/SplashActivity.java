package com.example.sandipghosh.zersey.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.sandipghosh.zersey.ImageDisplay.MainActivity;
import com.example.sandipghosh.zersey.ImageDisplay.SecondActivity;

/**
 * Created by sandipghosh on 11/05/17.
 */

public class SplashActivity extends AppCompatActivity {

    String TAG = "SplashActivity";

    SharedPreferences sharedPreferences;

    int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("ZerseyDetails", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("email", "").length() == 0) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, RC_SIGN_IN);
            finish();
        } else {
            // startMyActivity();
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
