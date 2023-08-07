package com.julyyu.learn.opengl.demo.pointlinesurface;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.julyyu.learn.opengl.util.BufferUtil;
import com.julyyu.learn.opengl.util.ShaderUtil;

import java.nio.FloatBuffer;

/**
 * @Author: yuhaocan
 * @CreateDate: 2020-02-19
 */
/// 线段
public class PointLine {

    int mProgram;//自定义渲染管线程序id
    int maPositionHandle; //顶点位置属性引用
    int maColorHandle; //顶点颜色属性引用
    String mVertexShader;//顶点着色器代码脚本
    String mFragmentShader;//片元着色器代码脚本
    FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    FloatBuffer mColorBuffer;//顶点着色数据缓冲

    public PointLine(GLSurfaceView mv) {
        //调用初始化顶点数据的initVertexData方法
        initVertexData();
        //调用初始化着色器的intShader方法
        initShader(mv);
    }

    public void initVertexData()//初始化顶点数据的方法
    {
        float vertices[] = new float[]{//顶点坐标数组

                -0.8f, 0.4f, 0, //2
                -0.7f, 0.4f, 0,
                -0.8f, 0.3f, 0,
                -0.7f, 0.3f, 0,


                -0.6f, 0.4f, 0,//3
                -0.5f, 0.4f, 0,
                -0.6f, 0.3f, 0,
                -0.5f, 0.3f, 0,

                -0.4f, 0.4f, 0,//4
                -0.3f, 0.4f, 0,
                -0.4f, 0.3f, 0,
                -0.3f, 0.3f, 0,

                -0.2f, 0.4f, 0,//5
                -0.1f, 0.4f, 0,
                -0.2f, 0.3f, 0,
                -0.1f, 0.3f, 0,

                0.0f, 0.4f, 0,//6
                0.1f, 0.4f, 0,
                0.0f, 0.3f, 0,
                0.1f, 0.3f, 0,

                0.2f, 0.4f, 0,//7
                0.3f, 0.4f, 0,
                0.2f, 0.3f, 0,
                0.3f, 0.3f, 0,

                -0.85f, -0.4f, 0,//8
                -0.9f, -0.3f, 0,
                -0.8f,  -0.3f, 0,
                -1.0f, -0.4f, 0,
                -0.7f,  -0.4f, 0,
                -0.9f, -0.5f, 0,
                -0.8f,  -0.5f, 0,

        };
        mVertexBuffer = BufferUtil.creatFloatBuffer(vertices);

        float colors[] = new float[]{//顶点颜色数组

                1, 0, 0, 0,//2 // 红
                0, 1, 0, 0, // 绿
                0, 0, 1, 0, // 蓝
                1, 0, 1, 0, // 紫

                1, 0, 0, 0,//3 // 红
                0, 1, 0, 0, // 绿
                0, 0, 1, 0, // 蓝
                1, 0, 1, 0, // 紫

                1, 0, 0, 0,//4// 红
                0, 1, 0, 0, // 绿
                0, 0, 1, 0, // 蓝
                1, 0, 1, 0, // 紫

                1, 0, 0, 0,//5// 红
                0, 1, 0, 0, // 绿
                0, 0, 1, 0, // 蓝
                1, 0, 1, 0, // 紫

                1, 0, 0, 0,//6// 红
                0, 1, 0, 0, // 绿
                0, 0, 1, 0, // 蓝
                1, 0, 1, 0, // 紫

                1, 0, 0, 0,//7// 红
                0, 1, 0, 0, // 绿
                0, 0, 1, 0, // 蓝
                1, 0, 1, 0, // 紫

                0, 0, 0, 0,//8
                1, 0, 0, 0,// 红
                0, 1, 0, 0, // 绿
                0, 0, 1, 0, // 蓝
                1, 0, 1, 0, // 紫
                0, 1, 0, 0,// 绿
                0, 0, 1, 0,// 蓝
        };

        mColorBuffer = BufferUtil.creatFloatBuffer(colors);
    }

    //初始化着色器的方法
    public void initShader(GLSurfaceView mv) {
        //加载顶点着色器的脚本内容
        mVertexShader = ShaderUtil.loadFromAssetsFile("samplexs/samplex1/vertex_samplex_1.vsh", mv.getResources());
        //加载片元着色器的脚本内容
        mFragmentShader = ShaderUtil.loadFromAssetsFile("samplexs/samplex1/frag_samplex_1.fsh", mv.getResources());
        //基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //获取程序中顶点位置属性引用
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //获取程序中顶点颜色属性引用
        maColorHandle = GLES30.glGetAttribLocation(mProgram, "aColor");
        GLES30.glLineWidth(5);//设置线的宽度
    }

    //glVertexAttribPointer
    // index代表句柄
    // size代表 几个值为一组
    // type代表类型
    // stride代表 每组数据的间隔大小 GL_FLOAT一个就是4
    // ptr代表数据源
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

        // 四个点 2
        GLES30.glDrawArrays(GLES30.GL_POINTS, 0, 4);
        // 两条线
        GLES30.glDrawArrays(GLES30.GL_LINES, 0, 4);

        // 四个点 3
        GLES30.glDrawArrays(GLES30.GL_POINTS, 4, 4);
        // 两条线
        GLES30.glDrawArrays(GLES30.GL_LINE_STRIP, 4, 4);

        // 四个点 4
        GLES30.glDrawArrays(GLES30.GL_POINTS, 8, 4);
        // 两条线
        GLES30.glDrawArrays(GLES30.GL_LINE_LOOP, 8, 4);

//        // 四个点 5
//        GLES30.glDrawArrays(GLES30.GL_POINTS, 12, 4);
//        // 两条线
//        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 12, 4);
//
//        // 四个点 6
//        GLES30.glDrawArrays(GLES30.GL_POINTS, 16, 4);
//        // 两条线
//        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 16, 4);
//
//        // 四个点 7
//        GLES30.glDrawArrays(GLES30.GL_POINTS, 20, 4);
//        // 两条线
//        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 20, 4);
//
//        // 四个点 8
//        GLES30.glDrawArrays(GLES30.GL_POINTS, 24, 7);
//        // 两条线
//        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 24, 7);

    }
}
