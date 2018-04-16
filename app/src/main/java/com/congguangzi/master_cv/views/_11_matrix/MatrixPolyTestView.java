package com.congguangzi.master_cv.views._11_matrix;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.congguangzi.master_cv.R;

/**
 * brief:
 *
 * @author congguangzi (congspark@163.com) 2018/4/13.
 */
public class MatrixPolyTestView extends View {

    private int testPoint = 0;
    private int triggerRadius = 180;

    private BitmapFactory.Options options;
    private Bitmap bitmap;
    private Paint pointPaint;

    private Matrix matrix;


    private float[] src = new float[9];
    private float[] dst = new float[9];

    {
        matrix = new Matrix();

        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setColor(Color.GRAY);
        pointPaint.setStrokeWidth(50);
        pointPaint.setStrokeCap(Paint.Cap.ROUND);

        options = new BitmapFactory.Options();
        options.inSampleSize = 2;
    }

    public MatrixPolyTestView(Context context) {
        super(context);
        init();
    }

    public MatrixPolyTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MatrixPolyTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable._11_poly_test, options);

        //
        src[0] = 0;
        src[1] = 1;     // top | left
        src[2] = bitmap.getWidth();
        src[3] = 0;     // top | right
        src[4] = bitmap.getWidth();
        src[5] = bitmap.getHeight();    // bottom | right
        src[6] = 0;
        src[7] = bitmap.getHeight();    // bottom | left

        dst = src.clone();

        matrix.setPolyToPoly(src, 0, dst, 0, 4);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(100, 100);

        canvas.drawBitmap(bitmap, matrix, null);

        matrix.mapPoints(dst, src);

        for (int i = 0; i < testPoint * 2; i += 2) {
            canvas.drawPoint(dst[i], dst[i + 1], pointPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float tempX = event.getX();
                float tempY = event.getY();

                for (int i = 0; i < testPoint * 2; i += 2) {
                    if (Math.abs(tempX - dst[i]) <= triggerRadius && Math.abs(tempY - dst[i + 1]) <= triggerRadius) {
                        dst[i] = tempX - 100;
                        dst[i + 1] = tempY - 100;
                        break;
                    }
                }

                resetMatrixPoly();
                invalidate();
                break;
        }
        return true;
    }

    private void resetMatrixPoly() {
        matrix.reset();
        matrix.setPolyToPoly(src, 0, dst, 0, testPoint);
    }


    public void setTestPoint(int testPoint) {
        this.testPoint = testPoint > 4 || testPoint < 0 ? 4 : testPoint;
        dst = src.clone();
        resetMatrixPoly();
        invalidate();
    }
}
