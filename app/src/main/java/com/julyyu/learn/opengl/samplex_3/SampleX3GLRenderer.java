package com.julyyu.learn.opengl.samplex_3;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.julyyu.learn.opengl.MatrixState;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: yuhaocan
 * @CreateDate: 2020-02-26
 */
public class SampleX3GLRenderer extends GLSurfaceView {

    private Renderer renderer;
    private float mPreviousY;//上次的触控位置Y坐标
    private float mPreviousX;//上次的触控位置X坐标
    private SampleX3 sampleX3;

    public SampleX3GLRenderer(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);
        renderer = new SceneRenderer();
        this.setRenderer(renderer);
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;//角度缩放比例

    //触摸事件回调方法
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();//获取此次触控的y坐标
        float x = e.getX();//获取此次触控的x坐标
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE://若为移动动作
                float dy = y - mPreviousY;//计算触控位置的Y位移
                float dx = x - mPreviousX;//计算触控位置的X位移
                sampleX3.yAngle += dx * TOUCH_SCALE_FACTOR;
                sampleX3.xAngle += dy * TOUCH_SCALE_FACTOR;
        }
        mPreviousY = y;//记录触控笔y坐标
        mPreviousX = x;//记录触控笔x坐标
        return true;
    }


    private class SceneRenderer implements GLSurfaceView.Renderer {


        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            sampleX3 = new SampleX3(SampleX3GLRenderer.this);
            //打开深度检测
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0, 0, width, height);
            //计算视口的宽高比
            Constant.ratio = (float) width / height;
            // 调用此方法计算产生投影矩阵
            MatrixState.setProjectOrtho(-Constant.ratio, Constant.ratio, -1, 1, -10, 10);
            // 调用此方法产生摄像机矩阵
            MatrixState.setCamera(0f, 0f, 1f,
                    0f, 0f, 0f,
                    0f, 1.0f, 0f);
            //初始化变换矩阵
            MatrixState.setInitStack();
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            //保护现场
            MatrixState.pushMatrix();
            MatrixState.pushMatrix();//保护现场
            sampleX3.drawSelf();
            MatrixState.popMatrix();//恢复现场
            MatrixState.popMatrix();
            //恢复现场
        }
    }
}
