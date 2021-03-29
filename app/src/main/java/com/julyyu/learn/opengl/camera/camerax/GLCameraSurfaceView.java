package com.julyyu.learn.opengl.camera.camerax;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.TextureView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;

/**
 * @author julyyu
 * @date 2021-01-22.
 * description：
 */
public class GLCameraSurfaceView extends GLSurfaceView {


    CameraRender cameraRender;

//    int textureId;
    int[] textures = new int[1];
    public GLCameraSurfaceView(Context context,SurfaceTexture surfaceTexture) {
        this(context,null,surfaceTexture);
    }

    public GLCameraSurfaceView(Context context, AttributeSet attrs,SurfaceTexture surfaceTexture) {
        super(context, attrs);
        this.setEGLContextClientVersion(3);
        this.cameraRender = new CameraRender(surfaceTexture);
        this.setRenderer(cameraRender);
        this.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }


    public class CameraRender implements GLSurfaceView.Renderer {
        private SurfaceTexture surfaceTexture;
        private GLCameraX glCameraX;

        public CameraRender(SurfaceTexture surfaceTexture) {
            this.surfaceTexture = surfaceTexture;
            glCameraX = new GLCameraX(GLCameraSurfaceView.this);
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES30.glGenTextures(textures.length, textures, 0);
            surfaceTexture.attachToGLContext(textures[0]);
            surfaceTexture.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() {
                @Override
                public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                    requestRender();
                }
            });
            GLES30.glClearColor(0f, 0f, 0f, 0.0f);
            GLES30.glClear(GL_COLOR_BUFFER_BIT);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0, 0, width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            surfaceTexture.updateTexImage();
            glCameraX.drawSelf(textures[0]);
        }

    }

}



