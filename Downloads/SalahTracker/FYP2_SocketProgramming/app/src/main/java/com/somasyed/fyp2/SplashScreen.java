package com.somasyed.fyp2;

import android.content.Intent;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class SplashScreen extends AppCompatActivity {
    public Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        handler=new Handler();

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                //The following code will execute after the 5 seconds.

                try {

                    //Go to next page i.e, start the next activity.
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);

                    //Let's Finish Splash Activity since we don't want to show this when user press back button.
                    finish();
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }, 2000);  // Give a 5 seconds delay.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //Remove all the callbacks otherwise navigation will execute even after activity is killed or closed.
        handler.removeCallbacksAndMessages(null);
    }

}
