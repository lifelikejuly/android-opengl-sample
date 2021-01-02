package com.julyyu.learn.opengl.samplex.samplex_2;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.julyyu.learn.opengl.util.BufferUtil;
import com.julyyu.learn.opengl.util.ShaderUtil;

import java.nio.FloatBuffer;

/**
 * @Author: yuhaocan
 * @CreateDate: 2020-02-25
 */
public class SampleX2 {

    int mProgram;//自定义渲染管线程序id
    int maPositionHandle; //顶点位置属性引用
    int maColorHandle; //顶点颜色属性引用
    String mVertexShader;//顶点着色器代码脚本
    String mFragmentShader;//片元着色器代码脚本
    FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    FloatBuffer mColorBuffer;//顶点着色数据缓冲
    FloatBuffer mColorWhiteBuffer;//顶点着色数据缓冲

    int vCount = 0;//顶点数量

    public SampleX2(GLSurfaceView mv) {
        //调用初始化顶点数据的initVertexData方法
        initVertexData();
        //调用初始化着色器的intShader方法
        initShader(mv);
    }

    //初始化着色器的方法
    public void initShader(GLSurfaceView mv) {
        //加载顶点着色器的脚本内容
        mVertexShader = ShaderUtil.loadFromAssetsFile("samplex2/vertex_samplex_2.vsh", mv.getResources());
        //加载片元着色器的脚本内容
        mFragmentShader = ShaderUtil.loadFromAssetsFile("samplex2/frag_samplex_2.fsh", mv.getResources());
        //基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //获取程序中顶点位置属性引用
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //获取程序中顶点颜色属性引用
        maColorHandle = GLES30.glGetAttribLocation(mProgram, "aColor");
    }

    public void initVertexData()//初始化顶点数据的方法
    {

        float vertices[] = new float[]{//顶点坐标数组

                -0.3f, 0.3f, 0,
                -0.6f, 0.3f, 0,
                -0.3f, 0.0f, 0,
                -0.6f, 0.0f, 0,

                -0.4f, -0.4f, 0,
                -0.7f, -0.4f, 0,
                -0.4f, -0.1f, 0,
                -0.7f, -0.1f, 0,

                0.3f, 0.3f, 0,
                0.6f, 0.3f, 0,
                0.3f, 0.0f, 0,
                0.6f, 0.0f, 0
        };
        mVertexBuffer = BufferUtil.creatFloatBuffer(vertices);


        float colors[] = new float[]{//顶点颜色数组
                1, 0, 0, 0,
                1, 0, 1, 0,
                1, 1, 0, 0,
                1, 1, 1, 0,

                1, 0, 0, 0,
                1, 0, 1, 0,
                1, 1, 0, 0,
                1, 1, 1, 0,

                1, 0, 0, 0,
                1, 0, 1, 0,
                1, 1, 0, 0,
                1, 1, 1, 0,
        };

        mColorBuffer = BufferUtil.creatFloatBuffer(colors);

        float colorWhites[] = new float[]{//顶点颜色数组
                1, 1, 1, 0,
                1, 1, 1, 0,
                1, 1, 1, 0,
                1, 1, 1, 0,
                1, 1, 1, 0,
                1, 1, 1, 0,
                1, 1, 1, 0,
                1, 1, 1, 0,
                1, 1, 1, 0,
                1, 1, 1, 0,
                1, 1, 1, 0,
                1, 1, 1, 0,
        };

        mColorWhiteBuffer = BufferUtil.creatFloatBuffer(colorWhites);
    }

    public void drawSelf() {
        //指定使用某套shader程序
        GLES30.glUseProgram(mProgram);
        //将顶点位置数据送入渲染管线
        GLES30.glVertexAttribPointer(
                maPositionHandle, 3,
                GLES30.GL_FLOAT, false,
                3 * 4, mVertexBuffer);
        //将顶点颜色数据送入渲染管线
        GLES30.glVertexAttribPointer(
                maColorHandle, 4,
                GLES30.GL_FLOAT, false,
                4 * 4, mColorBuffer);
        //启用顶点位置数据数组
        GLES30.glEnableVertexAttribArray(maPositionHandle);
        //启用顶点颜色数据数组
        GLES30.glEnableVertexAttribArray(maColorHandle);




        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 4);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 4, 4);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 8, 4);

        GLES30.glVertexAttribPointer(
                maColorHandle, 4,
                GLES30.GL_FLOAT, false,
                4 * 4, mColorWhiteBuffer);
        GLES30.glDrawArrays(GLES30.GL_POINTS, 0, 12);
    }
}
