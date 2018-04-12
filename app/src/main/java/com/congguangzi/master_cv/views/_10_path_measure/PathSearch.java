package com.congguangzi.master_cv.views._10_path_measure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 简介: 仅供参考学习 PathMeasure 使用, 不建议用于任何商业项目
 *
 * @author congguangzi (congspark@163.com) 2018/4/12.
 */
public class PathSearch extends View {

    private Paint pathPaint;

    private Paint corPaint;

    // 放大镜
    private Path searchPath;

    // 圆
    private Path circlePath;

    private Path dstPath;

    private PathMeasure searchMeasure;
    private PathMeasure circleMeasure;

    private RectF searchRect;
    private RectF circleRect;

    private float halfWidth;
    private float halfHeight;

    // path 开始的坐标点,
    private float startX;

    private float searchProgress;
    private float circleProgress;

    {
        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setStrokeWidth(30);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setColor(Color.BLUE);
        pathPaint.setStrokeCap(Paint.Cap.ROUND);

        corPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        corPaint.setStrokeWidth(1);
        corPaint.setColor(Color.RED);

        dstPath = new Path();

        searchPath = new Path();
        circlePath = new Path();

        searchRect = new RectF(-100, -100, 100, 100);
        circleRect = new RectF(-200, -200, 200, 200);


        startX = (float) (100f / Math.sqrt(2f));
        searchPath.moveTo(startX, startX);
        searchPath.addArc(searchRect, 45f, 359.9f);

        startX *= 2;
        searchPath.lineTo(startX, startX);
        circlePath.addArc(circleRect, 45f, 359.9f);

        searchMeasure = new PathMeasure(searchPath, false);
        circleMeasure = new PathMeasure(circlePath, false);
    }

    public PathSearch(Context context) {
        super(context);
    }

    public PathSearch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PathSearch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        halfHeight = h >> 1;
        halfWidth = w >> 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCoordinate(canvas);

        dstPath.reset();

        if (searchProgress >= 0 && searchProgress < 1) {
            float startD = searchProgress * searchMeasure.getLength();
            float stopD = searchMeasure.getLength();
            searchMeasure.getSegment(startD, stopD, dstPath, true);
        }

        if (circleProgress > 0 && circleProgress < 1) {
            float stopD = circleProgress * circleMeasure.getLength();
            float startD = stopD - (0.5f - Math.abs(circleProgress - 0.5f)) * 500f;
            circleMeasure.getSegment(startD, stopD, dstPath, true);
        }

        canvas.drawPath(dstPath, pathPaint);
    }

    private void drawCoordinate(Canvas canvas) {
        // 平移画布
        canvas.translate(halfWidth, halfHeight);

        canvas.drawLine(-halfWidth, 0, halfWidth, 0, corPaint);
        canvas.drawLine(0, -halfHeight, 0, halfHeight, corPaint);
    }

    public float getSearchProgress() {
        return searchProgress;
    }

    public void setSearchProgress(float searchProgress) {
        this.searchProgress = searchProgress;
        invalidate();
    }

    public float getCircleProgress() {
        return circleProgress;
    }

    public void setCircleProgress(float circleProgress) {
        this.circleProgress = (float) (circleProgress - Math.floor(circleProgress));
        invalidate();
    }
}
