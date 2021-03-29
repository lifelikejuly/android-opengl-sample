//package com.julyyu.learn.opengl.camera.camerax;
//
//import android.graphics.SurfaceTexture;
//import android.opengl.GLES20;
//import android.opengl.GLSurfaceView;
//
//import androidx.camera.core.Preview;
//import androidx.lifecycle.LifecycleOwner;
//
//import javax.microedition.khronos.egl.EGLConfig;
//import javax.microedition.khronos.opengles.GL10;
//
///**
// * @author julyyu
// * @date 2021-01-20.
// * description：
// */
//public class CameraRender implements GLSurfaceView.Renderer, Preview.OnPreviewOutputUpdateListener, SurfaceTexture.OnFrameAvailableListener {
//
//
//    private CameraView cameraView;
//
//    private SurfaceTexture surfaceTexture;
//
//    private int textureId;
//
//
//    float[] mtx = new float[16];
//
//    private CameraFilter cameraFilter;
//
//    public CameraRender(CameraView cameraView) {
//        this.cameraView = cameraView;
//        LifecycleOwner lifecycleOwner = (LifecycleOwner) cameraView.getContext();
//        CameraHelper.init(lifecycleOwner, this);
//
//    }
//
//    @Override
//    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//        surfaceTexture.attachToGLContext(textureId);
//        surfaceTexture.setOnFrameAvailableListener(this);
//
//        GLES20.glClearColor(0, 0, 0, 0);
//        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
//
//        cameraFilter = new CameraFilter(cameraView);
//    }
//
//    @Override
//    public void onSurfaceChanged(GL10 gl, int width, int height) {
//        GLES20.glViewport(0, 0, width, height);
//
//    }
//
//
//    @Override
//    public void onDrawFrame(GL10 gl) {
//        surfaceTexture.updateTexImage();
////        cameraFilter.onDraw(mtx, textureId);
//    }
//
//    @Override
//    public void onUpdated(Preview.PreviewOutput output) {
//        surfaceTexture = output.getSurfaceTexture();
//    }
//
//    @Override
//    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
//        cameraView.requestRender();
//    }
//}
