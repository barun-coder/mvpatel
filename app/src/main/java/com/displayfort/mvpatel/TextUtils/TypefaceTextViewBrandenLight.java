

package com.displayfort.mvpatel.TextUtils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * @author husains
 */
public class TypefaceTextViewBrandenLight extends TextView {

    public TypefaceTextViewBrandenLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TypefaceTextViewBrandenLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TypefaceTextViewBrandenLight(Context context) {
        super(context);
        init();
    }

    private void init() {
        try {

            if (!isInEditMode()) {
                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Light.ttf");
                setTypeface(tf);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//

}
