package com.displayfort.mvpatel.Screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.displayfort.mvpatel.Base.BaseActivity;
import com.displayfort.mvpatel.MVPatelPrefrence;
import com.displayfort.mvpatel.R;
import com.github.glomadrian.grav.GravView;

public class SplashActivity extends BaseActivity {

    private GravView gravView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        gravView = findViewById(R.id.grav);
        context = this;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (new MVPatelPrefrence(context).IsLogin()) {
                    startActivityWithAnim(new Intent(getBaseContext(), HomeActivity.class));
                } else {
                    startActivityWithAnim(new Intent(getBaseContext(), LoginActivity.class));
                }
                finish();
            }
        }, 3000);
//        Thread background = new Thread() {
//            public void run() {
//
//                try {
//                    // Thread will sleep for 5 seconds
//                    sleep(2 * 1500);
//                    // After 5 seconds redirect to another intent
//
//
//                    //Remove activity
//                    finish();
//
//                } catch (Exception e) {
//
//                }
//            }
//        };

        // start thread
//        background.start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                super.onClick(v);
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        gravView.stop();
    }
}
