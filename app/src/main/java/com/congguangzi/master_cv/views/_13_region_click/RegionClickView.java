package com.congguangzi.master_cv.views._13_region_click;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * brief:
 *
 * @author congguangzi (congspark@163.com) 2018/4/25.
 */
public class RegionClickView extends View {

    Paint cor1Paint;

    Paint cor2Paint;

    Region region;

    Path clipPath;

    int height, width;

    float click[] = new float[2];

    Matrix matrix;

    Matrix iMatrix;

    {
        cor1Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cor1Paint.setColor(Color.DKGRAY);
        cor1Paint.setStrokeWidth(4);
        cor1Paint.setStyle(Paint.Style.FILL_AND_STROKE);

        cor2Paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cor2Paint.setColor(Color.RED);
        cor2Paint.setStrokeWidth(4);
        cor2Paint.setStyle(Paint.Style.FILL_AND_STROKE);

        clipPath = new Path();

        region = new Region();

        click[0] = -1; click[1] = -1;

        matrix = new Matrix();
        iMatrix = new Matrix();
    }


    public RegionClickView(Context context) {
        super(context);
    }

    public RegionClickView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RegionClickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
        clipPath.addCircle(w >> 1, h >> 1, 300, Path.Direction.CW);
        Region globalRegion = new Region(0, 0, w, h);
        region.setPath(clipPath, globalRegion);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_DOWN:
                click[0] = (int) event.getX();
                click[1] = (int) event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                click[0] = click[1] = -1;
                invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        matrix.reset();
        matrix.preTranslate(width >> 1, height >> 1);
        drawFirstCoordinate(canvas);
        canvas.setMatrix(matrix);
        drawSecondCoordinate(canvas);

        if (click[0] == -1 && click[1] == -1) {
            return;
        }

        iMatrix.reset();
        matrix.invert(iMatrix);
        iMatrix.mapPoints(click);


        canvas.drawCircle(click[0], click[1], 20, cor1Paint);
    }

    private void drawFirstCoordinate(Canvas canvas) {
        canvas.drawLine(10, 10, 10, height, cor1Paint);
        canvas.drawLine(10, 10, width, 10, cor1Paint);
    }

    private void drawSecondCoordinate(Canvas canvas) {
        canvas.drawLine(0, -(height >> 1), 0, height >> 1, cor2Paint);
        canvas.drawLine(-(width >> 1), 0, width >> 1, 0, cor2Paint);
    }
}
