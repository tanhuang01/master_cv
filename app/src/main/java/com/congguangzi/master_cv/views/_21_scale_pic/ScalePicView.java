package com.congguangzi.master_cv.views._21_scale_pic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import com.congguangzi.master_cv.R;
import com.congguangzi.master_cv.ViewUtils;

/**
 * brief:
 *
 * @author congguangzi (congspark@163.com) 2018/7/28.
 */
public class ScalePicView extends View {

    private final int IMAGE_SIZE = ViewUtils.dpToPixel(200);
    private final int SCALE = 2;

    private Paint paint;

    // the pic of the view
    private Bitmap bitmap;

    private float width, height;

    private float centerX, centerY;

    // the draw offset of the image.
    private float originalOffsetX, originalOffsetY;

    // the finger offset when scroll.
    private float fingerOffsetX, fingerOffsetY;

    private float imageWidth = IMAGE_SIZE, imageHeight;

    private float smallScale, bigScale;

    private float scalingFraction = 0f;

    // a sign to mark is bigScale or smellScale; true-bigScale
    private boolean bigScaled;

    private ObjectAnimator scaleAnimator;

    private GestureDetector gestureDetector;

    private OverScroller onScroller;

    private ScalePicFlingRunnable scalePicFlingRunnable;

    private onScaleAnimationListener onScaleAnimationListener;

    public ScalePicView(Context context) {
        super(context);
        init(context);
    }

    public ScalePicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScalePicView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void init(Context context) {
        bitmap = ViewUtils.getAvatar(context.getResources(), R.drawable._11_poly_test, (int) imageWidth);
        imageHeight = bitmap.getHeight();

        gestureDetector = new GestureDetector(context, new onScalePicGestureListener());
        gestureDetector.setOnDoubleTapListener(new onScaleDoubleTabListener());

        onScroller = new OverScroller(context);

        scalePicFlingRunnable = new ScalePicFlingRunnable();

        onScaleAnimationListener = new onScaleAnimationListener();
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);

        if ((imageWidth / imageHeight) > (getWidth() / getHeight())) {
            smallScale = getWidth() / imageWidth;
            bigScale = getHeight() / imageHeight * 2;
        } else {
            smallScale = getHeight() / imageHeight;
            bigScale = getWidth() / imageWidth * 2;
        }

        centerX = getWidth() / 2;
        centerY = getHeight() / 2;

        originalOffsetX = (getWidth() - imageWidth) / 2;
        originalOffsetY = (getHeight() - imageHeight) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float scale = smallScale + (bigScale - smallScale) * scalingFraction;
        canvas.translate(fingerOffsetX * scalingFraction, fingerOffsetY * scalingFraction);
        canvas.scale(scale, scale, centerX, centerY);
        canvas.translate(originalOffsetX, originalOffsetY);
        canvas.drawBitmap(bitmap, 0, 0, paint);

    }

    public float getScalingFraction() {
        return scalingFraction;
    }

    public void setScalingFraction(float scalingFraction) {
        this.scalingFraction = scalingFraction;
        invalidate();
    }

    private ObjectAnimator getScaleAnimator() {
        if (scaleAnimator == null) {
            scaleAnimator = ObjectAnimator.ofFloat(this, "scalingFraction", 0f, 1f);
            scaleAnimator.addListener(onScaleAnimationListener);
        }
        return scaleAnimator;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private class onScaleDoubleTabListener implements GestureDetector.OnDoubleTapListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            bigScaled = !bigScaled;
            if (bigScaled) {
                fingerOffsetX = getWidth() / 2 - e.getX();
                fingerOffsetY = getHeight() / 2 - e.getY();
                getScaleAnimator().start();
            } else {
                getScaleAnimator().reverse();
            }
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }

    }

    private class onScalePicGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent down, MotionEvent event, float distanceX, float distanceY) {
            if (bigScaled) {
                fingerOffsetX -= distanceX;
                fingerOffsetX = Math.min(fingerOffsetX, (imageWidth * bigScale - getWidth()) / 2);
                fingerOffsetX = Math.max(fingerOffsetX, (getWidth() - imageWidth * bigScale) / 2);
                fingerOffsetY -= distanceY;
                fingerOffsetY = Math.min(fingerOffsetY, (imageHeight * bigScale - getHeight()) / 2);
                fingerOffsetY = Math.max(fingerOffsetY, (getHeight() - imageHeight * bigScale) / 2);
                invalidate();
            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            onScroller.fling(((int) fingerOffsetX), ((int) fingerOffsetY), ((int) velocityX), ((int) velocityY),
                    ((int) ((getWidth() - imageWidth * bigScale) / 2)),
                    ((int) ((imageWidth * bigScale - getWidth()) / 2)),
                    ((int) ((getHeight() - imageHeight * bigScale) / 2)),
                    ((int) ((imageHeight * bigScale - getHeight()) / 2)),
                    100, 100);
            postOnAnimation(scalePicFlingRunnable);
            return false;
        }
    }

    private class ScalePicFlingRunnable implements Runnable {
        @Override
        public void run() {
            if (onScroller.computeScrollOffset()) {
                fingerOffsetX = onScroller.getCurrX();
                fingerOffsetY = onScroller.getCurrY();
                invalidate();
                postOnAnimation(this);
            }
        }
    }

    private class onScaleAnimationListener extends AnimatorListenerAdapter {
        @Override
        public void onAnimationEnd(Animator animation, boolean isReverse) {
            if (isReverse) {
                fingerOffsetX = fingerOffsetY = 0;
            }
        }
    }


}
