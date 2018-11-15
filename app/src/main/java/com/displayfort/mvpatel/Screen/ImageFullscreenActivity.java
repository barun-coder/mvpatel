package com.displayfort.mvpatel.Screen;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.displayfort.mvpatel.Base.BaseActivity;
import com.displayfort.mvpatel.R;
import com.github.chrisbanes.photoview.PhotoView;

public class ImageFullscreenActivity extends BaseActivity {
    private PhotoView imageView;
    private Toolbar toolbar;
    private LinearLayout transitions_container;


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_image_fullscreen);
        getWindow().setSoftInputMode(32);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator((int) R.drawable.ic_back);
        this.imageView = (PhotoView) findViewById(R.id.imageView);
        this.transitions_container = (LinearLayout) findViewById(R.id.transitions_container);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }


}
