

package com.displayfort.mvpatel.TextUtils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;


/**
 * @author husains
 */
public class TypefaceCheckboxBrandenReg extends CheckBox {

    public TypefaceCheckboxBrandenReg(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TypefaceCheckboxBrandenReg(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TypefaceCheckboxBrandenReg(Context context) {
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
