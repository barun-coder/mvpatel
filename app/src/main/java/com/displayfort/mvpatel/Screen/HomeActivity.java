package com.displayfort.mvpatel.Screen;

/**
 * Created by pc on 16/10/2018 15:31.
 * DisplayFortSoftware
 */

import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.displayfort.mvpatel.Base.BaseActivity;
import com.displayfort.mvpatel.Base.BaseFragment;
import com.displayfort.mvpatel.Base.Constant;
import com.displayfort.mvpatel.DB.DatabaseHandler;
import com.displayfort.mvpatel.DB.TrackerDbHandler;
import com.displayfort.mvpatel.Fragments.ConnectNfcFragment;
import com.displayfort.mvpatel.Fragments.HomeFragment;
import com.displayfort.mvpatel.Fragments.MyProjectListingFragment;
import com.displayfort.mvpatel.Fragments.ProductDetailFragment;
import com.displayfort.mvpatel.Fragments.SearchFragment;
import com.displayfort.mvpatel.MVPatelPrefrence;
import com.displayfort.mvpatel.Model.CategoryDao;
import com.displayfort.mvpatel.Model.Master;
import com.displayfort.mvpatel.Model.Product;
import com.displayfort.mvpatel.MvPatelApplication;
import com.displayfort.mvpatel.R;
import com.displayfort.mvpatel.Utils.Dialogs;
import com.displayfort.mvpatel.Utils.NewViewAnimator;
import com.displayfort.mvpatel.Utils.Utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
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
//    private TextView titleTv;
    private Context context;
    private ImageView icBackIv;
    private boolean mToolBarNavigationListenerIsRegistered = false;
    private Fragment mCurrentFragment;
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;
    private long lastTagID = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        context = this;
        initGestures();
        initNFC();
        homeFragment = HomeFragment.newInstance();
        replaceFragment(homeFragment, Constant.HOME);
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.main_fragment, homeFragment)
//                .commit();
        icBackIv = findViewById(R.id.back_icon);

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
        setNFC();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (nfcAdapter != null) {
            if (!nfcAdapter.isEnabled())
                showWirelessSettings();

            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    private void showWirelessSettings() {
        Toast.makeText(context, "You need to enable NFC", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }

    private void initNFC() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(context);

        if (nfcAdapter == null) {
            Toast.makeText(context, "No NFC", Toast.LENGTH_SHORT).show();
            return;
        }

        pendingIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, context.getClass())
                        .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    public void goToHome(View view) {
        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        Fragment fragment = fragmentList.get(fragmentList.size() - 1);
        if (!(fragment instanceof HomeFragment)) {
            Intent intent = new Intent(context, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityWithAnim(intent);
        }

    }

    private void initGestures() {

        gestureDetector = new GestureDetector(new SwipeGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
    }

    private class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_MIN_DISTANCE = 50;
        private static final int SWIPE_MAX_OFF_PATH = 200;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            try {
                Toast t = Toast.makeText(context, "Gesture detected", Toast.LENGTH_SHORT);
                t.show();
                float diffAbs = Math.abs(e1.getY() - e2.getY());
                float diff = e1.getX() - e2.getX();

                if (diffAbs > SWIPE_MAX_OFF_PATH)
                    return false;

                // Left swipe
                if (diff > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    onLeftSwipe();
                }
                // Right swipe
                else if (-diff > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    onRightSwipe();
                }
            } catch (Exception e) {
                Log.e("Home", "Error on gestures");
            }
            return false;
        }

    }

    private void onLeftSwipe() {
        Toast t = Toast.makeText(context, "Left swipe", Toast.LENGTH_LONG);
        t.show();
        Intent go = new Intent("test.apps.FLORA");
        startActivity(go);
    }

    private void onRightSwipe() {
        Toast t = Toast.makeText(context, "Right swipe", Toast.LENGTH_LONG);
        t.show();
//        go = new Intent("test.apps.FAUNA");
//        startActivity(go);
    }

    private void GetDataLoad() {
        if (!new MVPatelPrefrence(context).isDataLoad()) {
            //TODO
            final ProgressDialog loadingdialog = ProgressDialog.show(context,
                    "Please Wait!", "Parsing Data..", true);
            new Thread() {
                public void run() {
                    try {
                        sleep(1500);
                        DatabaseHandler.ShowLog("Started");
                        CategoryDao categoryDao = CategoryDao.getCategoryDao(context);
                        Master.getSIze(context);
                        TrackerDbHandler dbHandler = MvPatelApplication.getDatabaseHandler();
                        /**/
                        try {
                            dbHandler.deleteAllTables();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (Master.categoryDaosMaster != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadingdialog.setCanceledOnTouchOutside(true);
                                    loadingdialog.setMessage("Downloading Category..");
                                }
                            });
                            dbHandler.MasterAddAllCategory(Master.categoryDaosMaster);
                        }
                        /**/
                        if (Master.subCategoriesMaster != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadingdialog.setCanceledOnTouchOutside(true);
                                    loadingdialog.setMessage("Downloading Subcategory..");
                                }
                            });
                            dbHandler.MasterAddAllSubcategory(Master.subCategoriesMaster);
                        }
                        /**/
                        if (Master.productsMaster != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadingdialog.setCanceledOnTouchOutside(true);
                                    loadingdialog.setMessage("Downloading Products..");
                                }
                            });
                            dbHandler.MasterAddAllProductList(Master.productsMaster);
                        }
                        /**/
                        if (Master.attachmentListDaosMaster != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadingdialog.setCanceledOnTouchOutside(true);
                                    loadingdialog.setMessage("Downloading Attachments..");
                                }
                            });
                            dbHandler.MasterAddAllAttachment(Master.attachmentListDaosMaster);
                        }
                        /**/
                        if (Master.productPriceArrayListMaster != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadingdialog.setCanceledOnTouchOutside(true);
                                    loadingdialog.setMessage("Downloading ProductsDetail..");
                                }
                            });
                            dbHandler.MasterAddAllProductPriceList(Master.productPriceArrayListMaster);
                        }
                        /**/
                        if (Master.colorArrayListMaster != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    loadingdialog.setCanceledOnTouchOutside(true);
                                    loadingdialog.setMessage("Downloading Others..");
                                }
                            });
                            dbHandler.MasterAddAllColorList(Master.colorArrayListMaster);
                        }


