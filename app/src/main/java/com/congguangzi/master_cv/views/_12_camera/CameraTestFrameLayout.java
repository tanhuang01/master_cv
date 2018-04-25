package com.congguangzi.master_cv.views._12_camera;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.congguangzi.master_cv.R;

/**
 * brief:
 *
 * @author congguangzi (congspark@163.com) 2018/4/16.
 */
public class CameraTestFrameLayout extends FrameLayout {

    Button button;
    RadioGroup group;
    CameraTestView cameraTestView;

    public CameraTestFrameLayout(@NonNull Context context) {
        super(context);
    }

    public CameraTestFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraTestFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        button = findViewById(R.id.bt_start);
        group = findViewById(R.id.rg_camera_coordinate);
        cameraTestView = findViewById(R.id.camera);

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraTestView.startAnimator();

                int[] loc = new int[2];
                button.getLocationInWindow(loc);
                Toast.makeText(getContext(), "x= " + loc[0] + " ,y= " + loc[1]
                        , Toast.LENGTH_SHORT).show();
            }
        });

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_x:
                        cameraTestView.setCur_coordinate(CameraTestView.X);
                        break;

                    case R.id.rb_y:
                        cameraTestView.setCur_coordinate(CameraTestView.Y);
                        break;

                    case R.id.rb_z:
                        cameraTestView.setCur_coordinate(CameraTestView.Z);
                        break;
                }
            }
        });
    }
}
