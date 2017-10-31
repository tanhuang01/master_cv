package com.congguangzi.master_cv.views._03_loadingView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.congguangzi.master_cv.R;

import java.util.List;

/**
 * 大神 gos 的进度条加载类.
 *
 * @author congguangzi (congspark@163.com) 2017/10/9.
 */

public class _03_LeafLoadingView extends View {

    private static final String TAG = "03_LeafLoadingView";

    private static final int MIDDLE_AMPLITUDE = 13;

    private static final int AMPLITUDE_DISPARITY = 5;

    private static final int TOTAL_PROGRESS = 100;

    private static final int LEFT_MARGIN = 9;   // TODO(congguangzi) : 使用自定义属性 

    private static final int RIGHT_MARGIN = 25; // // TODO(congguangzi) : 使用自定义属性

    private Bitmap leafBitmap;
    private int leafWidth, leafHeight;

    // 中等振幅大小
    private int middleAmplitude = MIDDLE_AMPLITUDE;

    // 振幅差
    private int amplitudeDisparity = AMPLITUDE_DISPARITY;

    // 叶子飘动一个周期所花的时间
    private long leafFloatTime = LeafFactory.LEAF_FLOAT_TIME;

    // 叶子旋转一周需要的时间
    private long leafRotateTime = LeafFactory.LEAF_ROTATE_TIME;


    private Bitmap outerBitmap;
    private Rect outerSrcRect, outerDestRect;
    private int outerWidth, outerHeight;

    private int totalWidth, totalHeight;

    private Paint bitmapPaint, whitePaint, orangePaint;
    private RectF whiteRectF, OrangeRectF, arcRectF;

    // 当前进度
    private int progress;

    // 所绘制进度条部分宽度
    private int progressWidth;

    // 当前所在的绘制的进度条的位置
    private int currentProgressPosition;

    // 弧形半径
    private int arcRadius;

    // acr 右上角的 x 坐标, 也是举行
    private int arcRightLocation;

    private Matrix matrix;

    private LeafFactory leafFactory;

    private List<Leaf> leafs;

    private int addTime;

    public _03_LeafLoadingView(Context context) {
        super(context);
    }

    public _03_LeafLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public _03_LeafLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        matrix = new Matrix();
        leafs = leafFactory.generateLeafs();

        leafBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.leaf);
        outerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.leaf_loading_outer);

        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapPaint.setDither(true);
        bitmapPaint.setFilterBitmap(true);

        whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        whitePaint.setColor(getResources().getColor(R.color.light_white));

        orangePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        orangePaint.setColor(getResources().getColor(R.color.oragne));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        totalWidth = w;
        totalHeight = h;

        progressWidth = totalWidth - LEFT_MARGIN - RIGHT_MARGIN;
        arcRadius = (totalHeight - (LEFT_MARGIN << 1)) >> 1;

        outerSrcRect = new Rect(0, 0, outerBitmap.getWidth(), outerBitmap.getHeight());
        outerDestRect = new Rect(0, 0, totalWidth, totalHeight);

        whiteRectF = new RectF(LEFT_MARGIN + currentProgressPosition,
                LEFT_MARGIN,
                totalWidth - RIGHT_MARGIN,
                totalHeight - LEFT_MARGIN);

        OrangeRectF = new RectF(LEFT_MARGIN + arcRadius,
                LEFT_MARGIN,
                currentProgressPosition,
                totalHeight - LEFT_MARGIN);

        arcRectF = new RectF(LEFT_MARGIN, LEFT_MARGIN,
                LEFT_MARGIN + (arcRadius << 1),
                totalHeight - LEFT_MARGIN);
        arcRightLocation = LEFT_MARGIN + arcRadius;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制进图条和叶子
        // 同一层级一起绘制
        drawProgressAndLeafs(canvas);

    }

    private void drawProgressAndLeafs(Canvas canvas) {
        if (progress >= TOTAL_PROGRESS) {
            progress = 0;
        }
        currentProgressPosition = progressWidth * progress / TOTAL_PROGRESS;

        if (currentProgressPosition < arcRadius) {
            Log.i(TAG, "progress = " + progress + " --currentProgressPosition = " +
                    +currentProgressPosition + " -- arcProgressWidth" + arcRadius);
            // 绘制白色 arc
            canvas.drawArc(arcRectF, 90, 180, false, whitePaint);

            // 绘制白色矩形
            whiteRectF.left = arcRightLocation;
            canvas.drawRect(whiteRectF, whitePaint);

            drawLeafs(canvas);

            // 半弧角度
            int angle = (int) Math.toDegrees(Math.acos(arcRadius - currentProgressPosition) / ((float) arcRadius));
            // 起始位置
            int startAngle = 180 - angle;
            // 扫过角度
            int sweepAngle = angle << 1;
            canvas.drawArc(arcRectF, startAngle, sweepAngle, false, orangePaint);
        } else {
            Log.i(TAG, "mProgress = " + progress + "---transfer-----mCurrentProgressPosition = "
                    + currentProgressPosition
                    + "--mArcProgressWidth" + arcRadius);
            // 1.绘制白底
            whiteRectF.left = currentProgressPosition;
            canvas.drawRect(whiteRectF, whitePaint);

            drawLeafs(canvas);

            // 后面长方形进度部分.
            canvas.drawArc(arcRectF, 90, 180, false, orangePaint);
            OrangeRectF.left = arcRightLocation;
            OrangeRectF.right = currentProgressPosition;
            canvas.drawRect(OrangeRectF, orangePaint);
        }
    }

    private void drawLeafs(Canvas canvas) {
        long currentTime = System.currentTimeMillis();
        for (Leaf leaf : leafs) {
            if (currentTime > leaf.startTime && leaf.startTime != 0) {
                // 根据时间计算出叶子的 (x, y) 起始坐标
                getLeafLocation(leaf, currentTime);

                canvas.save();
                matrix.reset();
                float transX = LEFT_MARGIN + leaf.x;
                float transY = LEFT_MARGIN + leaf.y;
                Log.i(TAG, "left.x = " + leaf.x + " --leaf.y= " + leaf.y);
                matrix.postTranslate(transX, transY);
                // 通过时间关联旋转角度, 可调节叶子旋转快慢
                float rotateFraction = ((currentTime - leaf.startTime) % leafRotateTime)
                        / ((float) leafRotateTime);
                int angle = (int) (rotateFraction * 360);
                // 根据叶子的旋转方向确定旋转角度
                int rotate = leaf.rotateDirection == Leaf.RotateDirection.CLOCKWISE ?
                        angle + leaf.rotateAngle : -angle + leaf.rotateAngle;
                matrix.postRotate(rotate, transX +
                        (leafWidth >> 1), transY + (leafHeight >> 1));
                canvas.drawBitmap(leafBitmap, matrix, bitmapPaint);
                canvas.restore();
            } else {
                continue;
            }
        }
    }

    private int getLeafLocation(Leaf leaf, long currentTime) {
        float w = (float) (2 * Math.PI / progressWidth);
        float a = middleAmplitude;
        switch (leaf.type) {
            case LITTLE:
                a = middleAmplitude - amplitudeDisparity;
                break;
            case MIDDLE:
                a = middleAmplitude;
                break;
            case BIG:
                a = middleAmplitude + amplitudeDisparity;
                break;
        }
        Log.i(TAG, "--a = " + a + "--w = " + w + "--leaf.x =" + leaf.x);
        return (int) (a * Math.sin(w * leaf.x) + arcRadius * 2 / 3);
    }


}
