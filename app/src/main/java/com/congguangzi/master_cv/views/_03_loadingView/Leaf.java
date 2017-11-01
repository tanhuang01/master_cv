package com.congguangzi.master_cv.views._03_loadingView;

/**
 * @author congguangzi (congspark@163.com) 2017/11/1.
 */

class Leaf {

    // 叶子振幅.
    static final int BIG = 0;
    static final int NORMAL = 1;
    static final int SMALL = 2;

    /**
     * 叶子在界面上的位置.
     */
    float x, y;

    /**
     * 控制叶子飘动的幅度
     */
    int type;

    /**
     * 飘动过程中旋转角度
     */
    int rotateAngle;

    /**
     * 旋转方向 0-顺时针; 1-逆时针
     */
    int rotateDirection;

    /**
     * 起始时间
     */
    long startTime;
}
