package com.julyyu.learn.opengl.demo.matrix;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Size;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 矩阵应用
 */

public class RotateMatrixSquareGLRenderer extends GLSurfaceView {

    SceneRenderer mRenderer;//自定义渲染器的引用
    RotateMatrixSquare square;

    public RotateMatrixSquareGLRenderer(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);
        mRenderer = new SceneRenderer();
        this.setRenderer(mRenderer);
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                square.rotate();
                break;
        }
        return super.onTouchEvent(event);
    }

    private class SceneRenderer implements Renderer {

        public void onDrawFrame(GL10 gl) {
            //清除深度缓冲与颜色缓冲
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            square.drawSelf();
        }


        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
            GLES30.glClearColor(1, 1, 1, 1.0f); // 白色
            //创建三角形对对象
            square = new RotateMatrixSquare(RotateMatrixSquareGLRenderer.this);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0, 0, width, height);
            square.setSize(new Size(width,height));
        }

    }
}
