package com.congguangzi.master_cv.views._03_loadingView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.congguangzi.master_cv.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义进度条加载类.
 *
 * @author congguangzi (congspark@163.com) 2017/10/9.
 */

public class LeafLoadingView extends View {

    private final float TOTAL_PROGRESS = 100f;

    private int height;
    private int width;
    private int centerX;
    private int centerY;

    // loading 中间矩形部分位置 .左-右-高. 不包括两侧圆形部分.
    private int loadingBarLeft;
    private int loadingBarRight;
    private int loadingBarHeight;

    /**
     * 两侧圆形的半径.
     */
    private int radius;

    /**
     * 进度条中间的长方形部分.
     */
    private RectF loadingBarRect;

    /**
     * 进度条左侧半圆
     */
    private RectF leftArcRect;

    /**
     * 进度条长度, 包括左侧半圆和中间矩形.
     */
    private int totalLoadingLength;

    /**
     * 进度条颜色, 叶子, 文字颜色.
     */
    private Paint orangePaint;


    /**
     * 进度条背景颜色.
     */
    private Paint lightWhitePaint;

    /**
     * 圆圈及其他部分绘制
     */
    private Paint whitePaint;

    /**
     * 进度 0 - 100.
     */
    private int progress;

    /**
     * 限制绘制区域. 主要用于进度条的左侧半圆部分.
     */
    private Path clipPath;

    private Bitmap fanBitmap;

    private int fanDegree;


    private Bitmap leafBitmap;

    // 叶子按照正弦函数飘动轨迹的振幅.
    private float amplitude;

    // 叶子按照正弦函数轨迹飘动的振幅偏差.
    private float disparity;

    private float leafFloatTime = 2000;

    private List<Leaf> leaves;

    private int leavesCount = 0;

