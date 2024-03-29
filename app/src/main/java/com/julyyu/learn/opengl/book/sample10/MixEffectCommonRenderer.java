package com.julyyu.learn.opengl.book.sample10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.view.MotionEvent;

import com.julyyu.learn.opengl.Constant;
import com.julyyu.learn.opengl.MatrixState;
import com.julyyu.learn.opengl.R;
import com.julyyu.learn.opengl.book.sample10.sample10_1.KeyThread;
import com.julyyu.learn.opengl.book.sample10.sample10_1.LoadUtil;
import com.julyyu.learn.opengl.book.sample10.sample10_1.LoadedObjectVertexNormalAverage;
import com.julyyu.learn.opengl.book.sample10.sample10_1.LoadedObjectVertexNormalFace;
import com.julyyu.learn.opengl.book.sample10.sample10_1.TextureRect;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public abstract class MixEffectCommonRenderer extends GLSurfaceView {
    private SceneRenderer mRenderer;//场景渲染器

    //矩形的位置
    public float rectX;
    public float rectY;
    public int rectState = KeyThread.Stop;
    public final float moveSpan = 0.1f;
    private KeyThread keyThread;

    public abstract SceneRenderer createRenderer();


    public MixEffectCommonRenderer(Context context) {
        super(context);
        this.setEGLContextClientVersion(3); //设置使用OPENGL ES3.0
        setRenderer(createRenderer());                //设置渲染器
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染
    }

    //触摸事件回调方法
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (x < Constant.SCREEN_WIDTH / 3.0f) {//按下屏幕左面1/3向左移
                    rectState = KeyThread.left;
                } else if (x > Constant.SCREEN_WIDTH * 2 / 3.0f) {//按下屏幕右面2/3向右移
                    rectState = KeyThread.right;
                } else {
                    if (y < Constant.SCREEN_HEIGHT / 2.0f) {   //按下屏幕上方向上移
                        rectState = KeyThread.up;
                    } else {//按下屏幕下方向下移
                        rectState = KeyThread.down;
                    }
                }
                break;
            case MotionEvent.ACTION_UP://抬起时停止移动
                rectState = KeyThread.Stop;
                break;
        }
        return true;
    }

    public abstract class SceneRenderer implements Renderer {
        int rectTexId;//纹理id
        //从指定的obj文件中加载对象
        LoadedObjectVertexNormalFace pm;
        LoadedObjectVertexNormalFace cft;
        LoadedObjectVertexNormalAverage qt;
        LoadedObjectVertexNormalAverage yh;
        LoadedObjectVertexNormalAverage ch;
        TextureRect rect;

        public abstract void glMixFunc();
        public abstract int loadTexture();

        public void onDrawFrame(GL10 gl) {
            //清除深度缓冲与颜色缓冲
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);

            MatrixState.pushMatrix();
            MatrixState.pushMatrix();
            MatrixState.rotate(25, 1, 0, 0);
            //若加载的物体部位空则绘制物体
            pm.drawSelf();//平面

            //缩放物体
            MatrixState.pushMatrix();
            MatrixState.scale(1.5f, 1.5f, 1.5f);
            //绘制物体
            //绘制长方体
            MatrixState.pushMatrix();
            MatrixState.translate(-10f, 0f, 0);
            cft.drawSelf();
            MatrixState.popMatrix();
            //绘制球体
            MatrixState.pushMatrix();
            MatrixState.translate(10f, 0f, 0);
            qt.drawSelf();
            MatrixState.popMatrix();
            //绘制圆环
            MatrixState.pushMatrix();
            MatrixState.translate(0, 0, -10f);
            yh.drawSelf();
            MatrixState.popMatrix();
            //绘制茶壶
            MatrixState.pushMatrix();
            MatrixState.translate(0, 0, 10f);
            ch.drawSelf();
            MatrixState.popMatrix();
            MatrixState.popMatrix();
            MatrixState.popMatrix();

            glMixFunc();


            //绘制纹理矩形
            MatrixState.pushMatrix();
            MatrixState.translate(rectX, rectY, 25f);
            rect.drawSelf(rectTexId);//绘制滤光镜纹理矩形
            MatrixState.popMatrix();
            //关闭混合
            GLES30.glDisable(GLES30.GL_BLEND);

            MatrixState.popMatrix();
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视窗大小及位置
            GLES30.glViewport(0, 0, width, height);
            //计算GLSurfaceView的宽高比
            float ratio = (float) width / height;
            //调用此方法计算产生透视投影矩阵
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 2, 100);
            //设置camera位置
            MatrixState.setCamera
                    (
                            0,   //人眼位置的X
                            0,    //人眼位置的Y
                            50,   //人眼位置的Z
                            0,    //人眼球看的点X
                            0,   //人眼球看的点Y
                            0,   //人眼球看的点Z
                            0,    //up位置
                            1,
                            0
                    );
            //初始化光源位置
            MatrixState.setLightLocation(100, 100, 100);
            keyThread = new KeyThread(MixEffectCommonRenderer.this);
            keyThread.start();
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
            GLES30.glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
            //打开深度检测
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            //打开背面剪裁
            GLES30.glEnable(GLES30.GL_CULL_FACE);
            //初始化变换矩阵
            MatrixState.setInitStack();
            //纹理id
            rectTexId = loadTexture();
            //加载要绘制的物体
            ch = LoadUtil.loadFromFileVertexOnlyAverage("book/sample10/ch.obj", MixEffectCommonRenderer.this.getResources(), MixEffectCommonRenderer.this);
            pm = LoadUtil.loadFromFileVertexOnlyFace("book/sample10/pm.obj", MixEffectCommonRenderer.this.getResources(), MixEffectCommonRenderer.this);
            cft = LoadUtil.loadFromFileVertexOnlyFace("book/sample10/cft.obj", MixEffectCommonRenderer.this.getResources(), MixEffectCommonRenderer.this);
            qt = LoadUtil.loadFromFileVertexOnlyAverage("book/sample10/qt.obj", MixEffectCommonRenderer.this.getResources(), MixEffectCommonRenderer.this);
            yh = LoadUtil.loadFromFileVertexOnlyAverage("book/sample10/yh.obj", MixEffectCommonRenderer.this.getResources(), MixEffectCommonRenderer.this);
            rect = new TextureRect(MixEffectCommonRenderer.this, 10, 10);
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
                        GLES30.GL_TEXTURE_2D,   //纹理类型
                        0,                      //纹理的层次，0表示基本图像层，可以理解为直接贴图
                        bitmapTmp,              //纹理图像
                        0                      //纹理边框尺寸
                );
        bitmapTmp.recycle();          //纹理加载成功后释放图片

        return textureId;
    }

    @Override
    public void onResume() {
        super.onResume();
        KeyThread.flag = true;
        keyThread = new KeyThread(MixEffectCommonRenderer.this);
        keyThread.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        KeyThread.flag = false;
    }
}
