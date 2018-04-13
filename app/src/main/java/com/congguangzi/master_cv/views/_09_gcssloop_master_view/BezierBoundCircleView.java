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
 * 简介: 使用贝塞尔曲线实现圆的拉伸和回弹效果.
 * <p>
 * 目前仅仅支持水平方向上的拉伸和回弹, 如果是任意方向, 可以旋转 canvas
 * <p>
 * 有关贝塞尔曲线的理解  <a href="http://spencermortensen.com/articles/bezier-circle/">bezier-circle</a>
 * <p>
 * 仅仅作为测试和学习贝塞尔曲线和 Path 相关 api 用. 如果作为背景应该使用 drawable.
 * <p>
 * v1,v2, h1, h2 相关点的位置关系如图:
 * <p>
 * <img alt="the coordinate" src ="../../../../../../../../../pic/_09_pic_coor.png" />
 *
 * @author congguangzi (congspark@163.com) 2018/4/9.
 */
public class BezierBoundCircleView extends View {

    // 贝塞尔曲线 [控制点与绘制点的距离] 和 [圆的半径] 的比例
    private final float CUBIC_RATE = 0.551915f;

    private Paint pathPaint;

    private Path path;

    // 绘制贝塞尔曲线控制点
    private Paint pointPaint;

    // 绘制贝塞尔曲线控制点的连线
    private Paint linePaint;

    // 坐标
    private Paint corPaint;

    /**
     * 动画完成度 [0, 1].
     */
    private float progress;

    // 圆拉伸长度
    private float stretchLength;

    // 贝塞尔曲线 c 值
    private float c;

    // 贝塞尔曲线竖直方向上控制点的移动距离
    private float cDistance;

    // 圆半径
    private int radius;

    // 水平方向上的移动距离
    private int moveDistance;

    private int height;
    private int width;

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

        // 水平方向上的移动距离暂定为 控件宽度 - 3 * radius
        moveDistance = w - (radius << 2);
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

        // 相关参数暂时在代码中指定.
        radius = 100;
        stretchLength = radius;
        c = radius * CUBIC_RATE;
        cDistance = c * 0.45f;

        h1 = new HorizontalBezierPoint();
        h2 = new HorizontalBezierPoint();
        v1 = new VerticalBezierPoint();
        v2 = new VerticalBezierPoint();

        path = new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCoordinate(canvas);

        // 不同状态的切换过度
        if (progress >= 0 && progress < 0.2) {
            model1(progress);
        } else if (progress >= 0.2 && progress < 0.5) {
            model2(progress);
        } else if (progress >= 0.5 && progress < 0.8) {
            model3(progress);
        } else if (progress >= 0.8 && progress < 0.9) {
            model4(progress);
        } else {
            model5(progress);
        }

        // 水平移动
        int offsetX = (int) (moveDistance * (progress - 0.2f));
        if (offsetX > 0) {
            v1.setOffsetX(offsetX);
            v2.setOffsetX(offsetX);
            h1.setOffsetX(offsetX);
            h2.setOffsetX(offsetX);
        }


        // 贝塞尔曲线对应的圆
        drawBezierCurve(canvas);