    {
        orangePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        orangePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        orangePaint.setColor(Color.parseColor("#ffffa800"));

        lightWhitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lightWhitePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        lightWhitePaint.setColor(Color.parseColor("#fffde399"));

        whitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        whitePaint.setColor(Color.WHITE);
        whitePaint.setStyle(Paint.Style.STROKE);
        whitePaint.setStrokeWidth(10);

        clipPath = new Path();

        fanBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.leaf_loading_fan);
        leafBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.leaf);

        leaves = new ArrayList<>(20);
    }

    public LeafLoadingView(Context context) {
        super(context);
    }

    public LeafLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LeafLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        // 每调用 5 次, 添加一片叶子.
        if ((leavesCount++) % 15 == 0) {
            leaves.add(Leaf.generateLeaf());
        }
        this.progress = progress;
        invalidate();
    }

    public int getFanDegree() {
        return fanDegree;
    }

    public void setFanDegree(int fanDegree) {
        invalidate();
        this.fanDegree = fanDegree;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
        centerX = width >> 1;
        centerY = height >> 1;

        // 进度条宽度为控件宽度 1/2, 高度为宽度 1/4
        loadingBarLeft = width >> 2;
        loadingBarRight = centerX + loadingBarLeft;
        loadingBarHeight = width >> 3;
        radius = loadingBarHeight >> 1;

        // 进度条
        loadingBarRect = new RectF(loadingBarLeft,
                centerY - radius,
                loadingBarRight,
                centerY + radius);
        // 左侧半圆
        leftArcRect = new RectF(loadingBarLeft - radius,
                centerY - radius,
                loadingBarLeft + radius,
                centerY + radius);

        // 计算出整个进度条的宽度.
        totalLoadingLength = radius + (loadingBarRight - loadingBarLeft);

        // 进度条的绘制区域.
        clipPath.addCircle(loadingBarLeft, centerY, radius - (radius >>> 2), Path.Direction.CW);
        clipPath.addRect(loadingBarRect.left,
                loadingBarRect.top + (radius >>> 2),
                loadingBarRect.right,
                loadingBarRect.bottom - (radius >>> 2), Path.Direction.CW);

        Bitmap tempBitmap = Bitmap.createScaledBitmap(fanBitmap,
                (radius - ((int) whitePaint.getStrokeWidth())) << 1,
                (radius - ((int) whitePaint.getStrokeWidth())) << 1, true);
        fanBitmap.recycle();
        fanBitmap = tempBitmap;

        amplitude = radius >> 1;
        disparity = radius >> 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制文字
        orangePaint.setTextSize(80);
        orangePaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Loading...", centerX, height >>> 2, orangePaint);

        // 基本背景. 中间矩形, 两侧圆形图案.
        canvas.drawRect(loadingBarRect, lightWhitePaint);
        canvas.drawArc(leftArcRect, 90, 180, false, lightWhitePaint);

        // 绘制叶子
        drawLeafs(canvas);

        // 进度条的绘制.
        drawLoading(canvas);

        // 绘制右侧圆形
        canvas.drawCircle(loadingBarRight, centerY, radius, orangePaint);
        canvas.drawCircle(loadingBarRight, centerY, radius, whitePaint);

        // 绘制风扇
        drawFan(canvas);
    }

    private void drawLeafs(Canvas canvas) {
        long currentTime = System.currentTimeMillis();
        for (int i = leaves.size() - 1; i >= 0; i--) {
            Leaf leaf = leaves.get(i);
            if (currentTime > leaf.startTime && leaf.startTime != 0) {
                getLeafLocation(leaf, currentTime);
                canvas.save();
                canvas.clipPath(clipPath);

                Matrix matrix = new Matrix();
                float transX = loadingBarLeft - radius + leaf.x;
                float transY = centerY + leaf.y - (leafBitmap.getHeight() >> 1);
                matrix.postTranslate(transX, transY);

                float rotateFraction = ((currentTime - leaf.startTime) % leafFloatTime) / leafFloatTime;
                int angle = (int) (rotateFraction * 360);

                int rotate = leaf.rotateDirection == 0 ? angle + leaf.rotateAngle : -angle
                        + leaf.rotateAngle;
                matrix.postRotate(rotate, transX
                        + leafBitmap.getWidth() / 2, transY + leafBitmap.getHeight() / 2);
                canvas.drawBitmap(leafBitmap, matrix, orangePaint);
                canvas.restore();
            } else {
                continue;
            }
            // 飘到左侧不在绘制.
            if (leaf.x < 5) {
                leaves.remove(leaf);
            }
        }
    }

    private void getLeafLocation(Leaf leaf, long currentTime) {
        long intervalTime = currentTime - leaf.startTime;
        if (intervalTime > leafFloatTime) {
            leaf.startTime = currentTime;
        }
        float fraction = intervalTime / leafFloatTime;
        leaf.x = (int) (totalLoadingLength - totalLoadingLength * fraction);
        leaf.y = getLocationY(leaf);
    }


    private int getLocationY(Leaf leaf) {
        float w = (float) ((float) 2 * Math.PI / totalLoadingLength);
        float a = amplitude;
        switch (leaf.type) {
            case Leaf.SMALL:
                a = amplitude - disparity;
                break;
            case Leaf.NORMAL:
                a = amplitude;
                break;
            case Leaf.BIG:
                a = amplitude + disparity;
                break;
            default:
                break;
        }
        return (int) (a * Math.sin(w * leaf.x));
    }


    private void drawLoading(Canvas canvas) {
        canvas.save();
        canvas.clipPath(clipPath);
        float curLength = (progress / TOTAL_PROGRESS) * totalLoadingLength;
        canvas.drawRect(loadingBarRect.left - radius,
                loadingBarRect.top,
                loadingBarRect.left - radius + curLength,
                loadingBarRect.bottom, orangePaint);
        canvas.restore();
    }

    private void drawFan(Canvas canvas) {
        canvas.save();
        canvas.rotate(progress * 12, loadingBarRight, centerY);
        canvas.drawBitmap(fanBitmap,
                loadingBarRect.right - radius + whitePaint.getStrokeWidth(),
                loadingBarRect.top + whitePaint.getStrokeWidth(),
                whitePaint);
        canvas.restore();
    }
}