//                        dbHandler.AddCategoryList(categoryDao.categoryDaos);
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
        list.add(new SlideMenuItem(Constant.NFC, R.drawable.ic_support));
        list.add(new SlideMenuItem(Constant.LOGIN, R.drawable.ic_login));
    }


    private void setActionBar() {
//        titleTv = findViewById(R.id.toolbar_title);
//        titleTv.setVisibility(View.GONE);
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

    /**/
    public void enableViews(boolean enable) {

        // To keep states of ActionBar and ActionBarDrawerToggle synchronized,
        // when you enable on one, you disable on the other.
        // And as you may notice, the order for this operation is disable first, then enable - VERY VERY IMPORTANT.
        int lockMode = drawerLayout.getDrawerLockMode(Gravity.LEFT);

        if (!enable) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            // Remove hamburger
            drawerToggle.setDrawerIndicatorEnabled(false);
            // Show back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // when DrawerToggle is disabled i.e. setDrawerIndicatorEnabled(false), navigation icon
            // clicks are disabled i.e. the UP button will not work.
            // We need to add a listener, as in below, so DrawerToggle will forward
            // click events to this listener.
            if (!mToolBarNavigationListenerIsRegistered) {
                drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Doesn't have to be onBackPressed
                        onBackPressed();
                    }
                });

                mToolBarNavigationListenerIsRegistered = true;
            }
        }
        if (lockMode == DrawerLayout.LOCK_MODE_UNLOCKED) {
            return;
        } else {
            //You must regain the power of swipe for the drawer.
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            // Remove back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            // Show hamburger
            drawerToggle.setDrawerIndicatorEnabled(true);
            // Remove the/any drawer toggle listener
            drawerToggle.setToolbarNavigationClickListener(null);
            mToolBarNavigationListenerIsRegistered = false;
            // Remove back button


        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_search) {
            loadSearchFragment();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void loadSearchFragment() {
        addFragment(SearchFragment.newInstance(), "Search", false);
    }

    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition, BaseFragment homeFragment, String tagName) {
        enableViews(true);
        View view = findViewById(R.id.main_fragment);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
        mCurrentFragment = homeFragment;
        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in_anim, R.anim.fade_out_anim)
                .replace(R.id.main_fragment, homeFragment, tagName).commit();
        return homeFragment;
    }

    void EmptyStack() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        android.app.Fragment myFragment;
        switch (slideMenuItem.getName()) {
            case Constant.CLOSE:
                return screenShotable;
            case Constant.HOME:
                myFragment = getFragmentManager().findFragmentByTag(slideMenuItem.getName());
                if (myFragment == null || !(myFragment.isVisible())) {
                    EmptyStack();
                    currentFragment = slideMenuItem.getName();
                    return replaceFragment(screenShotable, position, HomeFragment.newInstance(), slideMenuItem.getName());
                }
                break;
            case Constant.PROJECT:
                myFragment = getFragmentManager().findFragmentByTag(slideMenuItem.getName());
                if (myFragment == null || !myFragment.isVisible()) {
                    EmptyStack();
                    currentFragment = slideMenuItem.getName();
                    return replaceFragment(screenShotable, position, MyProjectListingFragment.newInstance(), slideMenuItem.getName());
                }
                break;
            case Constant.NFC: {
                myFragment = getFragmentManager().findFragmentByTag(slideMenuItem.getName());
                if (myFragment == null || !myFragment.isVisible()) {
                    EmptyStack();
                    currentFragment = slideMenuItem.getName();
                    return replaceFragment(screenShotable, position, ConnectNfcFragment.newInstance(), slideMenuItem.getName());
                }
            }

            break;
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
                Utility.ShowToast(slideMenuItem.getName(), context);
        }
        return screenShotable;
    }

    /**/
    public static void copyFile(FileInputStream fromFile, FileOutputStream toFile) throws IOException {
        FileChannel fromChannel = null;
        FileChannel toChannel = null;
        try {
            fromChannel = fromFile.getChannel();
            toChannel = toFile.getChannel();
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } finally {
            try {
                if (fromChannel != null) {
                    fromChannel.close();
                }
            } finally {
                if (toChannel != null) {
                    toChannel.close();
                }
            }
        }
    }

    public void LogoutProcess(final Context context) {
        Dialogs.showYesNolDialog(context, "Confirmation", "Are you sure you want to Logout", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Dialog) v.getTag()).dismiss();
                new MVPatelPrefrence(context).setClearPrefrence();
                Intent intent = new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityWithAnim(intent);
            }
        });
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
        mCurrentFragment = fragment;
