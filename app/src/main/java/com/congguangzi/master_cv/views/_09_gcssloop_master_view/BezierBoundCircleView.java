package com.congguangzi.master_cv.views._09_gcssloop_master_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 简介:
 *
 * @author congguangzi (congspark@163.com) 2018/4/9.
 */
public class BezierBoundCircleView extends View {

    // 贝塞尔曲线 [控制点与绘制点的距离] 和 [圆的半径] 的比例
    private final float CUBIC_RATE = 0.551915024494f;

    private Paint pathPaint;

    private Path path;

    // 绘制贝塞尔曲线控制点
    private Paint pointPaint;

    // 绘制贝塞尔曲线控制点的连线
    private Paint linePaint;

    // 坐标新
    private Paint corPaint;

    // 圆半径
    private int radius;

    private int height;
    private int width;

    // 水平平移
    private float offsetX = 0f;

    // y 轴上的控制点
    private HorizontalBezierPoint h1, h2;
    // x 轴上的控制点
    private VerticalBezierPoint v1, v2;

    public BezierBoundCircleView(Context context) {
        super(context);
    }

    public BezierBoundCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BezierBoundCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
    }

    {
        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setColor(Color.BLUE);
        pathPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        pathPaint.setStrokeWidth(5);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.GRAY);
        linePaint.setStrokeWidth(3);

        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setColor(Color.BLACK);
        pointPaint.setStrokeWidth(10);
        pointPaint.setStrokeCap(Paint.Cap.ROUND);

        corPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        corPaint.setColor(Color.RED);
        corPaint.setStrokeWidth(1);

        // 暂时在代码中指定.
        radius = 100;

        h1 = new HorizontalBezierPoint(0, -radius);
        h2 = new HorizontalBezierPoint(0, radius);
        v1 = new VerticalBezierPoint(-radius, 0);
        v2 = new VerticalBezierPoint(radius, 0);
        h1.setControlPoint(radius);
        h2.setControlPoint(radius);
        v1.setControlPoint(radius);
        v2.setControlPoint(radius);

        path = new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCoordinate(canvas);

        // 贝塞尔曲线对应的圆
        drawBezierCurve(canvas);

        // 辅助点
        drawPoint(canvas, h1.left, h1.right,
                h2.left, h2.right,
                v2.top, v2.bottom,
                v1.top, v1.bottom);

        // 辅助线
        drawBezierLines(canvas, h1);
        drawBezierLines(canvas, h2);
        drawBezierLines(canvas, v1);
        drawBezierLines(canvas, v2);
    }


    private void drawCoordinate(Canvas canvas) {
        // 平移画布
        canvas.translate(200, 400);

        canvas.drawLine(-200, 0, width - 200, 0, corPaint);
        canvas.drawLine(0, -400, 0, height - 400, corPaint);
    }


    private void drawBezierCurve(Canvas canvas) {
        path.reset();
        path.moveTo(v2.x + offsetX, v2.y);
        // offsetX 为水平偏移量
        path.cubicTo(v2.bottom.x + offsetX, v2.bottom.y,
                h2.right.x + offsetX, h2.right.y,
                h2.x + offsetX, h2.y);
        path.cubicTo(h2.left.x + offsetX, h2.left.y,
                v1.bottom.x + offsetX, v1.bottom.y,
                v1.x + offsetX, v1.y);
        path.cubicTo(v1.top.x + offsetX, v1.top.y,
                h1.left.x + offsetX, h1.left.y,
                h1.x + offsetX, h1.y);
        path.cubicTo(h1.right.x + offsetX, h1.right.y,
                v2.top.x + offsetX, v2.top.y,
                v2.x + offsetX, v2.y);
        canvas.drawPath(path, pathPaint);
    }

    private void drawPoint(Canvas canvas, PointF... points) {
        for (PointF point : points) {
            canvas.drawPoint(point.x + offsetX, point.y, pointPaint);
        }

    }

    /**
     * 绘制竖直方向上的辅助线
     */
    private void drawBezierLines(Canvas canvas, VerticalBezierPoint point) {
        canvas.drawLine(point.top.x + offsetX, point.top.y, point.bottom.x + offsetX, point.bottom.y, linePaint);
    }

    /**
     * 绘制水平方向的辅助线
     */
    private void drawBezierLines(Canvas canvas, HorizontalBezierPoint point) {
        canvas.drawLine(point.left.x + offsetX, point.left.y, point.right.x + offsetX, point.right.y, linePaint);
    }

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
        invalidate();
    }

    /**
     * x 轴上的绘制点, 以及其对应的竖直方向上的 [控制点] 的封装
     * <p>
     * <b>NOTE:</b> 为了日后能看懂, 故分成两个类
     */
    private class VerticalBezierPoint {
        float x, y;
        PointF top, bottom;

        VerticalBezierPoint(float x, float y) {
            this.x = x;
            this.y = y;
            top = new PointF();
            bottom = new PointF();
        }

        void setControlPoint(float radius) {
            top.x = this.x;
            bottom.x = this.x;
            top.y = this.y - (CUBIC_RATE * radius);
            bottom.y = this.y + (CUBIC_RATE * radius);
        }
    }

    /**
     * y 轴上的 [绘制点], 以及其对应水平方向上的 [控制点] 的封装
     */
    private class HorizontalBezierPoint {
        float x, y;
        PointF left, right;

        public HorizontalBezierPoint(float x, float y) {
            this.x = x;
            this.y = y;
            left = new PointF();
            right = new PointF();
        }

        void setControlPoint(float radius) {
            left.y = this.y;
            right.y = this.y;
            left.x = this.x - (CUBIC_RATE * radius);
            right.x = this.x + (CUBIC_RATE * radius);
        }

    }


}
