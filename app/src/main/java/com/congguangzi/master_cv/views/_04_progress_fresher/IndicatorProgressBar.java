package com.congguangzi.master_cv.views._04_progress_fresher;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.congguangzi.master_cv.R;
import com.congguangzi.master_cv.ViewUtils;


/**
 * @author congguangzi (congspark@163.com) 2017/11/8.
 */

public class IndicatorProgressBar extends ProgressBar {

    private TextPaint textPaint;
    private Drawable indicatorDrawable;
    private int offset = 5;


    public IndicatorProgressBar(Context context) {
        this(context, null);
    }

    public IndicatorProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IndicatorProgressBar, defStyleAttr, 0);
        offset = array.getInt(R.styleable.IndicatorProgressBar_offset, 0);
        indicatorDrawable = array.getDrawable(R.styleable.IndicatorProgressBar_progressIndicator);
        array.recycle();
    }

    {
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.density = getResources().getDisplayMetrics().density;
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(ViewUtils.dpToPixel(10));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        Drawable drawable = getProgressDrawable();

        super.onDraw(canvas);
    }






}
