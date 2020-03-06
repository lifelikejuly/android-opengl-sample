package com.julyyu.learn.opengl;

/**
 * @Author: yuhaocan
 * @CreateDate: 2020-02-27
 */
public class Constant {

    //单位尺寸
    public static float UNIT_SIZE = 1f;
    //计算GLSurfaceView的宽高比
    public static float ratio;

    //旋转地月系线程的工作标志位
    public static boolean threadFlag = true;

    //绘制方式
    public static final int GL_POINTS = 0;
    public static final int GL_LINES = 1;
    public static final int GL_LINE_STRIP = 2;
    public static final int GL_LINE_LOOP = 3;

    public static int CURR_DRAW_MODE = 0;//当前绘制方式


    //棍的边长
    public static final float LENGTH = 2f;
    //顶点球球半径
    public static final float BALL_R = 0.6f;
    //圆柱半径
    public static final float R = 0.05f;
    //切分角度
    public static final float ANGLE_SPAN = 18;
    //黄金长方形大小的缩放比
    public static final float TRIANGLE_SCALE = 1;
    //黄金长方形的长的一半
    public static final float TRIANGLE_AHALF = 2f;
    //每个二十面体切分的分数
    public static final int SPLIT_COUNT = 3;

    public static float DATA_RATIO = 0.025f;//数据点的缩放比
}
