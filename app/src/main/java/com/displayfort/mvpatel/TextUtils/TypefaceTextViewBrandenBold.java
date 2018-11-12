package com.displayfort.mvpatel.TextUtils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by applanche on 19/4/16.
 */
public class TypefaceTextViewBrandenBold extends TextView {

    public TypefaceTextViewBrandenBold(Context context, AttributeSet attrs, int defStyle) {
        super( context, attrs, defStyle );
        init();
    }

    public TypefaceTextViewBrandenBold(Context context, AttributeSet attrs) {
        super( context, attrs );
        init();
    }

    public TypefaceTextViewBrandenBold(Context context) {
        super( context );
        init();
    }

    private void init() {
        try {

            if (!isInEditMode()) {
                Typeface tf = Typeface.createFromAsset( getContext().getAssets(), "fonts/Roboto-Bold.ttf" );
                setTypeface( tf );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//

}