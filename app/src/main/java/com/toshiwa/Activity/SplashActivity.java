package com.toshiwa.Activity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.toshiwa.MainActivity;
import com.toshiwa.Preferences.SharedPreferencesManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(SharedPreferencesManager.getIsLogin(SplashActivity.this))
        {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
            finish();

        }
        else {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
            finish();
        }
    }

 }

