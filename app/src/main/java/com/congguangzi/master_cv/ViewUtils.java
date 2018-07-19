package com.congguangzi.master_cv;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * @author congguangzi (congspark@163.com) 2017/11/10.
 */

public class ViewUtils {

    public static float dpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * metrics.density;
    }


    public static int dpToPixel(int dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return (int) (dp * metrics.density);
    }


    public static int cameraZLocation(int dp) {
        return (int) (Resources.getSystem().getDisplayMetrics().density * dp);
    }

}
