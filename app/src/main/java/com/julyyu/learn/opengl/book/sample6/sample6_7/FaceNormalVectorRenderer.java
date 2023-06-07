package com.julyyu.learn.opengl.book.sample6.sample6_7;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.julyyu.learn.opengl.Constant;
import com.julyyu.learn.opengl.MatrixState;
import com.julyyu.learn.opengl.book.sample6.Sample6Renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: JulyYu
 * @CreateDate: 2020-03-01
 */
public class FaceNormalVectorRenderer extends Sample6Renderer {

    private SceneRenderer mRenderer;//场景渲染器
    Cube cube;//立方体

    public FaceNormalVectorRenderer(Context context) {
        super(context);
        this.setEGLContextClientVersion(3); //设置使用OPENGL ES3.0
        mRenderer = new SceneRenderer();    //创建场景渲染器
        setRenderer(mRenderer);                //设置渲染器
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染
    }

    //触摸事件回调方法

    @Override
    protected void touchEventCallback(double y, double x) {
        super.touchEventCallback(y, x);
        cube.yAngle += y;//设置填充椭圆绕y轴旋转的角度
        cube.xAngle += x;//设置填充椭圆绕x轴旋转的角度
    }


    private class SceneRenderer implements GLSurfaceView.Renderer {

        public void onDrawFrame(GL10 gl) {
            //清除深度缓冲与颜色缓冲
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            //初始化光源位置
            MatrixState.setLightLocation(lightOffset, 0, 1.5f);
            //保护现场
            MatrixState.pushMatrix();
            //绘制立方体
            MatrixState.pushMatrix();
            MatrixState.translate(-1.2f, 0, 0);
            cube.drawSelf();
            MatrixState.popMatrix();
            //绘制立方体
            MatrixState.pushMatrix();
            MatrixState.translate(1.2f, 0, 0);
            cube.drawSelf();
            MatrixState.popMatrix();
            //恢复现场
            MatrixState.popMatrix();
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视窗大小及位置
            GLES30.glViewport(0, 0, width, height);
            //计算GLSurfaceView的宽高比
            Constant.ratio = (float) width / height;
            // 调用此方法计算产生透视投影矩阵
            MatrixState.setProjectFrustum(-Constant.ratio, Constant.ratio, -1, 1, 20, 100);
            // 调用此方法产生摄像机9参数位置矩阵
            MatrixState.setCamera(0, 0f, 30, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

            //初始化变换矩阵
            MatrixState.setInitStack();
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
            GLES30.glClearColor(0f, 0f, 0f, 1.0f);
            //创建立方体对象
            cube = new Cube(FaceNormalVectorRenderer.this);
            //打开深度检测
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            //打开背面剪裁
            GLES30.glEnable(GLES30.GL_CULL_FACE);
        }
    }

    public void setLightOffset(float lightOffset) {
        this.lightOffset = lightOffset;
    }
}
