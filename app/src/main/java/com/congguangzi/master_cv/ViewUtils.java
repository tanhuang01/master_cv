package com.congguangzi.master_cv;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

/**
 * brief:
 *
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


    /**
     * get the bitmap with a specific width, maybe scaled
     *
     * @param resources
     * @param id
     * @param width
     * @return
     */
    public static Bitmap getAvatar(Resources resources, final int id, int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, id, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(resources, id, options);
    }

}
