package com.julyyu.learn.opengl;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.julyyu.learn.opengl.util.ShaderUtil;

import java.nio.FloatBuffer;

/**
 * @Author: yuhaocan
 * @CreateDate: 2020-02-26
 */
public abstract class BaseDrawSample {

    protected int mProgram;//自定义渲染管线着色器程序id
    protected int muMVPMatrixHandle;//总变换矩阵引用
    protected int maPositionHandle; //顶点位置属性引用
    protected int maColorHandle; //顶点颜色属性引用
    protected String mVertexShader;//顶点着色器代码脚本
    protected String mFragmentShader;//片元着色器代码脚本

    protected FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    protected FloatBuffer mColorBuffer;//顶点着色数据缓冲

    public float yAngle = 0;//绕y轴旋转的角度
    public float xAngle = 0;//绕x轴旋转的角度

    public BaseDrawSample(GLSurfaceView mv) {
        //初始化顶点坐标与着色数据
        initVertexData();
        //初始化shader
        initShader(mv);
    }




    //初始化shader
    public void initShader(GLSurfaceView mv) {
        //加载顶点着色器的脚本内容
        mVertexShader = ShaderUtil.loadFromAssetsFile("vertex.vsh", mv.getResources());
        //加载片元着色器的脚本内容
        mFragmentShader = ShaderUtil.loadFromAssetsFile("frag.fsh", mv.getResources());
        //基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //获取程序中顶点位置属性引用id
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //获取程序中顶点颜色属性引用id
        maColorHandle = GLES30.glGetAttribLocation(mProgram, "aColor");
        //获取程序中总变换矩阵引用id
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
    }
    static float[] mMMatrix = new float[16];
    public void drawSelf() {

        //制定使用某套shader程序
        GLES30.glUseProgram(mProgram);
        //初始化变换矩阵
        Matrix.setRotateM(mMMatrix, 0, 0, 0, 1, 0);
        //设置沿Z轴正向位移1
        Matrix.translateM(mMMatrix, 0, 0, 0, 1);
        //设置绕y轴旋转yAngle度
        Matrix.rotateM(mMMatrix, 0, yAngle, 0, 1, 0);
        //设置绕x轴旋转xAngle度
        Matrix.rotateM(mMMatrix, 0, xAngle, 1, 0, 0);
        //将最终变换矩阵传入shader程序
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(mMMatrix), 0);
        //为画笔指定顶点位置数据
        GLES30.glVertexAttribPointer
                (
                        maPositionHandle,
                        3,
                        GLES30.GL_FLOAT,
                        false,
                        3 * 4,
                        mVertexBuffer
                );
        //为画笔指定顶点着色数据
        GLES30.glVertexAttribPointer
                (
                        maColorHandle,
                        4,
                        GLES30.GL_FLOAT,
                        false,
                        4 * 4,
                        mColorBuffer
                );
        //允许顶点位置数据数组
        GLES30.glEnableVertexAttribArray(maPositionHandle);
        GLES30.glEnableVertexAttribArray(maColorHandle);
    }


    public abstract void initVertexData();

}
