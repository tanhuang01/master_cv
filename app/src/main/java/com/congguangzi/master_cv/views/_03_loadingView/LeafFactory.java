package com.congguangzi.master_cv.views._03_loadingView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author congguangzi (congspark@163.com) 2017/10/9.
 */

public class LeafFactory {
    private static final int MAX_LEAF = 8;
    static final long LEAF_FLOAT_TIME = 3000;
    static final long LEAF_ROTATE_TIME = 2000;

    private long leafFloatTime = LEAF_FLOAT_TIME;

    Random random = new Random();

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    // 最大数量产生叶子信息
    public List<Leaf> generateLeafs() {
        return generateLeafs(MAX_LEAF);
    }

    // 传入的数量产生叶子信息
    public List<Leaf> generateLeafs(int leafSize) {
        int size = leafSize > MAX_LEAF ? MAX_LEAF : leafSize;
        List<Leaf> leafs = new ArrayList<>(leafSize);
        for (int i = 0; i < size; i++) {
            leafs.add(generateLeaf());
        }
        return leafs;
    }

    // 一个叶子信息
    public Leaf generateLeaf() {
        Leaf leaf = new Leaf.Builder()
                .setType(getType())
                .setRotateAngle(random.nextInt(360))
                .setRotateDirection(getRotateDirection())
                .setStartTime(getStartTime())
                .build();
        return leaf;
    }

    private Leaf.StartType getType() {
        int swingType = random.nextInt(3);
        Leaf.StartType type = Leaf.StartType.MIDDLE;
        switch (swingType) {
            case 0:
                type = Leaf.StartType.MIDDLE;
                break;
            case 1:
                type = Leaf.StartType.LITTLE;
                break;
            case 2:
                type = Leaf.StartType.BIG;
                break;
        }
        return type;
    }

    private Leaf.RotateDirection getRotateDirection() {
        int rotateDirection = random.nextInt(2);
        Leaf.RotateDirection direction = Leaf.RotateDirection.CLOCKWISE;
        switch (rotateDirection) {
            case 0:
                direction = Leaf.RotateDirection.CLOCKWISE;
                break;
            case 1:
                direction = Leaf.RotateDirection.ANTICLOCKWISE;
                break;
        }
        return direction;
    }

    private long getStartTime() {
        leafFloatTime = leafFloatTime < 0 ? LEAF_FLOAT_TIME : leafFloatTime;
        return System.currentTimeMillis() + random.nextInt((int) leafFloatTime * 2);
    }

}
