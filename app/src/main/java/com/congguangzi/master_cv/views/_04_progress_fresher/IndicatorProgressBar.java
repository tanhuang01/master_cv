package com.congguangzi.master_cv.views._04_progress_fresher;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * @author congguangzi (congspark@163.com) 2017/11/8.
 */

public class IndicatorProgressBar extends ProgressBar {


    public IndicatorProgressBar(Context context) {
        super(context);
    }

    public IndicatorProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IndicatorProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
