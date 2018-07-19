package com.congguangzi.master_cv.views._18_anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.congguangzi.master_cv.R;
import com.congguangzi.master_cv.ViewUtils;

/**
 * brief: a special animation
 *
 * @author congguangzi (congspark@163.com) 2018/7/19.
 */
public class AnimationView extends View {

    // initialize with the custom attribute.
    private final int PIC_SIZE = ViewUtils.dpToPixel(200);
    private final int PIC_X = ViewUtils.dpToPixel(100);
    private final int PIC_Y = ViewUtils.dpToPixel(100);

    int width, height;

    private Paint paint;

    private Bitmap bitmap;

    private Camera camera;

    // the upper part of the view and the down part of the view.
    private Rect leftRect, rightRect;

    // the right part of the view rotate by x-axis angle
    float rightAngle;

    // the left part of the view rotate by x-axis angle
    float leftAngle;

    // the canvas rotate angle
    float rotateAngle;

    private int picCenterX;
    private int picCenterY;

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmap = getAvatar(PIC_SIZE);

        camera = new Camera();
        camera.setLocation(0, 0, ViewUtils.cameraZLocation(-6));
    }

    public AnimationView(Context context) {
        super(context);
    }

    public AnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        picCenterY = PIC_Y + (bitmap.getHeight() >> 1);
        picCenterX = PIC_X + (bitmap.getWidth() >> 1);
        leftRect = new Rect(0, 0, picCenterX, height);
        rightRect = new Rect(picCenterX, 0, width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.rotate(-rotateAngle, picCenterX, picCenterY);

        canvas.save();
        canvas.clipRect(leftRect);
        canvas.rotate(rotateAngle, picCenterX, picCenterY);
        camera.save();
        camera.rotateX(leftAngle);
        canvas.translate(picCenterX, picCenterY);
        camera.applyToCanvas(canvas);
        canvas.translate(-picCenterX, -picCenterY);
        canvas.drawBitmap(bitmap, PIC_X, PIC_Y, paint);
        camera.restore();

        // restore clipRect left
        canvas.restore();

        canvas.save();
        canvas.clipRect(rightRect);
        camera.save();
        camera.rotateY(rightAngle);
        canvas.translate(picCenterX, picCenterX);
        camera.applyToCanvas(canvas);
        canvas.translate(-picCenterX, -picCenterY);
        canvas.rotate(rotateAngle, picCenterX, picCenterY);
        canvas.drawBitmap(bitmap, PIC_X, PIC_Y, paint);
        camera.restore();

        // restore clipRect right
        canvas.restore();

        // restore rotate
        canvas.restore();
    }

    private Bitmap getAvatar(int size) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable._01_maps, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = size;
        return BitmapFactory.decodeResource(getResources(), R.drawable._01_maps, options);
    }

    public float getLeftAngle() {
        return leftAngle;
    }

    public void setLeftAngle(float leftAngle) {
        this.leftAngle = leftAngle;
        invalidate();
    }

    public float getRightAngle() {
        return rightAngle;
    }

    public void setRightAngle(float rightAngle) {
        this.rightAngle = rightAngle;
        invalidate();
    }

    public float getRotateAngle() {
        return rotateAngle;
    }

    public void setRotateAngle(float rotateAngle) {
        this.rotateAngle = rotateAngle;
        invalidate();
    }

    public AnimatorSet getAnimator() {
        // set the angles to zero to restart the animation.
        leftAngle = 0;
        rightAngle = 0;
        rotateAngle = 0;

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(this, "rightAngle", 0, -45);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(this, "rotateAngle", 0, 270);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(this, "leftAngle", 0, -45);
        animator1.setDuration(500);
        animator2.setDuration(1500);
        animator3.setDuration(500);

        AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator1, animator2, animator3);
        return set;
    }
}
