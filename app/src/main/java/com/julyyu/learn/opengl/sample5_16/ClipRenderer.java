package com.julyyu.learn.opengl.sample5_16;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import com.julyyu.learn.opengl.Constant;
import com.julyyu.learn.opengl.MatrixState;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: JulyYu
 * @CreateDate: 2020-02-28
 */
public class ClipRenderer extends GLSurfaceView {


    private SceneRenderer mRenderer;//场景渲染器

    public ClipRenderer(Context context) {
        super(context);
        this.setEGLContextClientVersion(3); //设置使用OPENGL ES3.0
        mRenderer = new SceneRenderer();    //创建场景渲染器
        setRenderer(mRenderer);                //设置渲染器
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染

    }

    int num = 0;


    boolean cullFaceFlag = false;//是否开启背面剪裁的标志位

    //设置是否开启背面剪裁的标志位
    public void setCullFace(boolean flag) {
        cullFaceFlag = flag;
    }

    boolean cwCcwFlag = false;//是否打开自定义卷绕的标志位

    //设置是否打开自定义卷绕的标志位
    public void setCwOrCcw(boolean flag) {
        cwCcwFlag = flag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (num >= 4) {
                num = 0;
            }
            Log.i("onTouchEvent", "num " + num);
            cullFaceFlag = ((num & 0b10) == 2);
            cwCcwFlag = ((num & 0b01) == 1);
            Log.i("onTouchEvent", "cullFaceFlag " + cullFaceFlag + " cwCcwFlag " + cwCcwFlag);
            num = num + 1;
        }
        return true;
    }

    private class SceneRenderer implements GLSurfaceView.Renderer {
        TrianglePair tp;//三角形对对象引用

        public void onDrawFrame(GL10 gl) {
            if (cullFaceFlag) {                                    //判断是否要打开背面剪裁
                GLES30.glEnable(GLES30.GL_CULL_FACE);            //打开背面剪裁
            } else {
                GLES30.glDisable(GLES30.GL_CULL_FACE);            //关闭背面剪裁
            }
            if (cwCcwFlag) {                                    //判断是否需要打开顺时针卷绕
                GLES30.glFrontFace(GLES30.GL_CCW);            //使用逆时针卷绕
            } else {
                GLES30.glFrontFace(GLES30.GL_CW);                //使用顺时针卷绕
            }
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);//清除深度缓冲与颜色缓冲
            MatrixState.pushMatrix();                                //保护现场
            MatrixState.translate(0, -1.4f, 0);                        //沿y轴负方向平移
            tp.drawSelf();                                        //绘制三角形对
            MatrixState.popMatrix();                                //恢复现场
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视口的大小及位置
            GLES30.glViewport(0, 0, width, height);
            //计算视口的宽高比
            Constant.ratio = (float) width / height;
            // 调用此方法计算产生透视投影矩阵
            MatrixState.setProjectFrustum(-Constant.ratio, Constant.ratio, -1, 1, 10, 100);
            // 调用此方法产生摄像机矩阵
            MatrixState.setCamera(0, 0f, 20, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

            //初始化变换矩阵
            MatrixState.setInitStack();
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
            GLES30.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
            //创建三角形对对象
            tp = new TrianglePair(ClipRenderer.this);
            //打开深度检测
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Constant.UNIT_SIZE = 0.25f;
    }

    @Override
    public void onPause() {
        super.onPause();
        Constant.UNIT_SIZE = 1f;
    }
}
