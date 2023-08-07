package com.julyyu.learn.opengl.demo.matrix;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.julyyu.learn.opengl.util.BufferUtil;
import com.julyyu.learn.opengl.util.ShaderUtil;

import java.nio.FloatBuffer;

/**
 */
/// 矩阵 + 面
public class AddMatrix {

    int mProgram;//自定义渲染管线程序id
    int maPositionHandle; //顶点位置属性引用
    int maColorHandle; //顶点颜色属性引用
    int uMatrixHandle;//矩阵变化
    String mVertexShader;//顶点着色器代码脚本
    String mFragmentShader;//片元着色器代码脚本
    FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    FloatBuffer mColorBuffer;//顶点着色数据缓冲

    final float[] mMVPMatrix = new float[16];

    public AddMatrix(GLSurfaceView mv) {
        //调用初始化顶点数据的initVertexData方法
        initVertexData();
        //调用初始化着色器的intShader方法
        initShader(mv);
    }

    public void initVertexData()//初始化顶点数据的方法
    {
        // 四个点坐标
        float vertices[] = new float[]{//顶点坐标数组
                -1.0f, 1.0f, 0, //1
                1.0f, 1.0f, 0,
                -1.0f, -1.0f, 0,
                1.0f, -1.0f, 0,

        };
        mVertexBuffer = BufferUtil.creatFloatBuffer(vertices);
        // 四个点颜色
        float colors[] = new float[]{//顶点颜色数组
                1, 0, 0, 0,// // 红
                0, 1, 0, 0,     // 绿
                0, 0, 1, 0,     // 蓝
                1, 0, 1, 0,     // 紫

        };

        mColorBuffer = BufferUtil.creatFloatBuffer(colors);
    }

    //初始化着色器的方法
    public void initShader(GLSurfaceView mv) {
        //加载顶点着色器的脚本内容
        mVertexShader = ShaderUtil.loadFromAssetsFile("demo/vertex_with_matrix.vsh", mv.getResources());
        //加载片元着色器的脚本内容
        mFragmentShader = ShaderUtil.loadFromAssetsFile("demo/frag_with_matrix.fsh", mv.getResources());
        //基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //获取程序中顶点位置属性引用
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //获取程序中顶点颜色属性引用
        maColorHandle = GLES30.glGetAttribLocation(mProgram, "aColor");
        uMatrixHandle = GLES30.glGetUniformLocation(mProgram, "u_Matrix");
    }

    //glVertexAttribPointer
    // index代表句柄
    // size代表 几个值为一组
    // type代表类型
    // stride代表 每组数据的间隔大小 GL_FLOAT一个就是4
    // ptr代表数据源
    public void drawSelf(float x,float y ,float z) {
        //指定使用某套shader程序
        GLES30.glUseProgram(mProgram);

        Matrix.orthoM(mMVPMatrix, 0, -1, 1, -1, 1, 0, -1f);
//        Matrix.transposeM(mMVPMatrix, 0,  mProjectMatrix, 0);
        Matrix.scaleM(mMVPMatrix,0,x,y,z);
        GLES30.glUniformMatrix4fv(uMatrixHandle, 1, false, mMVPMatrix, 0);


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
        // 五个点 1
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4);


//        public static final int GL_POINTS                                  = 0x0000;
//        public static final int GL_LINES                                   = 0x0001;
//        public static final int GL_LINE_LOOP                               = 0x0002;
//        public static final int GL_LINE_STRIP                              = 0x0003;
//        public static final int GL_TRIANGLES                               = 0x0004;
//        public static final int GL_TRIANGLE_STRIP                          = 0x0005;
//        public static final int GL_TRIANGLE_FAN                            = 0x0006;

//        GLES30.glDrawArrays(GLES30.GL_, 0, 5);
//        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 5);


    }
}
