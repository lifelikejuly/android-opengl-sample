package com.julyyu.learn.opengl.camera.camerax;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: yuhaocan
 * @CreateDate: 2020-02-19
 */

public class CameraView extends GLSurfaceView {


    public CameraView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);
//        this.setRenderer(cameraRender);
        this.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

}
