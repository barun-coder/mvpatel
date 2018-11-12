package com.displayfort.mvpatel.Base;

import android.app.Activity;
import android.util.Log;

import com.displayfort.mvpatel.R;


public class BaseAnimation {

    public enum EFFECT_TYPE {
        TAB_DEAFULT, TAB_SLIDE, TAB_FADE, TAB_SLIDE_RIGHT, TAB_SLIDE_RIGHT_FINISH, TAB_SLIDE_FINISH, TAB_SLIDE_DOWN_TO_UP, TAB_SLIDE_DOWN_TO_UP_FINISH;
    }

    public static void setAnimationTransition(Activity context, EFFECT_TYPE type) {
        switch (type) {
            case TAB_FADE:
                context.overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                break;
            case TAB_SLIDE_RIGHT:
            case TAB_SLIDE:
                Log.v("TAB_SLIDE", "TAB_SLIDE");
                context.overridePendingTransition(R.anim.slide_in_right,
                        android.R.anim.fade_in);
                break;
            case TAB_SLIDE_FINISH:
                context.overridePendingTransition(android.R.anim.fade_in,
                        R.anim.slide_out_right);
                break;
            case TAB_SLIDE_DOWN_TO_UP:
                context.overridePendingTransition(R.anim.slide_up_dialog,
                        0);
                break;
            case TAB_SLIDE_DOWN_TO_UP_FINISH:
                context.overridePendingTransition(0,
                        R.anim.slide_down_dialog);
                break;
            default:
                context.overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                break;
        }

    }

}
