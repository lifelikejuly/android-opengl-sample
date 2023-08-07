package com.julyyu.learn.opengl.demo.projectionmode;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Size;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 正交投影
 */

public class PerspectiveProjectionGLRenderer extends GLSurfaceView {

    SceneRenderer mRenderer;//自定义渲染器的引用
    PerspectiveProjection perspectiveProjection1;
    PerspectiveProjection perspectiveProjection2;
    Lines lines;

    private float mPreviousY;//上次的触控位置Y坐标
    private float mPreviousX;//上次的触控位置X坐标
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;//角度缩放比例



    public PerspectiveProjectionGLRenderer(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);
        mRenderer = new SceneRenderer();
        this.setRenderer(mRenderer);
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }


    //触摸事件回调方法
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();//获取此次触控的y坐标
        float x = e.getX();//获取此次触控的x坐标
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE://若为移动动作
                float dy = y - mPreviousY;//计算触控位置的Y位移
                float dx = x - mPreviousX;//计算触控位置的X位移
                perspectiveProjection1.yAngle += dx * TOUCH_SCALE_FACTOR;
                perspectiveProjection1.xAngle += dy * TOUCH_SCALE_FACTOR;

                perspectiveProjection2.yAngle += dx * TOUCH_SCALE_FACTOR;
                perspectiveProjection2.xAngle += dy * TOUCH_SCALE_FACTOR;

                lines.yAngle += dx * TOUCH_SCALE_FACTOR;
                lines.xAngle += dy * TOUCH_SCALE_FACTOR;
        }
        mPreviousY = y;//记录触控笔y坐标
        mPreviousX = x;//记录触控笔x坐标
        return true;
    }


    private class SceneRenderer implements Renderer {

        public void onDrawFrame(GL10 gl) {
            //清除深度缓冲与颜色缓冲
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            perspectiveProjection1.drawSelf();
            perspectiveProjection2.drawSelf();
            lines.drawSelf();
        }


        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
            GLES30.glClearColor(1, 1, 1, 1.0f); // 白色
            //创建三角形对对象
            perspectiveProjection1 = new PerspectiveProjection(PerspectiveProjectionGLRenderer.this,0);
            perspectiveProjection2 = new PerspectiveProjection(PerspectiveProjectionGLRenderer.this,-2f);
            lines = new Lines(PerspectiveProjectionGLRenderer.this,-2f);
//            lines.
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0, 0, width, height);
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            perspectiveProjection1.setSize(new Size(width,height));
            perspectiveProjection2.setSize(new Size(width,height));
            lines.setPSize(new Size(width,height));
        }

    }
}
