package com.julyyu.learn.opengl.camera.camerax;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLES30;
import android.view.TextureView;

/**
 * @author julyyu
 * @date 2021/1/31.
 * description：
 */
class GLCameraXRender implements  Runnable, TextureView.SurfaceTextureListener, SurfaceTexture.OnFrameAvailableListener {

    private static final int DRAW_INTERVAL = 1000 / 30;

    private Thread renderThread;
    private Context context;
    private int gwidth, gheight;

    private SurfaceTexture cameraSurfaceTexture;
    private int cameraTextureId;

    private GLRenderContent glRenderContent;
    private GLCameraXFilter glCameraXFilter;

    private TextureView textureView;

    private float[] transformMatrix = new float[16];


    public GLCameraXRender(Context context, SurfaceTexture surfaceTexture,TextureView textureView) {
        this.context = context;
        this.glRenderContent = new GLRenderContent();
        this.glCameraXFilter = new GLCameraXFilter(context);
        this.cameraSurfaceTexture = surfaceTexture;
        this.textureView = textureView;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        if (renderThread != null && renderThread.isAlive()) {
            renderThread.interrupt();
        }
        renderThread = new Thread(this);
        gwidth = -width;
        gheight = -height;

        renderThread.start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        gwidth = -width;
        gheight = -height;

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public void run() {
        cameraTextureId = GLRenderContent.genTexture();
        glRenderContent.initGL(textureView);
        glCameraXFilter.init();
        cameraSurfaceTexture.attachToGLContext(cameraTextureId);
        cameraSurfaceTexture.setOnFrameAvailableListener(GLCameraXRender.this);

//        while (!Thread.currentThread().isInterrupted()) {
//            try {
//                if (gwidth < 0 && gheight < 0)
//                    GLES30.glViewport(0, 0, gwidth = -gwidth, gheight = -gheight);
//
//                GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
//
//                // Update the camera preview texture
//                synchronized (this) {
//                    cameraSurfaceTexture.updateTexImage();
//                }
//
//                // Draw camera preview
//                glCameraXFilter.drawSelf(cameraTextureId, gwidth, gheight);
//
//                // Flush
//                GLES30.glFlush();
//               glRenderContent.eglSwapBuffers();
//
//                Thread.sleep(DRAW_INTERVAL);
//
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }
////        cameraSurfaceTexture.release();
//        GLES30.glDeleteTextures(1, new int[]{cameraTextureId}, 0);
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        if (cameraSurfaceTexture != null) {
            cameraSurfaceTexture.updateTexImage();
            cameraSurfaceTexture.getTransformMatrix(transformMatrix);
        }
        glRenderContent.makeCurrent();
        glCameraXFilter.drawFrame(cameraTextureId,transformMatrix);
        glRenderContent.eglSwapBuffers();
    }
}
