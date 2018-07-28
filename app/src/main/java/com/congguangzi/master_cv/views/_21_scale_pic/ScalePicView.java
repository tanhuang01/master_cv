package com.congguangzi.master_cv.views._21_scale_pic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.congguangzi.master_cv.ViewUtils;

/**
 * brief:
 *
 * @author congguangzi (congspark@163.com) 2018/7/28.
 */
public class ScalePicView extends View {

    Paint paint;

    public ScalePicView(Context context) {
        super(context);
    }

    public ScalePicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScalePicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(ViewUtils.dpToPixel(15));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawText("a pic to be draw", 100, 100, paint);
    }
}
