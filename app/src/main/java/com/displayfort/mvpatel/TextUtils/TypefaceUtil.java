package com.displayfort.mvpatel.TextUtils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

public class TypefaceUtil {

    /**
     * Using reflection to override default typeface NOTICE: DO NOT FORGET TO
     * SET TYPEFACE FOR APP THEME AS DEFAULT TYPEFACE WHICH WILL BE OVERRIDDEN
     *
     * @param context
     * to work with assets
     * @param defaultFontNameToOverride
     * for example "monospace"
     * @param customFontFileNameInAssets
     * file name of the font from assets
     */

    public static String LIGHT = "fonts/BRANDON_LIGHT.OTF";
    public static String BOLD = "fonts/BRANDON_BLD.OTF";
    public static String REGULAR = "fonts/BRANDON_REG.OTF";
    public static String MEDIUM = "fonts/BRANDON_MED.OTF";
    public static String AVENIRNEXT_REGULAR = "fonts/AvenirNextLTPro-Regular.otf";

    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);

            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {

        }
    }

    public static void setDefaultFont(Context context, String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(), fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    protected static void replaceFont(String staticTypefaceFieldName, final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class.getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void SetFontToView(Context mContext, String regular, View... view) {
        if (null != view) {

            try {
                int i = 0;
                while (i < view.length) {
                    SetFontToView(mContext, regular, view[i]);
                    i += 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void SetFontToView(Context context, String fontStyle, View view) {
        if (view != null) {
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                Typeface font = Typeface.createFromAsset(context.getAssets(), fontStyle);
                textView.setTypeface(font);
            }
            if (view instanceof EditText) {
                EditText textView = (EditText) view;
                Typeface font = Typeface.createFromAsset(context.getAssets(), fontStyle);
                textView.setTypeface(font);
            }
            if (view instanceof Button) {
                Button textView = (Button) view;
                Typeface font = Typeface.createFromAsset(context.getAssets(), fontStyle);
                textView.setTypeface(font);
            }
            if (view instanceof AutoCompleteTextView) {
                AutoCompleteTextView textView = (AutoCompleteTextView) view;
                Typeface font = Typeface.createFromAsset(context.getAssets(), fontStyle);
                textView.setTypeface(font);
            }
        }


    }
}
