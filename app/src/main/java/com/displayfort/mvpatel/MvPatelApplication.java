package com.displayfort.mvpatel;

import android.app.Application;
import android.content.Context;
import android.content.ServiceConnection;

import com.displayfort.mvpatel.DB.TrackerDbHandler;
import com.splunk.mint.Mint;

/**
 * Created by Husain on 07-03-2016.
 */
public class MvPatelApplication extends Application {
    public static Context context;
    private static MvPatelApplication mInstance;
    private static TrackerDbHandler mDatabaseHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initialize();
        MvPatelApplication.context = getApplicationContext();
        Mint.initAndStartSession(this, "844ac1aa");
    }

    private void initialize() {
        mDatabaseHandler = new TrackerDbHandler(getApplicationContext());

    }

    public static TrackerDbHandler getDatabaseHandler() {
        return mDatabaseHandler;
    }

    public static synchronized MvPatelApplication getInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return MvPatelApplication.context;
    }


    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    @Override
    public Context getBaseContext() {

        return super.getBaseContext();
    }

    @Override
    public void unbindService(ServiceConnection conn) {

        super.unbindService(conn);
    }
}
