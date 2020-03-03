package com.julyyu.learn.opengl;

import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * @Author: yuhaocan
 * @CreateDate: 2020-02-16
 */
public class MatrixState {
    private static float[] mProjMatrix = new float[16];//4x4矩阵 投影用
    private static float[] mVMatrix = new float[16];//摄像机位置朝向9参数矩阵
    private static float[] currMatrix;//当前变换矩阵
    private static float[] mMVPMatrix = new float[16];//最终的总变换矩阵
    public static float[] lightLocation = new float[]{0, 0, 0};//光源位置数组
    public static FloatBuffer lightPositionFB;

    public static float[] lightDirection = new float[]{0, 0, 1};//定向光光源方向
    public static FloatBuffer lightDirectionFB;
    public static FloatBuffer cameraFB;    //摄像机位置数据缓冲

    static float[][] mStack = new float[10][16];//用于保存变换矩阵的栈
    static int stackTop = -1;//栈顶索引

    public static float[] lightLocationSun=new float[]{0,0,0};//太阳定位光光源位置
    public static FloatBuffer lightPositionFBSun;


    //设置摄像机
    static ByteBuffer llbb = ByteBuffer.allocateDirect(3 * 4);
    static float[] cameraLocation = new float[3];//摄像机位置

    //设置摄像机的方法
    public static void setCamera(
            float cx,
            float cy,
            float cz,
            float tx,
            float ty,
            float tz,
            float upx,
            float upy,
            float upz
    ) {
        Matrix.setLookAtM
                (
                        mVMatrix,    //存储生成矩阵元素的float[]类型数组
                        0,            //填充起始偏移量
                        cx, cy, cz,    //摄像机位置的X、Y、Z坐标
                        tx, ty, tz,    //观察目标点X、Y、Z坐标
                        upx, upy, upz    //up向量在X、Y、Z轴上的分量
                );

        cameraLocation[0] = cx;
        cameraLocation[1] = cy;
        cameraLocation[2] = cz;

        llbb.clear();//清除摄像机位置缓冲
        llbb.order(ByteOrder.nativeOrder());//设置字节顺序
        cameraFB = llbb.asFloatBuffer();//转换为float型缓冲
        cameraFB.put(cameraLocation);//将摄像机位置放入缓冲
        cameraFB.position(0);  //设置缓冲的起始位置
    }

    //设置正交投影的方法
    public static void setProjectOrtho(
            float left,
            float right,
            float bottom,
            float top,
            float near,
            float far
    ) {
        Matrix.orthoM(
                mProjMatrix,    //存储生成矩阵元素的float[]类型数组
                0,                //填充起始偏移量
                left, right,    //near面的left、right
                bottom, top,    //near面的bottom、top
                near, far        //near面、far面与视点的距离
        );
    }


    //设置透视投影
    public static void setProjectFrustum(
            float left, // near面的left
            float right, // near面的right
            float bottom, // near面的bottom
            float top, // near面的top
            float near, // near面与视点的距离
            float far // far面与视点的距离
    ) {
        Matrix.frustumM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }


    ////////////////////////////////////////////////////////////////////
    //产生无任何变换的初始矩阵
    public static void setInitStack() {
        currMatrix = new float[16];
        Matrix.setRotateM(currMatrix, 0, 0, 1, 0, 0);
    }

    //将当前变换矩阵存入栈中
    public static void pushMatrix() {
        stackTop++;//栈顶索引加1
        for (int i = 0; i < 16; i++) {
            mStack[stackTop][i] = currMatrix[i];//当前变换矩阵中的各元素入栈
        }
    }

    //从栈顶取出变换矩阵
    public static void popMatrix() {
        for (int i = 0; i < 16; i++) {
            currMatrix[i] = mStack[stackTop][i];//栈顶矩阵元素进当前变换矩阵
        }
        stackTop--;//栈顶索引减1
    }

    //沿X、Y、Z轴方向进行平移变换的方法
    public static void translate(float x, float y, float z) {
        Matrix.translateM(currMatrix, 0, x, y, z);
    }

    //沿X、Y、Z轴方向进行旋转变换的方法
    public static void rotate(float angle, float x, float y, float z) {// 设置绕xyz轴移动
        Matrix.rotateM(currMatrix, 0, angle, x, y, z);
    }

    //沿X、Y、Z轴方向进行缩放变换的方法
    public static void scale(float x, float y, float z) {
        Matrix.scaleM(currMatrix, 0, x, y, z);
    }

    //获取具体物体的总变换矩阵
    public static float[] getFinalMatrix(float[] spec)//生成物体总变换矩阵的方法
    {
        mMVPMatrix = new float[16];//创建用来存放最终变换矩阵的数组
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, spec, 0); //将摄像机矩阵乘以变换矩阵
        //将投影矩阵乘以上一步的结果矩阵得到最终变换矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
        return mMVPMatrix;
    }

    //获取具体物体的总变换矩阵
    public static float[] getFinalMatrix()//计算产生总变换矩阵的方法
    {
        //摄像机矩阵乘以变换矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mVMatrix, 0, currMatrix, 0);
        //投影矩阵乘以上一步的结果矩阵
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, mMVPMatrix, 0);
        return mMVPMatrix;
    }

    //获取具体物体的变换矩阵
    public static float[] getMMatrix() {
        return currMatrix;
    }
    ////////////////////////////////////

    //设置灯光位置的方法
    static ByteBuffer llbbL = ByteBuffer.allocateDirect(3 * 4);

    public static void setLightLocation(float x, float y, float z) {
        llbbL.clear();

        lightLocation[0] = x;
        lightLocation[1] = y;
        lightLocation[2] = z;

        llbbL.order(ByteOrder.nativeOrder());//设置字节顺序
        lightPositionFB = llbbL.asFloatBuffer();
        lightPositionFB.put(lightLocation);
        lightPositionFB.position(0);
    }

    //设置灯光方向的方法
    public static void setLightDirection(float x, float y, float z) {
        llbbL.clear();

        lightDirection[0] = x;
        lightDirection[1] = y;
        lightDirection[2] = z;
        llbbL.order(ByteOrder.nativeOrder());//设置字节顺序
        lightDirectionFB = llbbL.asFloatBuffer();
        lightDirectionFB.put(lightDirection);
        lightDirectionFB.position(0);
    }

    //设置太阳光源位置的方法
    public static void setLightLocationSun(float x,float y,float z)
    {
        lightLocationSun[0]=x;
        lightLocationSun[1]=y;
        lightLocationSun[2]=z;
        ByteBuffer llbb = ByteBuffer.allocateDirect(3*4);
        llbb.order(ByteOrder.nativeOrder());//设置字节顺序
        lightPositionFBSun=llbb.asFloatBuffer();
        lightPositionFBSun.put(lightLocationSun);
        lightPositionFBSun.position(0);
    }

}