//        enableViews(true);
//        titleTv.setText(name);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in_anim, R.anim.fade_out_anim)
                .add(R.id.main_fragment, fragment)
//                .addToBackStack(null)
                .commit();
    }

    public void addFragment(Fragment fragment, String name, boolean setEnable) {
        mCurrentFragment = fragment;
        enableViews(setEnable);
//        titleTv.setText(name);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in_anim, R.anim.fade_out_anim)
                .add(R.id.main_fragment, fragment)
                .addToBackStack(null)
                .commit();


    }

    public void replaceFragment(Fragment fragment, String name) {
        mCurrentFragment = fragment;
//        titleTv.setText(name);
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.fade_in_anim, R.anim.fade_out_anim)
                .replace(R.id.main_fragment, fragment)
                .commit();

    }

    boolean doubleBackToExitPressedOnce = false;

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
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
            /**/
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

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        if (mCurrentFragment != null && mCurrentFragment instanceof SearchFragment) {
            ((SearchFragment) mCurrentFragment).onNewIntent(intent);
        } else if (mCurrentFragment != null && mCurrentFragment instanceof ConnectNfcFragment) {
            ((ConnectNfcFragment) mCurrentFragment).onNewIntent(intent);
        } else {

            String action = intent.getAction();
//android.nfc.action.NDEF_DISCOVERED
            if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                    || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                    || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)
                    || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
                Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                NdefMessage[] msgs;

                if (rawMsgs != null) {
                    msgs = new NdefMessage[rawMsgs.length];

                    for (int i = 0; i < rawMsgs.length; i++) {
                        msgs[i] = (NdefMessage) rawMsgs[i];
                    }
                } else {
                    byte[] empty = new byte[0];
                    byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
                    Tag tag = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                    long tagId = dumpTagIDData(tag);
                    if (lastTagID != tagId) {
//                        Utility.ShowToast("NEw NFC Tag " + tagId, context);
//                        if ((tagId + "").equalsIgnoreCase("1145151598180225")) {
//                            onQueryTextSubmit("KUP-CHR-35011BPM");
//                        } else if ((tagId + "").equalsIgnoreCase("1145147303212929")) {
//                            onQueryTextSubmit("ALI-CHR-85011B");
//                        } else {
//                        }
                        onQueryTextSubmit(tagId);

                        lastTagID = tagId;
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            lastTagID = 0;
                        }
                    }, 2000);
//                Product product = new Product(tagId + "");
//                displayNFCTag(product);


                }

            }
        }

    }

    private int limit = 10, offset = 0;

    private void onQueryTextSubmit(long s) {
        if (s != 0) {
            TrackerDbHandler dbHandler = MvPatelApplication.getDatabaseHandler();
            ArrayList<Product> productList = dbHandler.getNFCSearchProductList(s);
            if (productList.size() > 0) {
                Product product = productList.get(0);
                addFragment(ProductDetailFragment.newInstance(product.id), (product.name));
            }
        }
    }


    private long dumpTagIDData(Tag tag) {
        StringBuilder sb = new StringBuilder();
        byte[] id = tag.getId();
        long tagId = 0;
        tagId = toReversedDec(id);
        return tagId;
    }


    private long toReversedDec(byte[] bytes) {
        long result = 0;
        long factor = 1;
        for (int i = bytes.length - 1; i >= 0; --i) {
            long value = bytes[i] & 0xffl;
            result += value * factor;
            factor *= 256l;
        }
        return result;
    }

    private void setNFC() {
        Intent nfcIntent = new Intent(this, getClass());
        nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent nfcPendingIntent = PendingIntent.getActivity(this, 0, nfcIntent, 0);

        IntentFilter tagIntentFilter =
                new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            tagIntentFilter.addDataType("text/plain");
            IntentFilter[] intentFiltersArray = new IntentFilter[]{tagIntentFilter};
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}