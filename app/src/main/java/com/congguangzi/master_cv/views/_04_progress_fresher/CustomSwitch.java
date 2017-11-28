package com.congguangzi.master_cv.views._04_progress_fresher;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.congguangzi.master_cv.R;
import com.congguangzi.master_cv.ViewUtils;

/**
 * brief: custom switch
 * width: 60dp
 * height: 32dp
 *
 * @author congguangzi (congspark@163.com) 2017/11/27.
 */
public class CustomSwitch extends View {

    /**
     * the checked statu of the switch <br>
     * when CHECK_FLAG equals checked field, we assume that the switch is checked;
     */
    private final int CHECK_FLAG = 0x01;

    private int checked = 0x00;

    int height;
    int width;

    private Paint paint;

    private Path clipPath;

    private Bitmap checked_icon;
    private Bitmap unChecked_icon;

    private onCustomCheckedChangedListener onCheckedChangeListener;

    // for thumb translating X
    ObjectAnimator animator;

    // position X of the checked_icon or unChecked_icon
    int iconPositionX;

    public CustomSwitch(Context context) {
        super(context);
    }

    public CustomSwitch(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSwitch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        clipPath = new Path();
        checked_icon = BitmapFactory.decodeResource(getResources(), R.drawable._04_switch_on);
        unChecked_icon = BitmapFactory.decodeResource(getResources(), R.drawable._04_switch_off);

        // TODO(congguangzi) : update the size of the bitmap, define the bitmap size by view
        Bitmap tempChecked_icon = Bitmap.createScaledBitmap(checked_icon, ViewUtils.dpToPixel(30),
                ViewUtils.dpToPixel(30), true);
        checked_icon.recycle();
        checked_icon = tempChecked_icon;

        Bitmap tempUnChecked_cion = Bitmap.createScaledBitmap(unChecked_icon, ViewUtils.dpToPixel(30),
                ViewUtils.dpToPixel(30), true);
        unChecked_icon.recycle();
        unChecked_icon = tempUnChecked_cion;

        iconPositionX = ViewUtils.dpToPixel(1);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animator != null) {
            animator.cancel();
            animator = null;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        clipPath.addCircle(ViewUtils.dpToPixel(16), ViewUtils.dpToPixel(16), ViewUtils.dpToPixel(16), Path.Direction.CW);
        clipPath.addCircle(ViewUtils.dpToPixel(44), ViewUtils.dpToPixel(16), ViewUtils.dpToPixel(16), Path.Direction.CW);
        clipPath.addRect(ViewUtils.dpToPixel(16), ViewUtils.dpToPixel(0),
                ViewUtils.dpToPixel(44), ViewUtils.dpToPixel(32), Path.Direction.CW);
        canvas.clipPath(clipPath);

        super.onDraw(canvas);

        if (checked == CHECK_FLAG) {  // checked
            canvas.drawBitmap(checked_icon, iconPositionX, ViewUtils.dpToPixel(1), paint);
        } else { // unchecked
            canvas.drawBitmap(unChecked_icon, iconPositionX, ViewUtils.dpToPixel(1), paint);
        }
    }

    public int getIconPositionX() {
        return iconPositionX;
    }

    public void setIconPositionX(int iconPositionX) {
        this.iconPositionX = iconPositionX;
        invalidate();
    }

    public void setOnCheckedChangeListener(onCustomCheckedChangedListener listener) {
        this.onCheckedChangeListener = listener;
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checked ^= CHECK_FLAG;
                CustomSwitch.this.setSelected(checked == CHECK_FLAG);
                startCheckedAnimator(checked == CHECK_FLAG);
                if (onCheckedChangeListener != null) {
                    onCheckedChangeListener.onCheckedChanged(CustomSwitch.this, checked == CHECK_FLAG);
                }
            }
        });
    }

    private void startCheckedAnimator(boolean checked) {
        if (checked) {
            animator = ObjectAnimator.ofInt(this, "iconPositionX", ViewUtils.dpToPixel(29));
            animator.start();
        } else {
            animator = ObjectAnimator.ofInt(this, "iconPositionX", ViewUtils.dpToPixel(1));
            animator.start();
        }
    }

    public void setChecked(boolean checked) {
        if (checked) {
            this.checked = CHECK_FLAG;
            iconPositionX = ViewUtils.dpToPixel(29);
        } else {
            this.checked ^= CHECK_FLAG;
            iconPositionX = ViewUtils.dpToPixel(1);
        }
        setSelected(checked);
        startCheckedAnimator(checked);
    }

    public interface onCustomCheckedChangedListener {
        /**
         * 状态切换按钮.
         *
         * @param view    当前 switch 按钮.
         * @param checked 是否选中 true-选中, false-未选中
         */
        void onCheckedChanged(View view, boolean checked);
    }

}
