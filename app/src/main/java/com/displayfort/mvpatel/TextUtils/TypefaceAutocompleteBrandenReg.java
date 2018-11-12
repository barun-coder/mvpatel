

package com.displayfort.mvpatel.TextUtils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;


/**
 * @author husains
 */
public class TypefaceAutocompleteBrandenReg extends AutoCompleteTextView {

    public TypefaceAutocompleteBrandenReg(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TypefaceAutocompleteBrandenReg(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TypefaceAutocompleteBrandenReg(Context context) {
        super(context);
        init();
    }

    private void init() {
        try {

            if (!isInEditMode()) {
                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Regular.ttf");
                setTypeface(tf);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//

}
