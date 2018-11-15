package com.displayfort.mvpatel.Screen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.displayfort.mvpatel.Base.BaseActivity;
import com.displayfort.mvpatel.MVPatelPrefrence;
import com.displayfort.mvpatel.R;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(2 * 1000);
                    // After 5 seconds redirect to another intent
                    if (new MVPatelPrefrence(context).IsLogin()) {
                        startActivityWithAnim(new Intent(getBaseContext(), HomeActivity.class));
                    } else {
                        startActivityWithAnim(new Intent(getBaseContext(), LoginActivity.class));
                    }

                    //Remove activity
                    finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, SplashActivity.class);
        return intent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                super.onClick(v);
                break;
        }
    }
}
