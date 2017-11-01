package com.congguangzi.master_cv.views._03_loadingView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author congguangzi (congspark@163.com) 2017/11/1.
 */

class LeafFactory {

    private static final int leafFloatTime = 2000;

    private int addTime;

    private static final int MAX_LEAFS = 8;
    private Random random = new Random();

    // 生成一个叶子信息
    private Leaf generateLeaf() {
        Leaf leaf = new Leaf();
        leaf.type = random.nextInt(3);
        leaf.rotateAngle = random.nextInt(360);
        leaf.rotateDirection = random.nextInt(2);
        // 为了产生交错的感觉，让开始的时间有一定的随机性
        addTime += random.nextInt((leafFloatTime * 2));
        leaf.startTime = System.currentTimeMillis() + addTime;
        return leaf;
    }

    List<Leaf> generateLeafs() {
        return generateLeafs(MAX_LEAFS);
    }

    private List<Leaf> generateLeafs(int leafSize) {
        List<Leaf> leafs = new ArrayList<>();
        for (int i = 0; i < leafSize; i++) {
            leafs.add(generateLeaf());
        }
        return leafs;
    }
}