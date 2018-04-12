package com.congguangzi.master_cv.views._10_path_measure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author congguangzi (congspark@163.com) 2018/4/11.
 */

public class PathMeasureView extends View {


    private int width;
    private int height;
    private int halfWidth;
    private int halfHeight;

    private Paint corPaint;

    private Paint defaultPaint;

    private Paint arrowPaint;

    Path path;

    Path arrowPath;

    PathMeasure measure, arrowMeasure;

    // 完成度
    private float progress;

    private float[] pos;
    private float[] tan;

    // 线性变换
    Matrix matrix;

    {
        corPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        corPaint.setColor(Color.RED);
        corPaint.setStrokeWidth(2);

        defaultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        defaultPaint.setStrokeWidth(10);
        defaultPaint.setStyle(Paint.Style.STROKE);

        arrowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arrowPaint.setStrokeWidth(5);
        arrowPaint.setStyle(Paint.Style.STROKE);
        arrowPaint.setStrokeJoin(Paint.Join.ROUND);

        path = new Path();
        arrowPath = new Path();
        measure = new PathMeasure();
        arrowMeasure = new PathMeasure();

        pos = new float[2];
        tan = new float[2];

        matrix = new Matrix();
    }

    public PathMeasureView(Context context) {
        super(context);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PathMeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        halfHeight = h >> 1;
        halfWidth = w >> 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCoordinate(canvas);

        // 箭头
        arrowPath.reset();
        arrowPath.moveTo(0, 0);
        arrowPath.lineTo(-90, 40);
        arrowPath.lineTo(-75, 0);
        arrowPath.lineTo(-90, -40);
        arrowPath.close();

        // 圆
        path.reset();
        path.addCircle(0, 0, 300, Path.Direction.CCW);

        // 箭头位置和偏转角度
        matrix.reset();
        measure.setPath(path, false);
//        measure.getPosTan(measure.getLength() * progress, pos, tan);

//        arrowMeasure.setPath(arrowPath, false);
//        float degree = (float) (Math.atan2(tan[1], tan[0]) * 180 / Math.PI);
//        matrix.postRotate(degree);
//        matrix.postTranslate(pos[0], pos[1]); // 矩阵平移需要放到最后

        // 直接使用矩阵获取
        measure.getMatrix(measure.getLength() * progress, matrix,
                PathMeasure.POSITION_MATRIX_FLAG | PathMeasure.TANGENT_MATRIX_FLAG);
        /* matrix 操作的默认中心点是 path 添加时相对于坐标系的 (0,0) 点 */
        arrowPath.transform(matrix);


        canvas.drawPath(path, defaultPaint);
        canvas.drawPath(arrowPath, arrowPaint);
    }

    /**
     * 绘制坐标系
     */
    private void drawCoordinate(Canvas canvas) {
        // 平移画布
        canvas.translate(halfWidth, halfHeight);

        canvas.drawLine(-halfWidth, 0, halfWidth, 0, corPaint);
        canvas.drawLine(0, -halfHeight, 0, halfHeight, corPaint);
    }


    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = (float) (progress - Math.floor(progress));
        invalidate();
    }
}
