package com.julyyu.learn.opengl.demo.matrix;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Size;
import android.view.MotionEvent;
import android.view.View;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 矩阵应用
 */

public class ChangeMatrixSquareGLRenderer extends GLSurfaceView implements View.OnTouchListener {

    SceneRenderer mRenderer;//自定义渲染器的引用
    ChangeMatrixSquare square;

    private float oldDistance;//刚按下时双指之间的距离
    private float newDistance;//在屏幕上滑动后双指之间的距离
    private float scale = 1f;//缩放比

    public ChangeMatrixSquareGLRenderer(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);
        mRenderer = new SceneRenderer();
        this.setRenderer(mRenderer);
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        setOnTouchListener(this);
    }


    private float calculateDistance(MotionEvent motionEvent) {
        float x1 = motionEvent.getX(0);//第一个点x坐标
        float x2 = motionEvent.getX(1);//第二个点x坐标
        float y1 = motionEvent.getY(0);//第一个点y坐标
        float y2 = motionEvent.getY(1);//第二个点y坐标
        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//

//        }


//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                if (event.getX() <= (getWidth() / 2 + 100) && (event.getX() >= getWidth() / 2 - 100) &&
//                        event.getY() <= (getHeight() / 2 + 100) && (event.getY() >= getHeight() / 2 - 100)) {
//                    square.rotate();
//                } else if (event.getX() <= 300) {
//                    square.left();
//                } else if (event.getX() >= getWidth() - 300) {
//                    square.right();
//                } else if (event.getY() <= 300) {
//                    square.top();
//                } else if (event.getY() >= getHeight() - 300) {
//                    square.bottom();
//                }
//                break;
//        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getPointerCount() == 2) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_POINTER_DOWN:
                    if (event.getPointerCount() == 2) {//getPointerCount返回的是手指的数量
                        oldDistance = calculateDistance(event);//计算距离
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (event.getPointerCount() == 2) {
                        newDistance = calculateDistance(event);
                        scale += (newDistance - oldDistance) / oldDistance;
                        square.setScale(scale);
                    }
                    break;

            }
        } else {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (event.getX() <= (getWidth() / 2 + 100) && (event.getX() >= getWidth() / 2 - 100) &&
                            event.getY() <= (getHeight() / 2 + 100) && (event.getY() >= getHeight() / 2 - 100)) {
                        square.rotate();
                    } else if (event.getX() <= 300) {
                        square.left();
                    } else if (event.getX() >= getWidth() - 300) {
                        square.right();
                    } else if (event.getY() <= 300) {
                        square.top();
                    } else if (event.getY() >= getHeight() - 300) {
                        square.bottom();
                    }
                    break;
            }
        }
        return true;
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
            square = new ChangeMatrixSquare(ChangeMatrixSquareGLRenderer.this);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES30.glViewport(0, 0, width, height);
            square.setSize(new Size(width, height));
        }

    }
}
