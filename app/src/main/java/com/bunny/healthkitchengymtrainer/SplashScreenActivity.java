package com.bunny.healthkitchengymtrainer;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bunny.healthkitchengymtrainer.HomeActivity.HomeActivity;

public class SplashScreenActivity extends AppCompatActivity {
    public static int SPLASH_TIME_OUT_FIRST = 3000;
    public static int SPLASH_TIME_OUT = 1000;

    private static boolean IS_SPLASH_SCREEN_LAUNCHED = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        overridePendingTransition(R.anim.fadein , R.anim.fadeout);


        if(IS_SPLASH_SCREEN_LAUNCHED == true){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent homeIntent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(homeIntent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }else {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent homeIntent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(homeIntent);
                    finish();
                }
            }, SPLASH_TIME_OUT_FIRST);
            IS_SPLASH_SCREEN_LAUNCHED = true;
        }
    }
}
