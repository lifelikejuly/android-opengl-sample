package com.julyyu.learn.opengl.samplex.samplex_1;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: yuhaocan
 * @CreateDate: 2020-02-19
 */

public class SampleX1GLRenderer extends GLSurfaceView {

    SceneRenderer mRenderer;//自定义渲染器的引用
    SampleX1 sampleX1;

    public SampleX1GLRenderer(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);
        mRenderer = new SceneRenderer();
        this.setRenderer(mRenderer);
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }


    private class SceneRenderer implements GLSurfaceView.Renderer {

        public void onDrawFrame(GL10 gl) {
            //清除深度缓冲与颜色缓冲
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            sampleX1.drawSelf();
        }


        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
            GLES30.glClearColor(0, 0, 0, 1.0f);
            //创建三角形对对象
            sampleX1 = new SampleX1(SampleX1GLRenderer.this);
            //打开深度检测
//            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视窗大小及位置
            GLES30.glViewport(0, 0, width, height);
            //计算GLSurfaceView的宽高比
//            float ratio = (float) width / height;
//            调用此方法计算产生透视投影矩阵
//            Matrix.frustumM(Triangle.mProjMatrix, 0, -ratio, ratio, -1, 1, 1, 10);
//            调用此方法产生摄像机9参数位置矩阵
//            Matrix.setLookAtM(Triangle.mVMatrix, 0, 0, 0, 3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        }

    }
}
