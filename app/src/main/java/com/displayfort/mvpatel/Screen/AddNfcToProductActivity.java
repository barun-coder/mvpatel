package com.displayfort.mvpatel.Screen;

/**
 * Created by pc on 16/10/2018 15:31.
 * DisplayFortSoftware
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.displayfort.mvpatel.Base.BaseActivity;
import com.displayfort.mvpatel.Base.BaseFragment;
import com.displayfort.mvpatel.Base.Constant;
import com.displayfort.mvpatel.Fragments.AllCategoryFragment;
import com.displayfort.mvpatel.Fragments.HomeFragment;
import com.displayfort.mvpatel.Fragments.SearchFragment;
import com.displayfort.mvpatel.R;

import java.util.ArrayList;
import java.util.List;

import yalantis.com.sidemenu.model.SlideMenuItem;


public class AddNfcToProductActivity extends BaseActivity {
    private List<SlideMenuItem> list = new ArrayList<>();
    private BaseFragment baseFragment;
    private String currentFragment = Constant.HOME;
    private Context context;
    private ImageView icBackIv;
    private Fragment mCurrentFragment;
    private long lastTagID = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_nfc_to_product);
        context = this;
        baseFragment = AllCategoryFragment.newInstance("1,2,3,4,5");
        replaceFragment(baseFragment, "AllCategory");
        icBackIv = findViewById(R.id.back_icon);
        setActionBar();
    }


    public void goToHome(View view) {
        startActivityWithAnim(new Intent(getBaseContext(), HomeActivity.class));

    }


    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    public void addFragment(Fragment fragment, String name) {
        mCurrentFragment = fragment;
//        enableViews(true);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in_anim, R.anim.fade_out_anim)
                .add(R.id.main_fragment, fragment)
//                .addToBackStack(null)
                .commit();
    }

    public void addFragment(Fragment fragment, String name, boolean setEnable) {
        mCurrentFragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in_anim, R.anim.fade_out_anim)
                .add(R.id.main_fragment, fragment)
                .addToBackStack(null)
                .commit();


    }

    public void replaceFragment(Fragment fragment, String name) {
        mCurrentFragment = fragment;
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in_anim, R.anim.fade_out_anim)
                .replace(R.id.main_fragment, fragment)
                .commit();

    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        for (int i = (fragmentList.size() - 1); i >= 0; i--) {
            Fragment fragment = fragmentList.get(i);
            if (fragment instanceof SearchFragment) {
                ((SearchFragment) fragment).onBackPressed();
            }
        }

        int count = fragmentList.size();
        //getFragmentManager().getBackStackEntryCount();

        if (count <= 1) {
            /*  super.onBackPressed();*/
            super.onBackPressed();
            //additional code
        } else {
            Fragment fragemnt = (fragmentList.get((fragmentList.size() - 1)));
            if (fragemnt != null) {
                getSupportFragmentManager().beginTransaction().remove(fragemnt).commit();
                mCurrentFragment = (fragmentList.get((fragmentList.size() - 2)));
            }
            getFragmentManager().popBackStack();
        }

    }


}