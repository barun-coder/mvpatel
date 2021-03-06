package com.displayfort.mvpatel;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Husain on 15-03-2016.
 */
public class MVPatelPrefrence {


    private static final String SHARED_PREFERENCE_NAME = "MVPatelSharedPreference";
    private static final String SHARED_PREFERENCE_NAME_TUTORIAL = "MVPatelSharedPreferenceTUORIAL";

    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferencesTutorial;

    public void setClearPrefrence() {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.clear();
        prefsEditor.commit();
    }

    public MVPatelPrefrence(Context context) {
//        context = RestaurantApplication.getInstance();
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        this.sharedPreferencesTutorial = context.getSharedPreferences(SHARED_PREFERENCE_NAME_TUTORIAL,
                Context.MODE_PRIVATE);
    }


    public void setLoginSessionKey(String sessionKey) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("session_Key", null == sessionKey ? "" : sessionKey);
        prefsEditor.commit();
    }

    public String getLoginSessionKey() {
        return sharedPreferences.getString("session_Key", "12345");
    }

    public void setID(String ID) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString("ID", null == ID ? "" : ID);
        prefsEditor.commit();
    }

    public String getID() {
        return sharedPreferences.getString("ID", "");
    }

    public void setIsLogin(boolean IsLogin) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean("IsLogin", IsLogin);
        prefsEditor.commit();
    }

    public boolean IsLogin() {
        return sharedPreferences.getBoolean("IsLogin", false);
    }


    public boolean isDataLoad() {
        return sharedPreferences.getBoolean("isDataLoad", false);
    }

    public void setisDataLoad(boolean isDataLoad) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean("isDataLoad", isDataLoad);
        prefsEditor.commit();
    }


    public void setValue(String Key, String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(Key, null == value ? "" : value);
        prefsEditor.commit();
    }

    public String getValue(String Key) {
        return sharedPreferences.getString(Key, "");
    }

    public void setIntValue(String Key, int value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putInt(Key, value);
        prefsEditor.commit();
    }

    public int getIntValue(String Key) {
        return sharedPreferences.getInt(Key, 0);
    }

}