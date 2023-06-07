package com.julyyu.learn.opengl.samplex.samplex_13;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.julyyu.learn.opengl.MatrixState;
import com.julyyu.learn.opengl.util.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * @Author: JulyYu
 * @CreateDate: 2020-03-06
 * 2D图片展示
 */
public class SampleX13 {

    int mProgram;//自定义渲染管线程序id
    int muMVPMatrixHandle;//总变换矩阵引用
    int maPositionHandle; //顶点位置属性引用
    int maTexCoorHandle; //顶点纹理坐标属性引用
    String mVertexShader;//顶点着色器代码脚本
    String mFragmentShader;//片元着色器代码脚本

    FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    FloatBuffer mTexCoorBuffer;//顶点纹理坐标数据缓冲
    int vCount = 0;

    final long START_TIME = System.currentTimeMillis();
    int iFrame = 0;


    public SampleX13(GLSurfaceView mv) {
        //初始化顶点数据的方法
        initVertexData();
        //初始化着色器的方法
        initShader(mv);
    }

    //初始化顶点数据的方法
    public void initVertexData() {
        //顶点坐标数据的初始化================begin============================
        vCount = 6;
        float vertices[] = new float[]
                {
                        -1, 1, 0,
                        -1, -1, 0,
                        1, -1, 0,

                        -1, 1, 0,
                        1, 1, 0,
                        1, -1, 0,
                };
        //创建顶点坐标数据缓冲
        //vertices.length*4是因为一个整数四个字节
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mVertexBuffer = vbb.asFloatBuffer();//转换为Float型缓冲
        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点坐标数据的初始化================end============================

        //顶点纹理坐标数据的初始化================begin============================
        float texCoor[] = new float[]//顶点颜色值数组，每个顶点4个色彩值RGBA
                {
                        0, 0,
                        0, 1,
                        1, 1,

                        0, 0,
                        1, 0,
                        1, 1,

                };
        //创建顶点纹理坐标数据缓冲
        ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length * 4);
        cbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mTexCoorBuffer = cbb.asFloatBuffer();//转换为Float型缓冲
        mTexCoorBuffer.put(texCoor);//向缓冲区中放入顶点纹理数据
        mTexCoorBuffer.position(0);//设置缓冲区起始位置
        //特别提示：由于不同平台字节顺序不同数据单元不是字节的一定要经过ByteBuffer
        //转换，关键是要通过ByteOrder设置nativeOrder()，否则有可能会出问题
        //顶点纹理坐标数据的初始化================end============================

    }

    //初始化着色器
    public void initShader(GLSurfaceView mv) {
        //加载顶点着色器的脚本内容
        mVertexShader = ShaderUtil.loadFromAssetsFile("samplexs/samplex13/vertex_samplex13.vsh", mv.getResources());
        //加载片元着色器的脚本内容
        mFragmentShader = ShaderUtil.loadFromAssetsFile("samplexs/samplex13/frag_samplex13.fsh", mv.getResources());
        //基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //获取程序中顶点位置属性引用
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //获取程序中顶点纹理坐标属性引用
        maTexCoorHandle = GLES30.glGetAttribLocation(mProgram, "aTexCoor");
        //获取程序中总变换矩阵引用
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
    }

    public void drawSelf(int texId,int width,int height) {
        //指定使用某套shader程序
        GLES30.glUseProgram(mProgram);

        MatrixState.setInitStack();
        //将最终变换矩阵传入渲染管线
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
        //将顶点位置数据传送进渲染管线
        GLES30.glVertexAttribPointer
                (
                        maPositionHandle,
                        3,
                        GLES30.GL_FLOAT,
                        false,
                        3 * 4,
                        mVertexBuffer
                );
        //将顶点纹理坐标数据传送进渲染管线
        GLES30.glVertexAttribPointer
                (
                        maTexCoorHandle,
                        2,
                        GLES30.GL_FLOAT,
                        false,
                        2 * 4,
                        mTexCoorBuffer
                );
        int iResolutionLocation = GLES30.glGetUniformLocation(mProgram, "iResolution");
        GLES30.glUniform3fv(iResolutionLocation, 1,
                FloatBuffer.wrap(new float[]{(float) width, (float) height, 1.0f}));

        float time = ((float) (System.currentTimeMillis() - START_TIME)) / 1000.0f;
        int iGlobalTimeLocation = GLES30.glGetUniformLocation(mProgram, "iGlobalTime");
        GLES30.glUniform1f(iGlobalTimeLocation, time);

        int iFrameLocation = GLES30.glGetUniformLocation(mProgram, "iFrame");
        GLES30.glUniform1i(iFrameLocation, iFrame);


        //允许顶点位置数据数组
        GLES30.glEnableVertexAttribArray(maPositionHandle);//启用顶点位置数据
        GLES30.glEnableVertexAttribArray(maTexCoorHandle);//启用顶点纹理坐标数据
        //绑定纹理
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);//设置使用的纹理编号
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);//绑定指定的纹理id
        //以三角形的方式填充
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
        iFrame++;

    }
}
