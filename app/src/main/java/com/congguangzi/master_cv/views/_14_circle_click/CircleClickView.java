package com.congguangzi.master_cv.views._14_circle_click;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 简介: 自定义点击事件.
 *
 * <b>NOTE:</b> 不支持 KeyEvent 事件.
 *
 * @author congguangzi (congspark@163.com) 2018/4/26.
 */
public class CircleClickView extends View {

    Path topPath, bottomPath, leftPath, rightPath, centerPath;
    Region topRegion, bottomRegion, leftRegion, rightRegion, centerRegion;

    Region globalRegion;

    int width, height;

    // 点击坐标与映射坐标的变换矩阵
    Matrix matrix;
    Matrix invertMatrix;

    Paint defaultPaint, touchPaint;

    private final int CENTER = 0x01;
    private final int LEFT = 0x02;
    private final int TOP = 0x03;
    private final int RIGHT = 0x04;
    private final int BOTTOM = 0x05;
    private final int OTHER = 0x06;

    private int touchFlag = -1;
    private int curFlag = -1;

    private float touchPoint[] = new float[2];

    int defaultColor = Color.parseColor("#FF4E5268");
    int touchedColor = 0xFFDF9C81;

    private OnMenuClickListener onMenuClickListener;

    {
        topPath = new Path();
        bottomPath = new Path();
        leftPath = new Path();
        rightPath = new Path();
        centerPath = new Path();

        topRegion = new Region();
        bottomRegion = new Region();
        leftRegion = new Region();
        rightRegion = new Region();
        centerRegion = new Region();

        globalRegion = new Region();

        defaultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        defaultPaint.setColor(defaultColor);
        defaultPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        touchPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        touchPaint.setColor(touchedColor);
        touchPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        matrix = new Matrix();
        invertMatrix = new Matrix();

        touchPoint[0] = touchPoint[1] = -1;
    }

    public CircleClickView(Context context) {
        super(context);
    }

    public CircleClickView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleClickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;

        // NOTE. The Region Will Change to the Canvas.
        globalRegion.set(-w, -h, w, h);

        int minWidth = w > h ? h : w;
        minWidth *= 0.8;

        int br = minWidth / 2;
        RectF bigCircle = new RectF(-br, -br, br, br);

        int sr = minWidth / 4;
        RectF smallCircle = new RectF(-sr, -sr, sr, sr);


        float sweepAngle = 80;

        centerPath.addCircle(0, 0, 0.2f * minWidth, Path.Direction.CW);
        centerRegion.setPath(centerPath, globalRegion);

        leftPath.addArc(bigCircle, 140, sweepAngle + 4);
        leftPath.arcTo(smallCircle, 220, -sweepAngle);
        leftPath.close();
        leftRegion.setPath(leftPath, globalRegion);

        topPath.addArc(bigCircle, 230, sweepAngle + 4);
        topPath.arcTo(smallCircle, 310, -sweepAngle);
        topPath.close();
        topRegion.setPath(topPath, globalRegion);

        bottomPath.addArc(bigCircle, 50, sweepAngle + 4);
        bottomPath.arcTo(smallCircle, 130, -sweepAngle);
        bottomPath.close();
        bottomRegion.setPath(bottomPath, globalRegion);

        rightPath.addArc(bigCircle, -40, sweepAngle + 4);
        rightPath.arcTo(smallCircle, 40, -sweepAngle);
        rightPath.close();
        rightRegion.setPath(rightPath, globalRegion);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchPoint[0] = event.getX();
        touchPoint[1] = event.getY();
        invertMatrix.mapPoints(touchPoint);

        int x = (int) touchPoint[0];
        int y = (int) touchPoint[1];

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                curFlag = touchFlag = getTouchPath(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                curFlag = getTouchPath(x, y);
                break;
            case MotionEvent.ACTION_UP:
                curFlag = getTouchPath(x, y);

                if (curFlag != -1 && curFlag == touchFlag && onMenuClickListener != null) {
                    if (curFlag == CENTER) {
                        onMenuClickListener.onCenterClick();
                    } else if (curFlag == LEFT) {
                        onMenuClickListener.onLeftClick();
                    } else if (curFlag == TOP) {
                        onMenuClickListener.onTopClick();
                    } else if (curFlag == BOTTOM) {
                        onMenuClickListener.onBottomClick();
                    } else if (curFlag == RIGHT) {
                        onMenuClickListener.onRightClick();
                    } else if (curFlag == OTHER) {
                        performClick();
                    }
                }
                curFlag = touchFlag = -1;
                break;

            case MotionEvent.ACTION_CANCEL:
                touchPoint[0] = touchPoint[1] = -1;
                curFlag = touchFlag = -1;
                break;
        }
        invalidate();
        return true;
    }

    private int getTouchPath(int x, int y) {
        if (centerRegion.contains(x, y)) {
            return CENTER;
        } else if (leftRegion.contains(x, y)) {
            return LEFT;
        } else if (topRegion.contains(x, y)) {
            return TOP;
        } else if (bottomRegion.contains(x, y)) {
            return BOTTOM;
        } else if (rightRegion.contains(x, y)) {
            return RIGHT;
        }
        return OTHER;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        matrix.reset();
        matrix.preTranslate(width >> 1, height >> 1);
        canvas.save();
        canvas.setMatrix(matrix);

        matrix.invert(invertMatrix);

        canvas.drawPath(topPath, defaultPaint);
        canvas.drawPath(bottomPath, defaultPaint);
        canvas.drawPath(leftPath, defaultPaint);
        canvas.drawPath(rightPath, defaultPaint);
        canvas.drawPath(centerPath, defaultPaint);


        if (curFlag == CENTER) {
            canvas.drawPath(centerPath, touchPaint);
        } else if (curFlag == LEFT) {
            canvas.drawPath(leftPath, touchPaint);
        } else if (curFlag == TOP) {
            canvas.drawPath(topPath, touchPaint);
        } else if (curFlag == BOTTOM) {
            canvas.drawPath(bottomPath, touchPaint);
        } else if (curFlag == RIGHT) {
            canvas.drawPath(rightPath, touchPaint);
        }
        canvas.restore();


    }

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

    /**
     * 接口定义: 点击对应按钮触发
     */
    public interface OnMenuClickListener {
        void onCenterClick();

        void onLeftClick();

        void onTopClick();

        void onRightClick();

        void onBottomClick();
    }
}
