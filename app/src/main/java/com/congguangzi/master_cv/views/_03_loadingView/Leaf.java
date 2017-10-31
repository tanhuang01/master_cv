package com.congguangzi.master_cv.views._03_loadingView;

/**
 *
 *
 * @author congguangzi (congspark@163.com) 2017/10/9.
 */

public class Leaf {

    // 叶子飘入振幅
    enum StartType {
        LITTLE, MIDDLE, BIG,
    }

    enum RotateDirection {
        CLOCKWISE, ANTICLOCKWISE;
    }
    // 位置
    float x, y;

    // 飘入震动幅度
    StartType type;

    // 旋转角度
    int rotateAngle;

    // 旋转方向
    RotateDirection rotateDirection;

    // 起始时间
    long startTime;

    public Leaf(Builder builder) {
        x = builder.x;
        y = builder.y;
        type = builder.type;
        rotateAngle = builder.rotateAngle;
        rotateDirection = builder.rotateDirection;
        startTime = builder.startTime;
    }

    public static final class Builder {
        float x, y;
        StartType type;
        int rotateAngle;
        RotateDirection rotateDirection;
        long startTime;

        public Builder() {
        }

        public Builder setX(float x) {
            this.x = x;
            return this;
        }

        public Builder setY(float y) {
            this.y = y;
            return this;
        }

        public Builder setType(StartType type) {
            this.type = type;
            return this;
        }

        public Builder setRotateAngle(int rotateAngle) {
            this.rotateAngle = rotateAngle;
            return this;
        }

        public Builder setRotateDirection(RotateDirection rotateDirection) {
            this.rotateDirection = rotateDirection;
            return this;
        }

        public Builder setStartTime(long startTime) {
            this.startTime = startTime;
            return this;
        }

        public Leaf build() {
            return new Leaf(this);
        }
    }

}
