package com.congguangzi.master_cv.views._05_sin_circle_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


/**
 * @author congguangzi (congspark@163.com) 2017/11/10.
 */

public class SinCircleView extends View {

    private static final int circleNum = 7;

    // 每个圆圈的延迟.
    private static final float fraction = (float) (2 * Math.PI / circleNum);

    // 每隔圆圈的颜色.
    private int[] colors = {Color.RED, Color.parseColor("#FF6100"), Color.YELLOW,
            Color.GREEN, Color.BLUE, Color.parseColor("#082E54"), Color.parseColor("#A020F0")};

    private float[] colorFraction = {0.143f, 0.286f, 0.429f, 0.572f, 0.715f, 0.868f, 1f};

    // 圆圈的绘制效果.
    private Shader shader;
    private MaskFilter maskFilter;

    int height;
    int width;

    // in this view, we assume that 1/4 is centerY
    int centerY;

    int[] circleYSet = new int[7];

    // each circle radius
    int radius;

    Paint circlePaint;

    // the animator x pivot
    float timeX;

    Handler delayAnimation;

    public SinCircleView(Context context) {
        super(context);
    }

    public SinCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SinCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // TODO(congguangzi) : the stoke width should use the costume declare type
        circlePaint.setStrokeWidth(15);
        circlePaint.setColor(Color.BLUE);
        circlePaint.setStyle(Paint.Style.STROKE);

        circleYSet = new int[7];
        maskFilter = new BlurMaskFilter(5, BlurMaskFilter.Blur.NORMAL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;

        centerY = h >>> 2;
        radius = w >>> 4;

        shader = new LinearGradient(0, 0, width, 0, colors, colorFraction, Shader.TileMode.MIRROR);
    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        delayAnimation = new Handler() {
            int index;

            @Override
            public void handleMessage(Message msg) {
                if (index < circleYSet.length) {
                    circleYSet[index++] = centerY;
                    sendEmptyMessageDelayed(1, 200);
                }
            }
        };
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (delayAnimation != null) {
            delayAnimation.removeMessages(1);
            delayAnimation = null;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        circlePaint.setMaskFilter(maskFilter);
        circlePaint.setShader(shader);

        for (int i = 0; i < circleYSet.length; i++) {
            canvas.drawCircle(15 + radius + (15 + (radius << 1)) * i,
                    (float) (centerY + Math.sin(timeX + i * fraction) * (circleYSet[i] >> 1)),
                    radius,
                    circlePaint);
        }
    }

    public float getTimeX() {
        return timeX;
    }

    public void setTimeX(float timeX) {
        this.timeX = timeX;
        // 动画开始时, 设置每隔圆圈的 y 轴的值.
        if (delayAnimation != null && !delayAnimation.hasMessages(1)) {
            delayAnimation.sendEmptyMessageDelayed(1, 200);
        }
        invalidate();
    }

}
