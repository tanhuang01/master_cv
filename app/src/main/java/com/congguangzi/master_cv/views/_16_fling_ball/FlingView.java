package com.congguangzi.master_cv.views._16_fling_ball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * brief: a fling ball
 *
 * @author congguangzi (congspark@163.com) 2018/5/2.
 */
public class FlingView extends View {

    private Paint paint;
    private Point point;

    private GestureDetector gestureDetector;

    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener;

    private boolean isFling;

    int height, width;
    int velocityX, velocityY;

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        point = new Point();
        point.x = 300;
        point.y = 300;

        simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent eDown, MotionEvent eMove, float velocityX, float velocityY) {

                if (isFling) {
                    return true;
                }

                Log.d("speed", "velocity-X= " + velocityX + " velocity-Y= " + velocityY);
                FlingView.this.velocityX = (int) velocityX / 50;
                FlingView.this.velocityY = (int) velocityY / 50;

                isFling = true;
                invalidate();

                return super.onFling(eDown, eMove, velocityX, velocityY);
            }
        };

        gestureDetector = new GestureDetector(getContext(), simpleOnGestureListener);

    }

    public FlingView(Context context) {
        super(context);
    }

    public FlingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    public FlingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        point.x += velocityX;
        point.y += velocityY;

        if (point.x > width - 150 || point.x < 150) {
            velocityX *= -1;
            point.x += velocityX;
        }
        if (point.y > height - 150 || point.y < 150) {
            velocityY *= -1;
            point.y += velocityY;
        }
        canvas.drawCircle(point.x, point.y, 150, paint);

        if (velocityX > 0) {
            velocityX -= 1;
        } else if (velocityX < 0) {
            velocityX += 1;
        } else {
            velocityX = 0;
        }

        if (velocityY > 0) {
            velocityY -= 1;
        } else if (velocityY < 0) {
            velocityY += 1;
        } else {
            velocityY = 0;
        }

        if (velocityX != 0 || velocityY != 0) {
            invalidate();
        } else {
            isFling = false;
        }

    }
}
