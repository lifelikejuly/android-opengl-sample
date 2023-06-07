package com.julyyu.learn.opengl.samplex.samplex_3;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.julyyu.learn.opengl.MatrixState;
import com.julyyu.learn.opengl.util.BufferUtil;
import com.julyyu.learn.opengl.util.ShaderUtil;

import java.nio.FloatBuffer;

/**
 * @Author: yuhaocan
 * @CreateDate: 2020-02-26
 */
/// 正方形
public class SampleX3 {

    int mProgram;//自定义渲染管线着色器程序id
    int muMVPMatrixHandle;//总变换矩阵引用
    int maPositionHandle; //顶点位置属性引用
    int maColorHandle; //顶点颜色属性引用
    String mVertexShader;//顶点着色器代码脚本
    String mFragmentShader;//片元着色器代码脚本

    FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    FloatBuffer mColorBuffer;//顶点着色数据缓冲

    float yAngle = 0;//绕y轴旋转的角度
    float xAngle = 0;//绕x轴旋转的角度

    public SampleX3(GLSurfaceView mv) {
        //初始化顶点坐标与着色数据
        initVertexData();
        //初始化shader
        initShader(mv);
    }

    //默认的x，y坐标系长度不同所以比例相同但实际计算的长度不同导致三角形非正常的等边三角
    public void initVertexData() {


        float vertices[] = new float[]{

                // ver
                -1, 1, 0,
                0, 1, 0,
                0, 1, 0,
                1, 1, 0,

                -1, 0, 0,
                0, 0, 0,
                0, 0, 0,
                1, 0, 0,

                -1, -1, 0,
                0, -1, 0,
                0, -1, 0,
                1, -1, 0,


                // hor
                -1, 1, 0,
                -1, 0, 0,
                -1, 0, 0,
                -1, -1, 0,


                0, 1, 0,
                0, 0, 0,
                0, 0, 0,
                0, -1, 0,


                1, 1, 0,
                1, 0, 0,
                1, 0, 0,
                1, -1, 0,

        };

        float[] vertices2 = new float[vertices.length];
        System.arraycopy(vertices, 0, vertices2, 0, vertices.length);
        for (int i = 0; i < vertices2.length / 3; i++) {
            vertices2[i * 3 + 2] = 1f;
        }

        float[] vertices3 = new float[vertices.length];
        System.arraycopy(vertices, 0, vertices3, 0, vertices.length);
        for (int i = 0; i < vertices3.length / 3; i++) {
            vertices3[i * 3 + 2] = -1f;
        }


        float[] zVertices = {

                -1, 1, 1,
                -1, 1, 0,
                -1, 1, 0,
                -1, 1, -1,

                0, 1, 1,
                0, 1, 0,
                0, 1, 0,
                0, 1, -1,

                1, 1, 1,
                1, 1, 0,
                1, 1, 0,
                1, 1, -1,
        };

        float[] zVertices2 = new float[zVertices.length];
        System.arraycopy(zVertices, 0, zVertices2, 0, zVertices.length);
        for (int i = 0; i < zVertices2.length / 3; i++) {
            zVertices2[i * 3 + 1] = 0f;
        }

        float[] zVertices3 = new float[zVertices.length];
        System.arraycopy(zVertices, 0, zVertices3, 0, zVertices.length);
        for (int i = 0; i < zVertices3.length / 3; i++) {
            zVertices3[i * 3 + 1] = -1f;
        }

        float[] allVertices = new float[vertices.length * 3 + zVertices.length * 3];
        System.arraycopy(vertices, 0, allVertices, 0, vertices.length);
        System.arraycopy(vertices2, 0, allVertices, vertices.length, vertices2.length);
        System.arraycopy(vertices3, 0, allVertices, vertices.length + vertices2.length, vertices3.length);

        System.arraycopy(zVertices, 0, allVertices, vertices.length + vertices2.length + vertices3.length, zVertices.length);
        System.arraycopy(zVertices2, 0, allVertices, vertices.length + vertices2.length + vertices3.length + zVertices.length, zVertices2.length);
        System.arraycopy(zVertices3, 0, allVertices, vertices.length + vertices2.length + vertices3.length + zVertices.length + zVertices2.length, zVertices3.length);


        for (int i = 0; i < allVertices.length; i++) {
            allVertices[i] = allVertices[i] * 0.6f;
        }

        mVertexBuffer = BufferUtil.creatFloatBuffer(allVertices);

        float white[] = {
                1, 1, 1, 0
        };

        float colors[] = new float[allVertices.length * 4 / 3];//顶点颜色数组
        for (int i = 0; i < allVertices.length / 3; i++) {
            System.arraycopy(white, 0, colors, i * 4, white.length);
        }

        mColorBuffer = BufferUtil.creatFloatBuffer(colors);

    }

    //初始化shader
    public void initShader(GLSurfaceView mv) {
        //加载顶点着色器的脚本内容
        mVertexShader = ShaderUtil.loadFromAssetsFile("samplexs/samplex3/vertex_samplex3.vsh", mv.getResources());
        //加载片元着色器的脚本内容
        mFragmentShader = ShaderUtil.loadFromAssetsFile("samplexs/samplex3/frag_samplex3.fsh", mv.getResources());
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

        //将最终变换矩阵传入渲染管线
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
        GLES30.glLineWidth(10);
        GLES30.glDrawArrays(GLES30.GL_LINES, 0, 12);
        GLES30.glDrawArrays(GLES30.GL_LINES, 12, 12);

        GLES30.glDrawArrays(GLES30.GL_LINES, 24, 12);
        GLES30.glDrawArrays(GLES30.GL_LINES, 36, 12);

        GLES30.glDrawArrays(GLES30.GL_LINES, 48, 12);
        GLES30.glDrawArrays(GLES30.GL_LINES, 60, 12);

        GLES30.glDrawArrays(GLES30.GL_LINES, 72, 12);
        GLES30.glDrawArrays(GLES30.GL_LINES, 84, 12);
        GLES30.glDrawArrays(GLES30.GL_LINES, 96, 12);

    }
}