        // 辅助点
        drawPoint(canvas, h1.left, h1.right, h2.left, h2.right,
                v1.top, v1.bottom, v2.top, v2.bottom);


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
        path.moveTo(v2.x, v2.y);
        path.cubicTo(v2.bottom.x, v2.bottom.y,
                h2.right.x, h2.right.y,
                h2.x, h2.y);
        path.cubicTo(h2.left.x, h2.left.y,
                v1.bottom.x, v1.bottom.y,
                v1.x, v1.y);
        path.cubicTo(v1.top.x, v1.top.y,
                h1.left.x, h1.left.y,
                h1.x, h1.y);
        path.cubicTo(h1.right.x, h1.right.y,
                v2.top.x, v2.top.y,
                v2.x, v2.y);
        canvas.drawPath(path, pathPaint);
    }

    private void drawPoint(Canvas canvas, PointF... points) {
        for (PointF point : points) {
            canvas.drawPoint(point.x, point.y, pointPaint);
        }

    }

    /**
     * 绘制竖直方向上的辅助线
     */
    private void drawBezierLines(Canvas canvas, VerticalBezierPoint point) {
        canvas.drawLine(point.top.x, point.top.y, point.bottom.x, point.bottom.y, linePaint);
    }

    /**
     * 绘制水平方向的辅助线
     */
    private void drawBezierLines(Canvas canvas, HorizontalBezierPoint point) {
        canvas.drawLine(point.left.x, point.left.y, point.right.x, point.right.y, linePaint);
    }


    private void model0() {
        // 初始化 [绘制点] 和 [控制点]
        h1.x = h2.x = 0;
        v1.x = h1.y = -radius;
        v1.y = v2.y = 0;
        h2.y = v2.x = radius;

        h1.setControlPoint(radius);
        h2.setControlPoint(radius);
        v1.setControlPoint(radius);
        v2.setControlPoint(radius);
    }

    private void model1(float progress) { // [0, 0.2)
        model0();

        v2.setOffsetX((stretchLength * progress * 5));   // *5 确保每一段 progress 变化为1
    }

    private void model2(float progress) { // [0.2, 0.5)
        model1(0.2f);
        progress = (progress - 0.2f) * (10f / 3);   // 乘 10/3 确保每一段变化为1

        // h1, h2 的平移效果
        h1.setOffsetX((stretchLength / 2) * progress);
        h2.setOffsetX((stretchLength / 2) * progress);

        // v2, v1 的拉伸效果, 让 model1 拉伸过后的右侧变圆
        v1.setOffsetY(cDistance * progress);
        v2.setOffsetY(cDistance * progress);
    }

    private void model3(float progress) {   // [0.5, 0.8)
        model2(0.5f);
        progress = (progress - 0.5f) * (10f / 3);

        // h1, h2 平移效果
        h1.setOffsetX((stretchLength / 2) * progress);
        h2.setOffsetX((stretchLength / 2) * progress);

        // v2, v1 反向拉伸
        v1.setOffsetY(-cDistance * progress);
        v2.setOffsetY(-cDistance * progress);

        // v1 [绘制点] 前移
        v1.setOffsetX((stretchLength / 2) * progress);

    }

    private void model4(float progress) {   // [0.8, 0.9)
        model3(0.8f);

        progress = (progress - 0.8f) * 10;  // *10 确保每一段变化为 1.

        // v1 [绘制点] 前移
        v1.setOffsetX((stretchLength / 2) * progress);
    }

    private void model5(float progress) { // [0.9, 1] 回弹效果.
        model4(0.9f);

        progress = (progress - 0.9f) * 10;

        v1.setOffsetX((float) (Math.sin(Math.PI * progress) * (1f / 5 * radius))); // 回弹的距离为 radius 的 1/5
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        if (progress >= 0 && progress <= 1) {
            this.progress = progress;
            invalidate();
        } else {
            this.progress = 0;
        }
    }

    /**
     * x 轴上的绘制点, 以及其对应的竖直方向上的 [控制点] 的封装
     * <p>
     * <b>NOTE:</b> 为了日后能看懂, 故分成两个类
     */
    private class VerticalBezierPoint {
        float x, y;
        PointF top, bottom;

        VerticalBezierPoint() {
            top = new PointF();
            bottom = new PointF();
        }

        void setControlPoint(float radius) {
            top.x = this.x;
            bottom.x = this.x;
            top.y = this.y - c;
            bottom.y = this.y + c;
        }

        void setOffsetX(float offsetX) {
            this.x += offsetX;
            top.x += offsetX;
            bottom.x += offsetX;
        }

        /**
         * 设置 x 轴上, 竖直方向的控制点的偏移
         *
         * @param offsetY
         */
        void setOffsetY(float offsetY) {
            top.y -= offsetY;
            bottom.y += offsetY;
        }
    }

    /**
     * y 轴上的 [绘制点], 以及其对应水平方向上的 [控制点] 的封装
     */
    private class HorizontalBezierPoint {
        float x, y;
        PointF left, right;

        public HorizontalBezierPoint() {
            left = new PointF();
            right = new PointF();
        }

        void setControlPoint(float radius) {
            left.y = this.y;
            right.y = this.y;
            left.x = this.x - c;
            right.x = this.x + c;
        }

        void setOffsetX(float offsetX) {
            this.x += offsetX;
            left.x += offsetX;
            right.x += offsetX;
        }
    }


}
