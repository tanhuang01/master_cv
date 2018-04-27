package com.congguangzi.master_cv.views._15_multi_motion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.congguangzi.master_cv.R;

/**
 * brief: multi motion control
 *
 * @author congguangzi (congspark@163.com) 2018/4/27.
 */
public class MultiMotionView extends View {

    private Bitmap bitmap;
    private Matrix matrix;
    private RectF bitmapRect;

    private boolean dragged;
    private PointF lastPoint;

    {
        matrix = new Matrix();
        bitmapRect = new RectF();

        lastPoint = new PointF();
    }

    public MultiMotionView(Context context) {
        super(context);
    }

    public MultiMotionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outWidth = 400;
        options.outHeight = 400;

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable._01_maps, options);
        bitmapRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
    }

    public MultiMotionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:

                if (event.getPointerId(event.getActionIndex()) == 0 &&
                        bitmapRect.contains(event.getX(), event.getY())) {
                    lastPoint.set(event.getX(), event.getY());
                    dragged = true;
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:

                if (event.getPointerId(event.getActionIndex()) == 0) {
                    dragged = false;
                }

                break;
            case MotionEvent.ACTION_MOVE:

                if (dragged) {
                    int index = event.findPointerIndex(0);

                    float dx = event.getX(index) - lastPoint.x;
                    float dy = event.getY(index) - lastPoint.y;

                    lastPoint.set(event.getX(index), event.getY(index));
                    matrix.preTranslate(dx, dy);

                    // 重新定义
                    bitmapRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
                    matrix.mapRect(bitmapRect);

                    invalidate();
                }
                break;
        }

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, matrix, null);
    }
}
