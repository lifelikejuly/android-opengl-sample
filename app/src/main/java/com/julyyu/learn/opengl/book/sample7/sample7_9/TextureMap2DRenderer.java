package com.julyyu.learn.opengl.book.sample7.sample7_9;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.julyyu.learn.opengl.Constant;
import com.julyyu.learn.opengl.MatrixState;
import com.julyyu.learn.opengl.R;

import java.nio.ByteBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: JulyYu
 * @CreateDate: 2020-03-06
 */
public class TextureMap2DRenderer extends GLSurfaceView {


    private SceneRenderer mRenderer;//场景渲染器

    //纹理图资源id数组
    int[] texResIdArray =
            {
                    R.drawable.tex1, R.drawable.tex2, R.drawable.tex3, R.drawable.tex4,
                    R.drawable.tex5, R.drawable.tex6, R.drawable.tex7, R.drawable.tex8,
                    R.drawable.tex9, R.drawable.tex10, R.drawable.tex11, R.drawable.tex12,
                    R.drawable.tex13, R.drawable.tex14, R.drawable.tex15, R.drawable.tex16
            };

    public TextureMap2DRenderer(Context context) {
        super(context);
        this.setEGLContextClientVersion(3); //设置使用OPENGL ES3.0
        mRenderer = new SceneRenderer();    //创建场景渲染器
        setRenderer(mRenderer);                //设置渲染器
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染
    }

    private class SceneRenderer implements GLSurfaceView.Renderer {
        Points point;//点或线
        int texId;//纹理id

        public void onDrawFrame(GL10 gl) {
            //清除深度缓冲与颜色缓冲
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);
            //保护现场
            MatrixState.pushMatrix();
            //绘制点
            MatrixState.pushMatrix();
            point.drawSelf(texId);
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
            MatrixState.setCamera(0, 8f, 30, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
            //初始化变换矩阵
            MatrixState.setInitStack();
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
            GLES30.glClearColor(0, 0, 0, 1.0f);
            //创建点对象
            point = new Points(TextureMap2DRenderer.this);
            //打开深度检测
            GLES30.glEnable(GLES30.GL_DEPTH_TEST);
            //打开背面剪裁
            GLES30.glEnable(GLES30.GL_CULL_FACE);
            //加载纹理
            texId = initTextureArray(texResIdArray, 128, 128);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Constant.UNIT_SIZE = 0.5f;
    }

    @Override
    public void onPause() {
        super.onPause();
        Constant.UNIT_SIZE = 1f;
    }

    //加载纹理的方法
    public int initTextureArray(int[] picId, int width, int height) {
        //生成纹理ID
        int[] textures = new int[1];
        GLES30.glGenTextures
                (
                        1,          //产生的纹理id的数量
                        textures,   //纹理id的数组
                        0           //偏移量
                );
        int textureId = textures[0];
        //绑定2D纹理数组
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D_ARRAY, textureId);
        //设置采样参数
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D_ARRAY, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);//设置S轴拉伸方式
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D_ARRAY, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);//设置T轴拉伸方式
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D_ARRAY, GLES30.GL_TEXTURE_WRAP_R, GLES30.GL_CLAMP_TO_EDGE);//设置R轴拉伸方式
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D_ARRAY, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);//设置MIN采样参数
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D_ARRAY, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);    //设置MAG采样参数
        //将一组图片数据加载到内存缓冲
        ByteBuffer texels = ConvertUtil.convertPicsToBuffer
                (
                        this.getResources(),
                        picId,
                        width,
                        height
                );
        //设置缓冲区起始位置
        texels.position(0);
        //加载2D纹理数组
        GLES30.glTexImage3D
                (
                        GLES30.GL_TEXTURE_2D_ARRAY,  //纹理类型
                        0, //纹理的层次
                        GLES30.GL_RGBA8, //纹理颜色分量
                        width, //宽度
                        height, //高度
                        picId.length,//数组长度
                        0, //纹理边框尺寸
                        GLES30.GL_RGBA, //纹理数据的格式
                        GLES30.GL_UNSIGNED_BYTE, //纹理数据的数据类型
                        texels//纹理缓冲
                );


        return textureId;//返回纹理id
    }

}
