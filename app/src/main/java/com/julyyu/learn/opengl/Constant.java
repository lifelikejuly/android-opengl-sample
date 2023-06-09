package com.julyyu.learn.opengl;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

/**
 * @Author: yuhaocan
 * @CreateDate: 2020-02-27
 */
public class Constant {

    public static int SCREEN_WIDTH = 800;
    public static int SCREEN_HEIGHT = 480;


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


    public static float WIDTH;
    public static float HEIGHT;


    public static float[][] yArray;//存储地形顶点高度的数组
    public static float LAND_HIGH_ADJUST = -2f;//陆地的高度调整值
    public static float LAND_HIGHEST = 20f;//陆地最大高差
    public static int colsPlusOne;//灰度图的列数
    public static int rowsPlusOne;//灰度图的行数

    //从灰度图片中加载陆地上每个顶点的高度
    public static float[][] loadLandforms(Resources resources, int index) {
        Bitmap bt = BitmapFactory.decodeResource(resources, index);//导入灰度图
        int colsPlusOne = bt.getWidth(); //获得存储地形顶点高度数组的列数
        int rowsPlusOne = bt.getHeight(); //获得存储地形顶点高度数组的行数
        float[][] result = new float[rowsPlusOne][colsPlusOne];//创建存储地形顶点高度的数组
        for (int i = 0; i < rowsPlusOne; i++)//对灰度图像素行遍历
        {
            for (int j = 0; j < colsPlusOne; j++)//对灰度图像素列遍历
            {
                int color = bt.getPixel(j, i);//获得指定行列处像素的颜色
                //获得该像素红、绿、蓝三个色彩通道的值
                int r = Color.red(color);
                int g = Color.green(color);
                int b = Color.blue(color);
                int h = (r + g + b) / 3;//3个色彩通道求平均
                result[i][j] = h * LAND_HIGHEST / 255 + LAND_HIGH_ADJUST;  //按公式计算顶点海拔
            }
        }
        return result;//返回存储地形顶点高度的数组
    }
}
