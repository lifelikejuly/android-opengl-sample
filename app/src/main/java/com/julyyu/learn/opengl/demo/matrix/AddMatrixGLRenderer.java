package com.julyyu.learn.opengl.demo.matrix;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 矩阵应用
 */

public class AddMatrixGLRenderer extends GLSurfaceView {

    SceneRenderer mRenderer;//自定义渲染器的引用
    AddMatrix surface;

    public AddMatrixGLRenderer(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);
        mRenderer = new SceneRenderer();
        this.setRenderer(mRenderer);
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }


    private class SceneRenderer implements Renderer {

        public void onDrawFrame(GL10 gl) {
            //清除深度缓冲与颜色缓冲
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            for (int i = 0; i< 5; i++){
                surface.drawSelf(1f - 0.1f * i,1f - 0.1f * i,0);
            }
        }


        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
            GLES30.glClearColor(1, 1, 1, 1.0f); // 白色
            //创建三角形对对象
            surface = new AddMatrix(AddMatrixGLRenderer.this);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0, 0, width, height);
        }

    }
}
