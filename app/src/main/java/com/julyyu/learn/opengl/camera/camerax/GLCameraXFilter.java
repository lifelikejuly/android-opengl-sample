package com.julyyu.learn.opengl.camera.camerax;

import android.content.Context;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLES30;

import com.julyyu.learn.opengl.util.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * @author julyyu
 * @date 2021/1/31.
 * description：
 */
class GLCameraXFilter {

    private static final int BUF_ACTIVE_TEX_UNIT = GLES20.GL_TEXTURE8;


    int mProgram;//自定义渲染管线程序id
    int muMVPMatrixHandle;//总变换矩阵引用
    int maPositionHandle; //顶点位置属性引用
    int maTexCoorHandle; //顶点纹理坐标属性引用
    String mVertexShader;//顶点着色器代码脚本
    String mFragmentShader;//片元着色器代码脚本

    FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    FloatBuffer mTexCoorBuffer;//顶点纹理坐标数据缓冲

    int mFilterProgram;


    Context context;

    private static GLRenderBuffer CAMERA_RENDER_BUF;

    public GLCameraXFilter(Context context) {
        this.context = context;

    }

    public void init(){
        //创建顶点坐标数据缓冲
        //vertices.length*4是因为一个整数四个字节

        float vertices[] = new float[]
                {
                        1.0f, -1.0f,
                        -1.0f, -1.0f,
                        1.0f, 1.0f,
                        -1.0f, 1.0f,
                };

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
                        1.0f, 0.0f,
                        0.0f, 0.0f,
                        1.0f, 1.0f,
                        0.0f, 1.0f,
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

        //加载顶点着色器的脚本内容
        mVertexShader = ShaderUtil.loadFromAssetsFile("camera/camera_vertext.vsh", context.getResources());
        //加载片元着色器的脚本内容
        mFragmentShader = ShaderUtil.loadFromAssetsFile("camera/camera_original_rtt.fsh", context.getResources());
        //基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //指定使用某套shader程序
        GLES30.glUseProgram(mProgram);
    }

    //初始化顶点数据的方法
    public void initVertexData() {
        //顶点坐标数据的初始化================begin============================

        //创建顶点坐标数据缓冲
        //vertices.length*4是因为一个整数四个字节

        float vertices[] = new float[]
                {
                        1.0f, -1.0f,
                        -1.0f, -1.0f,
                        1.0f, 1.0f,
                        -1.0f, 1.0f,
                };

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
                        1.0f, 0.0f,
                        0.0f, 0.0f,
                        1.0f, 1.0f,
                        0.0f, 1.0f,
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
    public void initShader() {
        //加载顶点着色器的脚本内容
        mVertexShader = ShaderUtil.loadFromAssetsFile("camera/camera_vertext.vsh", context.getResources());
        //加载片元着色器的脚本内容
        mFragmentShader = ShaderUtil.loadFromAssetsFile("camera/camera_original_rtt.fsh", context.getResources());
        //基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //指定使用某套shader程序
        GLES30.glUseProgram(mProgram);
        //获取程序中顶点位置属性引用
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //获取程序中顶点纹理坐标属性引用
        maTexCoorHandle = GLES30.glGetAttribLocation(mProgram, "aTexCoor");


        //加载顶点着色器的脚本内容
        String mFilterVertexShader = ShaderUtil.loadFromAssetsFile("camera/filter_vertext.vsh", context.getResources());
        //加载片元着色器的脚本内容
        String mFilterFragmentShader = ShaderUtil.loadFromAssetsFile("camera/filter_original.fsh", context.getResources());
        //基于顶点着色器与片元着色器创建程序
        mFilterProgram = ShaderUtil.createProgram(mFilterVertexShader, mFilterFragmentShader);
        //指定使用某套shader程序
        GLES30.glUseProgram(mFilterProgram);

    }

    public void drawFrame(int texId,float[] floats){
        int iChannel0Location = GLES20.glGetUniformLocation(mProgram, "iChannel0");
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texId);
        GLES20.glUniform1i(iChannel0Location, 0);

        int vPositionLocation = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(vPositionLocation);
        GLES20.glVertexAttribPointer(vPositionLocation, 2, GLES20.GL_FLOAT, false, 4 * 2, mVertexBuffer);

        int vTexCoordLocation = GLES20.glGetAttribLocation(mProgram, "vTexCoord");
        GLES20.glEnableVertexAttribArray(vTexCoordLocation);
        GLES20.glVertexAttribPointer(vTexCoordLocation, 2, GLES20.GL_FLOAT, false, 4 * 2, mTexCoorBuffer);


        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texId);
        GLES20.glUniform1i(iChannel0Location, 0);


        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);


    }

    public void drawSelf(int texId,int canvasWidth, int canvasHeight) {

        if (CAMERA_RENDER_BUF == null ||
                CAMERA_RENDER_BUF.getWidth() != canvasWidth ||
                CAMERA_RENDER_BUF.getHeight() != canvasHeight) {
            CAMERA_RENDER_BUF = new GLRenderBuffer(canvasWidth, canvasHeight, BUF_ACTIVE_TEX_UNIT);
        }

        GLES20.glUseProgram(mProgram);

        int iChannel0Location = GLES20.glGetUniformLocation(mProgram, "iChannel0");
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, texId);
        GLES20.glUniform1i(iChannel0Location, 0);

        int vPositionLocation = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(vPositionLocation);
        GLES20.glVertexAttribPointer(vPositionLocation, 2, GLES20.GL_FLOAT, false, 4 * 2, mVertexBuffer);

        int vTexCoordLocation = GLES20.glGetAttribLocation(mProgram, "vTexCoord");
        GLES20.glEnableVertexAttribArray(vTexCoordLocation);
        GLES20.glVertexAttribPointer(vTexCoordLocation, 2, GLES20.GL_FLOAT, false, 4 * 2, mTexCoorBuffer);

        CAMERA_RENDER_BUF.bind();
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        CAMERA_RENDER_BUF.unbind();
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);


        // Filter
        GLES20.glUseProgram(mFilterProgram);

        int vPositionLocation2 = GLES20.glGetAttribLocation(mFilterProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(vPositionLocation2);
        GLES20.glVertexAttribPointer(vPositionLocation2, 2, GLES20.GL_FLOAT, false, 4 * 2, mVertexBuffer);

        int vTexCoordLocation2 = GLES20.glGetAttribLocation(mFilterProgram, "vTexCoord");
        GLES20.glEnableVertexAttribArray(vTexCoordLocation2);
        GLES20.glVertexAttribPointer(vTexCoordLocation2, 2, GLES20.GL_FLOAT, false, 4 * 2, mTexCoorBuffer);

        int textureId = CAMERA_RENDER_BUF.getTexId();
        int sTextureLocation = GLES20.glGetUniformLocation(mFilterProgram, "iChannel0");
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glUniform1i(sTextureLocation, 0);



        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
    }
}
