package com.julyyu.learn.opengl.book.sample8.sample8_7;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.view.MotionEvent;

import com.julyyu.learn.opengl.MatrixState;
import com.julyyu.learn.opengl.R;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: JulyYu
 * @CreateDate: 2020-03-06
 */
public class BuildingRenderer extends GLSurfaceView {
    private final float TOUCH_SCALE_FACTOR = 180.0f / 480;//角度缩放比例

    private float cameraX = 0;//摄像机的位置
    private float cameraY = 0;
    private float cameraZ = 8;

    private float targetX = 0;//看点
    private float targetY = -2;
    private float targetZ = -15;

    private float sightDis = 30;//摄像机和目标的距离
    private float angdegElevation = 45;//仰角
    private float angdegAzimuth = 90;//方位角


    private float mPreviousY;//上次的触控位置Y坐标
    private float mPreviousX;//上次的触控位置X坐标

    private SceneRenderer mRenderer;//场景渲染器

    int textureId;      //系统分配的纹理id

    boolean lightFlag = true;        //光照旋转的标志位

    public BuildingRenderer(Context context) {
        super(context);
        this.setEGLContextClientVersion(3); //设置使用OPENGL ES3.0
        mRenderer = new SceneRenderer();    //创建场景渲染器
        setRenderer(mRenderer);                //设置渲染器
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染
    }

    //触摸事件回调方法
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dy = y - mPreviousY;//计算触控笔Y位移
                float dx = x - mPreviousX;//计算触控笔X位移
                angdegAzimuth += dx * TOUCH_SCALE_FACTOR;//设置沿y轴旋转角度
                angdegElevation += dy * TOUCH_SCALE_FACTOR;//设置沿x轴旋转角度

                //方位角
                if (angdegAzimuth >= 360) {
                    angdegAzimuth = 0;
                } else if (angdegAzimuth <= 0) {
                    angdegAzimuth = 360;
                }
                //仰角
                if (angdegElevation >= 85) {
                    angdegElevation = 85;
                } else if (angdegElevation <= 0) {
                    angdegElevation = 0;
                }
        }
        mPreviousY = y;//记录触控笔位置
        mPreviousX = x;//记录触控笔位置
        return true;
    }

    private class SceneRenderer implements GLSurfaceView.Renderer {

        Building building;

        public void onDrawFrame(GL10 gl) {
            //清除深度缓冲与颜色缓冲
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);


            double angradElevation = Math.toRadians(angdegElevation);//仰角（弧度）
            double angradAzimuth = Math.toRadians(angdegAzimuth);//方位角
            cameraX = (float) (targetX + sightDis * Math.cos(angradElevation) * Math.cos(angradAzimuth));
            cameraY = (float) (targetY + sightDis * Math.sin(angradElevation));
            cameraZ = (float) (targetZ + sightDis * Math.cos(angradElevation) * Math.sin(angradAzimuth));

            MatrixState.setCamera(//设置camera位置
                    cameraX, //人眼位置的X
                    cameraY, //人眼位置的Y
                    cameraZ, //人眼位置的Z

                    targetX, //人眼球看的点X
                    targetY, //人眼球看的点Y
                    targetZ, //人眼球看的点Z

                    0,  //头的朝向
                    1,
                    0
            );

            //保护现场
            MatrixState.pushMatrix();
            MatrixState.translate(0, 0, -15);
            building.drawSelf(textureId);
            MatrixState.popMatrix();

        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视窗大小及位置
            GLES30.glViewport(0, 0, width, height);
            //计算GLSurfaceView的宽高比
            float ratio = (float) width / height;
            //调用此方法计算产生透视投影矩阵
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 4f, 100);
//            //调用此方法产生摄像机9参数位置矩阵
//            MatrixState.setCamera(0,0,8.0f,0f,0f,0f,0f,1.0f,0.0f);

            //初始化光源
            MatrixState.setLightLocation(10, 0, -10);

            //启动一个线程定时修改灯光的位置
            new Thread() {
                public void run() {
                    float redAngle = 0;
                    while (lightFlag) {
                        //根据角度计算灯光的位置
                        redAngle = (redAngle + 5) % 360;
                        float rx = (float) (15 * Math.sin(Math.toRadians(redAngle)));
                        float rz = (float) (15 * Math.cos(Math.toRadians(redAngle)));
                        MatrixState.setLightLocation(rx, 0, rz);

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
            GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            //启用深度测试
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            //设置为打开背面剪裁
            GLES30.glEnable(GLES30.GL_CULL_FACE);
            //初始化变换矩阵
            MatrixState.setInitStack();

            //加载纹理
            textureId = initTexture(R.drawable.white);

            //创建对象
            building = new Building(BuildingRenderer.this, 0.8f, 8, 8);

        }
    }

    public int initTexture(int drawableId)//textureId
    {
        //生成纹理ID
        int[] textures = new int[1];
        GLES30.glGenTextures
                (
                        1,          //产生的纹理id的数量
                        textures,   //纹理id的数组
                        0           //偏移量
                );
        int textureId = textures[0];
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);

        //通过输入流加载图片===============begin===================
        InputStream is = this.getResources().openRawResource(drawableId);
        Bitmap bitmapTmp;
        try {
            bitmapTmp = BitmapFactory.decodeStream(is);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //通过输入流加载图片===============end=====================

        //实际加载纹理
        GLUtils.texImage2D
                (
                        GLES30.GL_TEXTURE_2D,   //纹理类型，在OpenGL ES中必须为GL10.GL_TEXTURE_2D
                        0,                      //纹理的层次，0表示基本图像层，可以理解为直接贴图
                        bitmapTmp,              //纹理图像
                        0                      //纹理边框尺寸
                );
        bitmapTmp.recycle();          //纹理加载成功后释放图片

        return textureId;
    }
}
