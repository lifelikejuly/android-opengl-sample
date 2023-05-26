package com.julyyu.learn.opengl.demo.texturemap;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.Matrix;

import com.julyyu.learn.opengl.util.BuffetUtils;
import com.julyyu.learn.opengl.util.ShaderUtil;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

// 灰色效果滤镜
public class SceneGrayRenderer {


    Context context;

    FloatBuffer vertexBuffer, mTexVertexBuffer;
    ShortBuffer mVertexIndexBuffer;


    int mProgram;
    int textureId;//系统分配的纹理id

    //投影矩阵
    private final float[] mProjectMatrix = new float[16];
    //最终变换矩阵
    private final float[] mMVPMatrix = new float[16];

    //返回属性变量的位置
    //变换矩阵
    private int uMatrixLocation;
    //顶点
    private int aPositionLocation;
    //纹理
    private int aTextureLocation;

    /**
     * 顶点坐标
     * (x,y,z)
     * <p>
     * -1，1 （2）           1，1 （1）
     * <p>
     * 0，0（0）
     * <p>
     * -1，-1 （3）            1，-1（4）
     */
    private static final float[] POSITION_VERTEX = new float[]{
            0f, 0f, 0f,     //顶点坐标V0
            1f, 1f, 0f,     //顶点坐标V1
            -1f, 1f, 0f,    //顶点坐标V2
            -1f, -1f, 0f,   //顶点坐标V3
            1f, -1f, 0f     //顶点坐标V4
    };

    /**
     * 纹理坐标
     * (s,t)
     * 0，1	（3） 		1，1（4）
     * <p>
     * 0.5，0.5（0）
     * <p>
     * 0，0	（2）		1，0  （1）
     */
    private static final float[] TEX_VERTEX = {
            0.5f, 0.5f, //纹理坐标V0
            1f, 0f,     //纹理坐标V1
            0f, 0f,     //纹理坐标V2
            0f, 1.0f,   //纹理坐标V3
            1f, 1.0f    //纹理坐标V4
    };

    /**
     * 绘制顺序索引
     */
    private static final short[] VERTEX_INDEX = {
            0, 1, 2,  //V0,V1,V2 三个顶点组成一个三角形
            0, 2, 3,  //V0,V2,V3 三个顶点组成一个三角形
            0, 3, 4,  //V0,V3,V4 三个顶点组成一个三角形
            0, 4, 1   //V0,V4,V1 三个顶点组成一个三角形
    };

    public static String grayFsCode = "#version 300 es\n" +
            "precision mediump float;\n" +
            "uniform sampler2D uTextureUnit;\n" +
            "in vec2 vTexCoord;\n" +
            "out vec4 vFragColor;\n" +
            "const vec3 W = vec3(0.2125, 0.7154, 0.0721);\n" +
            "void main() {\n" +
            "  vec4 textureColor = texture(uTextureUnit,vTexCoord);\n" +
            "  float luminance = dot(textureColor.rgb, W);\n" +
            "  vFragColor = vec4(vec3(luminance), textureColor.a);\n" +
            "}\n";



    public SceneGrayRenderer(Context context) {
        this.context = context;
        // 顶点
        vertexBuffer = BuffetUtils.createFloatBuffer(POSITION_VERTEX, 4);
        vertexBuffer.position(0);
        // 纹理
        mTexVertexBuffer = BuffetUtils.createFloatBuffer(TEX_VERTEX, 4);
        mTexVertexBuffer.position(0);
        // 顶点坐标
        mVertexIndexBuffer = BuffetUtils.createShortBuffer(VERTEX_INDEX, 2);
        mVertexIndexBuffer.position(0);
    }

    public void onSurfaceCreated() {
        //设置屏幕背景色RGBA
        GLES30.glClearColor(0f, 0f, 0.5f, 1.0f);

        mProgram = ShaderUtil.createProgram(ShaderUtil.vsCode, grayFsCode);
        //在OpenGLES环境中使用程序
        GLES30.glUseProgram(mProgram);
        //获取属性位置
        uMatrixLocation = GLES30.glGetUniformLocation(mProgram, "u_Matrix");
        aPositionLocation = GLES30.glGetAttribLocation(mProgram, "vPosition");
        aTextureLocation = GLES30.glGetAttribLocation(mProgram, "aTextureCoord");
        GLES30.glUseProgram(0);
    }

    public void onSurfaceChanged(int width, int height) {
//        this.width = width;
//        this.height = height;
        //设置视窗大小及位置
//        GLES30.glViewport(0, 0, width, height);

    }

    public void onDrawFrame(int textureId) {

        GLES30.glUseProgram(mProgram);
        /// 为啥FBO上下是反的 bottom 和 top 需要翻转
        Matrix.orthoM(mProjectMatrix, 0, -1,1, 1,-1, 0, 10f);
        // 不需要加相机的方法
        Matrix.transposeM(mMVPMatrix, 0, mProjectMatrix, 0);
        //将变换矩阵传入顶点渲染器
        GLES30.glUniformMatrix4fv(uMatrixLocation, 1, false, mMVPMatrix, 0);

        //启用顶点坐标属性
        GLES30.glEnableVertexAttribArray(aPositionLocation);
        GLES30.glVertexAttribPointer(aPositionLocation, 3, GLES30.GL_FLOAT, false, 0, vertexBuffer);
        //启用纹理坐标属性
        GLES30.glEnableVertexAttribArray(aTextureLocation);
        GLES30.glVertexAttribPointer(aTextureLocation, 2, GLES30.GL_FLOAT, false, 0, mTexVertexBuffer);
        //激活纹理
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        //绑定纹理
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId);
        // 绘制
        GLES30.glDrawElements(GLES20.GL_TRIANGLES, VERTEX_INDEX.length, GLES20.GL_UNSIGNED_SHORT, mVertexIndexBuffer);

        //禁止顶点数组的句柄
        GLES30.glDisableVertexAttribArray(aPositionLocation);
        GLES30.glDisableVertexAttribArray(aTextureLocation);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);
        GLES30.glUseProgram(0);
    }


}