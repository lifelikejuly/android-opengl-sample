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
public class SampleX2Circle {


    int mProgram;//自定义渲染管线程序id
    int maPositionHandle; //顶点位置属性引用
    int maColorHandle; //顶点颜色属性引用
    String mVertexShader;//顶点着色器代码脚本
    String mFragmentShader;//片元着色器代码脚本
    FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    FloatBuffer mColorBuffer;//顶点着色数据缓冲

    int vCount = 0;

    public SampleX2Circle(GLSurfaceView mv) {

        //调用初始化顶点数据的initVertexData方法
        initVertexData();
        //调用初始化着色器的intShader方法
        initShader(mv);
    }

    //初始化着色器的方法
    public void initShader(GLSurfaceView mv) {
        //加载顶点着色器的脚本内容
        mVertexShader = ShaderUtil.loadFromAssetsFile("vertex_samplex_1.vsh", mv.getResources());
        //加载片元着色器的脚本内容
        mFragmentShader = ShaderUtil.loadFromAssetsFile("frag_samplex_1.fsh", mv.getResources());
        //基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //获取程序中顶点位置属性引用
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //获取程序中顶点颜色属性引用
        maColorHandle = GLES30.glGetAttribLocation(mProgram, "aColor");
    }

    public void initVertexData()//初始化顶点数据的方法
    {

        //顶点坐标数据的初始化================begin============================
        int n = 60;
        vCount = n + 2;

        float angdegSpan = 360.0f / n;
        float[] vertices = new float[vCount * 3];//顶点坐标数据数组
        //坐标数据初始化
        int count = 0;
        //第一个顶点的坐标
        vertices[count++] = 0;
        vertices[count++] = 0;
        vertices[count++] = 0;
        for (int angdeg = 0; Math.ceil(angdeg) <= 360; angdeg += angdegSpan) {//循环生成其他顶点的坐标
            double angrad = Math.toRadians(angdeg);//当前弧度
            //当前点
            vertices[count++] = (float) (-0.1 * Math.sin(angrad));//顶点x坐标
            vertices[count++] = (float) (0.2 * Math.cos(angrad));//顶点y坐标
            vertices[count++] = 0;//顶点z坐标
        }


        mVertexBuffer = BufferUtil.creatFloatBuffer(vertices);


        count = 0;
        float colors[] = new float[vCount * 4];
        //第一个顶点的颜色:白色
        colors[count++] = 1;
        colors[count++] = 1;
        colors[count++] = 1;
        colors[count++] = 0;
        //剩余顶点的颜色:绿色
        for (int i = 4; i < colors.length; i += 4) {
            colors[count++] = 0;
            colors[count++] = 1;
            colors[count++] = 0;
            colors[count++] = 0;
        }

        mColorBuffer = BufferUtil.creatFloatBuffer(colors);

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

        GLES30.glLineWidth(10);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 0, vCount);

    }

}
