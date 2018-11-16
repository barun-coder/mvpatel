package com.displayfort.mvpatel.Screen;

/**
 * Created by pc on 16/10/2018 15:31.
 * DisplayFortSoftware
 */

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.displayfort.mvpatel.Base.BaseActivity;
import com.displayfort.mvpatel.Base.BaseFragment;
import com.displayfort.mvpatel.Base.Constant;
import com.displayfort.mvpatel.DB.DatabaseHandler;
import com.displayfort.mvpatel.DB.TrackerDbHandler;
import com.displayfort.mvpatel.Fragments.HomeFragment;
import com.displayfort.mvpatel.MVPatelPrefrence;
import com.displayfort.mvpatel.Model.CategoryDao;
import com.displayfort.mvpatel.MvPatelApplication;
import com.displayfort.mvpatel.R;
import com.displayfort.mvpatel.Services.SaveJsonDateInDbService;
import com.displayfort.mvpatel.Utils.NewViewAnimator;

import java.util.ArrayList;
import java.util.List;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;


public class HomeActivity extends BaseActivity implements ViewAnimator.ViewAnimatorListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private HomeFragment homeFragment;
    private NewViewAnimator viewAnimator;
    private LinearLayout linearLayout;
    private String currentFragment = Constant.HOME;
    private TextView titleTv;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        context = this;
        homeFragment = HomeFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, homeFragment)
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });
        setActionBar();
        createMenuList();
        viewAnimator = new NewViewAnimator<>(this, list, homeFragment, drawerLayout, this);
        GetDataLoad();
    }

    private void GetDataLoad() {
        if (!new MVPatelPrefrence(context).isDataLoad()) {
            //TODO
            final ProgressDialog loadingdialog = ProgressDialog.show(context,
                    "Please Wait!", "Loading Data..", true);
            new Thread() {
                public void run() {
                    try {
                        sleep(1500);
                        DatabaseHandler.ShowLog("Started");
                        CategoryDao categoryDao = CategoryDao.getCategoryDao(context);
                        TrackerDbHandler dbHandler = MvPatelApplication.getDatabaseHandler();
                        dbHandler.AddCategoryList(categoryDao.categoryDaos);
                        DatabaseHandler.ShowLog("End");
                    } catch (Exception e) {
                        loadingdialog.dismiss();
                        DatabaseHandler.ShowLog("Exception " + e.getMessage());
                        Log.e("threadmessage", e.getMessage());
                    }
                    loadingdialog.dismiss();
                    new MVPatelPrefrence(context).setisDataLoad(true);
                }
            }.start();
        }


    }

    private void createMenuList() {
        list.add(new SlideMenuItem(Constant.CLOSE, R.drawable.ic_close));
        list.add(new SlideMenuItem(Constant.HOME, R.drawable.ic_home));
        list.add(new SlideMenuItem(Constant.JAGUAR, R.drawable.ic_jaquar));
        list.add(new SlideMenuItem(Constant.FAVOURITE, R.drawable.ic_favourite));
        list.add(new SlideMenuItem(Constant.NEW_ARRIVE, R.drawable.ic_new));
        list.add(new SlideMenuItem(Constant.PROJECT, R.drawable.ic_project));
        list.add(new SlideMenuItem(Constant.NEW, R.drawable.ic_news_notifications));
        list.add(new SlideMenuItem(Constant.CUSTOMER, R.drawable.ic_dealer));
        list.add(new SlideMenuItem(Constant.VIDEOS, R.drawable.ic_video));
        list.add(new SlideMenuItem(Constant.SUPPORT, R.drawable.ic_support));
        list.add(new SlideMenuItem(Constant.LOGIN, R.drawable.ic_login));
    }


    private void setActionBar() {
        titleTv = findViewById(R.id.toolbar_title);
        titleTv.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition, BaseFragment homeFragment) {
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();

        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right).replace(R.id.content_frame, homeFragment).commit();
        return homeFragment;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case Constant.CLOSE:
                return screenShotable;
            case Constant.HOME:
                titleTv.setText(slideMenuItem.getName());
                if (!currentFragment.equalsIgnoreCase(slideMenuItem.getName())) {
                    currentFragment = slideMenuItem.getName();
//                    currentFragment = slideMenuItem.getName();
                    return replaceFragment(screenShotable, position, HomeFragment.newInstance());
                }
                break;
