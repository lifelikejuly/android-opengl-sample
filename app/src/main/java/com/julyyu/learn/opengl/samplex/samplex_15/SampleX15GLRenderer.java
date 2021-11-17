package com.julyyu.learn.opengl.samplex.samplex_15;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

import com.julyyu.learn.opengl.MatrixState;
import com.julyyu.learn.opengl.R;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author julyyu
 * @date 2020-07-05.
 * description：
 */


public class SampleX15GLRenderer extends GLSurfaceView {


    private SceneRenderer mRenderer;//场景渲染器


    int textureId;//系统分配的纹理id
    int width = 0;
    int height = 0;
    public SampleX15GLRenderer(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);    //设置使用OPENGL ES3.0
        mRenderer = new SceneRenderer();    //创建场景渲染器
        setRenderer(mRenderer);                //设置渲染器
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染
    }


    private class SceneRenderer implements Renderer {
        SampleX15 texRect;//纹理三角形对象引用

        public void onDrawFrame(GL10 gl) {
            //清除深度缓冲与颜色缓冲
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            //绘制纹理三角形
            texRect.drawSelf(textureId,width,height);
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            SampleX15GLRenderer.this.width = width;
            SampleX15GLRenderer.this.height = height;
            //设置视窗大小及位置
            GLES30.glViewport(0, 0, width, height);
            //计算GLSurfaceView的宽高比
            float ratio = (float) width / height;
            //调用此方法计算产生透视投影矩阵
            MatrixState.setProjectFrustum(-ratio * 2, ratio * 2, -1 * 2, 1 * 2, 1, 30);
            //调用此方法产生摄像机9参数位置矩阵
            MatrixState.setCamera(0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
            GLES30.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
            //创建三角形对对象
            texRect = new SampleX15(SampleX15GLRenderer.this);
            //初始化纹理
            initTexture();
            //关闭背面剪裁
            GLES30.glDisable(GLES30.GL_CULL_FACE);
        }
    }

    public void initTexture()//textureId
    {
        //生成纹理ID
        int[] textures = new int[1];
        GLES30.glGenTextures
                (
                        1,          //产生的纹理id的数量
                        textures,   //纹理id的数组
                        0           //偏移量
                );
        textureId = textures[0];
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);
        //通过输入流加载图片===============begin===================
        InputStream is = this.getResources().openRawResource(R.raw.jay);
        Bitmap bitmapTmp;
        try {
            bitmapTmp = BitmapFactory.decodeStream(is);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        InputStream is2 = this.getResources().openRawResource(R.raw.tex11);
        Bitmap bitmapTmp2;
        try {
            bitmapTmp2 = BitmapFactory.decodeStream(is2);
        } finally {
            try {
                is2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //通过输入流加载图片===============end=====================

        //实际加载纹理进显存
        GLUtils.texImage2D
                (
                        GLES30.GL_TEXTURE_2D, //纹理类型
                        0,                      //纹理的层次，0表示基本图像层，可以理解为直接贴图
                        bitmapTmp,              //纹理图像
                        0                      //纹理边框尺寸
                );
//        GLUtils.texImage2D
//                (
//                        GLES30.TEXTURE_, //纹理类型
//                        0,                      //纹理的层次，0表示基本图像层，可以理解为直接贴图
//                        bitmapTmp2,              //纹理图像
//                        0                      //纹理边框尺寸
//                );
        bitmapTmp.recycle();          //纹理加载成功后释放内存中的纹理图
        bitmapTmp2.recycle();
    }

}
