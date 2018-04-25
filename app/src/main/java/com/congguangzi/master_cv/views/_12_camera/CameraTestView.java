package com.congguangzi.master_cv.views._12_camera;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.congguangzi.master_cv.R;

/**
 * brief: 测试 camera 相关 api.
 *
 * @author congguangzi (congspark@163.com) 2018/4/16.
 */
public class CameraTestView extends View {

    public static final short X = 0x01;
    public static final short Y = 0x02;
    public static final short Z = 0x03;

    // default is x-coordinate
    private short cur_coordinate = 0x01;

    private Bitmap bitmap;
    Camera camera = new Camera();
    Matrix matrix = new Matrix();
    Matrix tempMatrix = new Matrix();
    int degree = 0;
    Point point = new Point(200, 50);
    int centerX = 0;
    int centerY = 0;
    ObjectAnimator animator = ObjectAnimator.ofInt(this, "degree", 0, 360);

    {
        animator.setDuration(3000);
        animator.setInterpolator(new LinearInterpolator());
    }

    public CameraTestView(Context context) {
        super(context);
        init();
    }

    public CameraTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CameraTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable._01_maps);
        centerX = point.x + (bitmap.getWidth() >> 1);
        centerY = point.y + (bitmap.getHeight() >> 1);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        matrix.reset();
        camera.save();
        camera.setLocation(0, 0, -20);

        if (cur_coordinate == X) {
            camera.rotateX(degree);
        } else if (cur_coordinate == Y) {
            camera.rotateY(degree);
        } else {
            camera.rotateZ(degree);
        }
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
        canvas.save();
        canvas.concat(matrix);

        canvas.drawBitmap(bitmap, point.x, point.y, null);
//        canvas.drawBitmap(bitmap, matrix, null);
        canvas.restore();
    }

    public void startAnimator() {
        animator.start();
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        this.degree = degree;
        invalidate();
    }

    public void setCur_coordinate(final short cur_coordinate) {
        // update the coordinate after the animator if necessary
        if (!animator.isRunning()) {
            this.cur_coordinate = cur_coordinate;

        } else {
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    CameraTestView.this.cur_coordinate = cur_coordinate;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }
}
