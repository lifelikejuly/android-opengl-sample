package com.julyyu.learn.opengl.camera;

import android.app.Activity;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: JulyYu
 * @CreateDate: 2020-03-09
 */
public class CameraGLSurfaceRenderer extends GLSurfaceView implements GLSurfaceView.Renderer {


    private CameraDrawer cameraDrawer;
    public SurfaceTexture surfaceTexture;
    private int[] textureId = new int[1];
    private int mRatioWidth = 0;
    private int mRatioHeight = 0;
    private SurfaceTextureListener surfaceTextureListener;

//    private Handler mainHandler;
//    private Handler childHandler;

    public CameraGLSurfaceRenderer(Context context) {
        this(context,null);
    }

    public CameraGLSurfaceRenderer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setEGLContextClientVersion(3);
        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    //    private void initHandler() {
//        HandlerThread handlerThread = new HandlerThread("Camera2");
//        handlerThread.start();
//        mainHandler = new Handler(getMainLooper());//主线程Handler
//        childHandler = new Handler(handlerThread.getLooper());//子线程Handler
//    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        cameraDrawer = new CameraDrawer(getContext());
        //创建纹理对象
        GLES30.glGenTextures(textureId.length, textureId, 0);
        //将纹理对象绑定到srufaceTexture
        surfaceTexture = new SurfaceTexture(textureId[0]);        //创建并连接程序
//        initHandler();
        if(surfaceTextureListener != null){
            ((Activity)getContext()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    surfaceTextureListener.onSurfaceTextureAvailable();
                }
            });
        }

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        surfaceTexture.updateTexImage();
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
        cameraDrawer.draw(textureId[0]);
    }

    /**
     * Sets the aspect ratio for this view. The size of the view will be measured based on the ratio
     * calculated from the parameters. Note that the actual sizes of parameters don't matter, that
     * is, calling setAspectRatio(2, 3) and setAspectRatio(4, 6) make the same result.
     *
     * @param width  Relative horizontal size
     * @param height Relative vertical size
     */
    public void setAspectRatio(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        mRatioWidth = width;
        mRatioHeight = height;
        ((Activity)getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                requestLayout();
            }
        });
//
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (0 == mRatioWidth || 0 == mRatioHeight) {
            setMeasuredDimension(width, height);
        } else {
            if (width < height * mRatioWidth / mRatioHeight) {
                setMeasuredDimension(width, width * mRatioHeight / mRatioWidth);
            } else {
                setMeasuredDimension(height * mRatioWidth / mRatioHeight, height);
            }
        }
    }


    public SurfaceTexture getSurfaceTexture() {
        return surfaceTexture;
    }

    public boolean isAvailable() {
        return surfaceTexture != null;
    }

    public interface SurfaceTextureListener {
        void onSurfaceTextureAvailable();
    }

    public SurfaceTextureListener getSurfaceTextureListener() {
        return surfaceTextureListener;
    }

    public void setSurfaceTextureListener(SurfaceTextureListener surfaceTextureListener) {
        this.surfaceTextureListener = surfaceTextureListener;
    }
}

