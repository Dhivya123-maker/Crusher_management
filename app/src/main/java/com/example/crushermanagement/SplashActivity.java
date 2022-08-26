package com.example.crushermanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.crushermanagement.Dashboard.DashboardClass;
import com.example.crushermanagement.utils.PreferenceUtils;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override

            public void run() {
                if(PreferenceUtils.getToken(SplashActivity.this) != null) {
                    Intent intent = new Intent(SplashActivity.this, DashboardClass.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SplashActivity.this, Login.class);
                    startActivity(intent);
                }

            }
        }, SPLASH_TIME_OUT);


    }
}