//            case Constant.SCREEN:
//                titleTv.setText(slideMenuItem.getName());
//                if (!currentFragment.equalsIgnoreCase(slideMenuItem.getName())) {
//                    currentFragment = slideMenuItem.getName();
//                    return replaceFragment(screenShotable, position, ScreenFragment.newInstance());
//                }
//                break;
//            case Constant.ADS_PROFILE:
//                titleTv.setText(slideMenuItem.getName());
//                if (!currentFragment.equalsIgnoreCase(slideMenuItem.getName())) {
//                    currentFragment = slideMenuItem.getName();
//                    return replaceFragment(screenShotable, position, AdsFragment.newInstance());
//                }
//                break;
//            case Constant.SETTING:
//                titleTv.setText(slideMenuItem.getName());
//                if (!currentFragment.equalsIgnoreCase(slideMenuItem.getName())) {
//                    currentFragment = slideMenuItem.getName();
//                    return replaceFragment(screenShotable, position, SettingFragment.newInstance());
//                }
//                break;
//            case Constant.LOGS:
//                titleTv.setText(slideMenuItem.getName());
//                if (!currentFragment.equalsIgnoreCase(slideMenuItem.getName())) {
//                    currentFragment = slideMenuItem.getName();
//                    return replaceFragment(screenShotable, position, LogsFragment.newInstance());
//                }
//                break;
//            case Constant.REPORT:
//                titleTv.setText(slideMenuItem.getName());
//                if (!currentFragment.equalsIgnoreCase(slideMenuItem.getName())) {
//                    currentFragment = slideMenuItem.getName();
//                    return replaceFragment(screenShotable, position, ReportFragment.newInstance());
//                }
//                break;
//            case Constant.STATUS:
//                titleTv.setText(slideMenuItem.getName());
//                if (!currentFragment.equalsIgnoreCase(slideMenuItem.getName())) {
//                    currentFragment = slideMenuItem.getName();
//                    return replaceFragment(screenShotable, position, StatusFragment.newInstance());
//                }
//                break;
//            case Constant.MEDIA:
//                titleTv.setText(slideMenuItem.getName());
//                if (!currentFragment.equalsIgnoreCase(slideMenuItem.getName())) {
//                    currentFragment = slideMenuItem.getName();
//                    return replaceFragment(screenShotable, position, MediaFragment.newInstance());
//                }
//                break;
            case Constant.LOGOUT:
            case Constant.LOGIN:
                LogoutProcess(context);
                break;
            default:
                titleTv.setText(slideMenuItem.getName());
                if (!currentFragment.equalsIgnoreCase(slideMenuItem.getName())) {
                    currentFragment = slideMenuItem.getName();
                    return replaceFragment(screenShotable, position, HomeFragment.newInstance());
                }
        }
        return screenShotable;
    }

    public void LogoutProcess(final Context context) {
        new MVPatelPrefrence(context).setIsLogin(false);
        new MVPatelPrefrence(context).setisDataLoad(false);
//        Dialogs.showYesNolDialog(context, "Confirmation", "Are you sure you want to Logout", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((Dialog) v.getTag()).dismiss();
//                new DFPrefrence(context).setClearPrefrence();
//                Intent intent = new Intent(context, LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivityWithAnim(intent);
//            }
//        });
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }

    public void addFragment(Fragment fragment, String name) {
        titleTv.setText(name);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right).replace(R.id.content_frame, fragment).addToBackStack(null).commit();
    }

    public void replaceFragment(Fragment fragment, String name) {
        titleTv.setText(name);
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right).replace(R.id.content_frame, fragment).commit();

    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }
}