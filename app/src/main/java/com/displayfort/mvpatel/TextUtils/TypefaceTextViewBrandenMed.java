package com.displayfort.mvpatel.TextUtils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by applanche on 23/4/16.
 */
public class TypefaceTextViewBrandenMed extends TextView {

    public TypefaceTextViewBrandenMed(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TypefaceTextViewBrandenMed(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TypefaceTextViewBrandenMed(Context context) {
        super(context);
        init();
    }

    private void init() {
        try {

            if (!isInEditMode()) {
                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Medium.ttf");
                setTypeface(tf);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//

}