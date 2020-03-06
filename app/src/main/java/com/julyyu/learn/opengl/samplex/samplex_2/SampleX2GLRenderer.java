package com.julyyu.learn.opengl.samplex.samplex_2;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.julyyu.learn.opengl.Constant;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: yuhaocan
 * @CreateDate: 2020-02-25
 */
public class SampleX2GLRenderer extends GLSurfaceView {


    private SampleX2 samplex2;
    private SampleX2Circle sampleX2Circle;
    private SceneRenderer sceneRenderer;

    public SampleX2GLRenderer(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);
        sceneRenderer = new SceneRenderer();
        setRenderer(sceneRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }


    public class SceneRenderer implements GLSurfaceView.Renderer {


        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
            GLES30.glClearColor(0, 0, 0, 1.0f);
            samplex2 = new SampleX2(SampleX2GLRenderer.this);
            sampleX2Circle = new SampleX2Circle(SampleX2GLRenderer.this);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视口的大小及位置
            GLES30.glViewport(0, 0, width, height);
            //计算视口的宽高比
            Constant.ratio = (float) width / height;
            if (Constant.ratio > 1) {
                Constant.ratio = (float) height / width;
            }

        }

        @Override
        public void onDrawFrame(GL10 gl) {
            //清除深度缓冲与颜色缓冲
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            sampleX2Circle.drawSelf();
            samplex2.drawSelf();
        }
    }
}
