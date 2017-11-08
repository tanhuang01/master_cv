package com.congguangzi.master_cv.views._03_loading_view;

import java.util.Random;

/**
 * @author congguangzi (congspark@163.com) 2017/11/1.
 */

class Leaf {

    private static final int leafFloatTime = 2000;

    private static Random random = new Random();

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

    // 生成一个叶子信息
    public static Leaf generateLeaf() {
        Leaf leaf = new Leaf();
        leaf.type = random.nextInt(3);
        leaf.rotateAngle = random.nextInt(360);
        leaf.rotateDirection = random.nextInt(2);
        // 为了产生交错的感觉，让开始的时间有一定的随机性
        leaf.startTime = System.currentTimeMillis() + random.nextInt((leafFloatTime >>> 3));
        return leaf;
    }
}
