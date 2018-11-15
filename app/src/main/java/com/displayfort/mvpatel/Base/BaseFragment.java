package com.displayfort.mvpatel.Base;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

public class BaseFragment extends Fragment implements ScreenShotable, View.OnClickListener {


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public void startActivityWithouthAnim(FragmentActivity fragmentActivity,
                                          Intent intent) {
        startActivity(intent);

    }

    /**
     * @param fragmentActivity
     * @param intent           Start Activity with Custom animation
     */
    public void startActivityWithAnim(FragmentActivity fragmentActivity,
                                      Intent intent) {
        startActivityWithAnim(fragmentActivity, intent, BaseAnimation.EFFECT_TYPE.TAB_DEAFULT);
    }

    public void startActivityWithAnim(FragmentActivity fragmentActivity,
                                      Intent intent, BaseAnimation.EFFECT_TYPE type) {
        startActivity(intent);
        setAnimationTransition(fragmentActivity, type);
    }

    /**
     * @param fragmentActivity
     * @param intent
     * @param requestCode      Start Intent With result with Result Code
     */
    public void startActivityWithResultAnim(FragmentActivity fragmentActivity,
                                            Intent intent, int requestCode) {
        startActivityWithResultAnim(fragmentActivity, intent, requestCode,
                BaseAnimation.EFFECT_TYPE.TAB_DEAFULT);
    }

    public void startActivityWithResultAnim(FragmentActivity fragmentActivity,
                                            Intent intent, int requestCode, BaseAnimation.EFFECT_TYPE type) {
        startActivityForResult(intent, requestCode);
        setAnimationTransition(fragmentActivity, type);
    }

    /**
     * @param fragmentActivity Finish Activity with Custom Animation
     */
    public void finishActivityWithAnim(FragmentActivity fragmentActivity) {
        finishActivityWithAnim(fragmentActivity, BaseAnimation.EFFECT_TYPE.TAB_DEAFULT);
    }

    public void finishActivityWithAnim(FragmentActivity fragmentActivity,
                                       BaseAnimation.EFFECT_TYPE type) {
        fragmentActivity.finish();
        setAnimationTransition(fragmentActivity, type);

    }

    private void setAnimationTransition(FragmentActivity fragmentActivity,
                                        BaseAnimation.EFFECT_TYPE type) {
        BaseAnimation.setAnimationTransition(fragmentActivity, type);
    }


    private void setViewColor(View viewTheme, int themeLightBlue) {
        viewTheme.setBackgroundColor(getResources().getColor(themeLightBlue));
    }


    private void setTextColor(TextView viewTheme, int themeLightBlue) {
        viewTheme.setTextColor(getResources().getColor(themeLightBlue));
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    public DisplayMetrics getScreenSize(Activity context) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics;
    }
}