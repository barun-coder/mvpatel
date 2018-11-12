

package com.displayfort.mvpatel.TextUtils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;


/**
 * @author husains
 */
public class TypefaceButtonBrandenReg extends Button {

    public TypefaceButtonBrandenReg(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TypefaceButtonBrandenReg(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TypefaceButtonBrandenReg(Context context) {
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
