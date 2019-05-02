package com.example.shreshth.googleProject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {


    public static final long SPLASH_SCREEN_TIMEOUT=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent dashBoardIntent=new Intent(SplashActivity.this,DashBoardActivity.class);
                startActivity(dashBoardIntent);
                finish();
            }
        },SPLASH_SCREEN_TIMEOUT);
    }
}
