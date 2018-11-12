package com.displayfort.mvpatel.TextUtils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;

public class DisplayUtil {
	/**
	 * Find device real display size in pixel.
	 * 
	 * @param activity
	 * @param dimens
	 *            int array of more than or equal to two size<br/>
	 *            0-width<br/>
	 *            1-height
	 */
	public static void probeScreenSize(Activity activity, int[] dimens) {
		if (null != activity) {
			Display display = activity.getWindowManager().getDefaultDisplay();
			if (android.os.Build.VERSION.SDK_INT >= 13) {
				Point size = new Point();
				display.getSize(size);
				dimens[0] = size.x;
				dimens[1] = size.y;
			} else {
				dimens[0] = display.getWidth();
				dimens[1] = display.getHeight();
			}
		}
	}

	public static float convertDpToPixel(float dp, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
		return px;
	}
}
