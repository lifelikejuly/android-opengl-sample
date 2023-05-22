package com.julyyu.learn.opengl.demo.texturemap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

import com.julyyu.learn.opengl.R;
import com.julyyu.learn.opengl.util.BuffetUtils;
import com.julyyu.learn.opengl.util.LogUtils;
import com.julyyu.learn.opengl.util.ShaderUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author julyyu
 * @date 2021/9/22.
 * description：
 */
public class TextureMapGLView extends GLSurfaceView {

    SceneRenderer mRenderer;


    public TextureMapGLView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);    //设置使用OPENGL ES3.0
        mRenderer = new SceneRenderer(context);    //创建场景渲染器
        setRenderer(mRenderer);                //设置渲染器
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染
    }


    static class SceneRenderer implements Renderer {


        Context context;

        FloatBuffer vertexBuffer, mTexVertexBuffer;
        ShortBuffer mVertexIndexBuffer;


        int mProgram;
        int textureId;//系统分配的纹理id

        //相机矩阵
//        private final float[] mViewMatrix = new float[16];
        //投影矩阵
        private final float[] mProjectMatrix = new float[16];
        //最终变换矩阵
        private final float[] mMVPMatrix = new float[16];

        //返回属性变量的位置
        //变换矩阵
        private int uMatrixLocation;
        //顶点
        private int aPositionLocation;
        //纹理
        private int aTextureLocation;

        /**
         * 顶点坐标
         * (x,y,z)
         *
         * -1，1 （2）           1，1 （1）
         *
         *        0，0（0）
         *
         * -1，-1 （3）            1，-1（4）
         */
        private static final float[] POSITION_VERTEX = new float[]{
                0f, 0f, 0f,     //顶点坐标V0
                1f, 1f, 0f,     //顶点坐标V1
                -1f, 1f, 0f,    //顶点坐标V2
                -1f, -1f, 0f,   //顶点坐标V3
                1f, -1f, 0f     //顶点坐标V4
        };

        /**
         * 纹理坐标
         * (s,t)
         * 0，1	（3） 		1，1（4）
         *
         *       0.5，0.5（0）
         *
         * 0，0	（2）		1，0  （1）
         *
         */
        private static final float[] TEX_VERTEX = {
                0.5f, 0.5f, //纹理坐标V0
                1f, 0f,     //纹理坐标V1
                0f, 0f,     //纹理坐标V2
                0f, 1.0f,   //纹理坐标V3
                1f, 1.0f    //纹理坐标V4
        };

        /**
         * 绘制顺序索引
         */
        private static final short[] VERTEX_INDEX = {
                0, 1, 2,  //V0,V1,V2 三个顶点组成一个三角形
                0, 2, 3,  //V0,V2,V3 三个顶点组成一个三角形
                0, 3, 4,  //V0,V3,V4 三个顶点组成一个三角形
                0, 4, 1   //V0,V4,V1 三个顶点组成一个三角形
        };

        private Bitmap mBitmap;


        public SceneRenderer(Context context) {
            this.context = context;
            // 顶点
            vertexBuffer = BuffetUtils.createFloatBuffer(POSITION_VERTEX,4);
            vertexBuffer.position(0);
            // 纹理
            mTexVertexBuffer = BuffetUtils.createFloatBuffer(TEX_VERTEX,4);
            mTexVertexBuffer.position(0);
            // 顶点坐标
            mVertexIndexBuffer = BuffetUtils.createShortBuffer(VERTEX_INDEX,2);
            mVertexIndexBuffer.position(0);
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
            GLES30.glClearColor(0f, 0f, 0.5f, 1.0f);

            mProgram = ShaderUtil.createProgram(ShaderUtil.vsCode, ShaderUtil.fsCode);
            //在OpenGLES环境中使用程序
            GLES30.glUseProgram(mProgram);
            //获取属性位置
            uMatrixLocation = GLES30.glGetUniformLocation(mProgram, "u_Matrix");
            aPositionLocation = GLES30.glGetAttribLocation(mProgram, "vPosition");
            aTextureLocation = GLES30.glGetAttribLocation(mProgram, "aTextureCoord");
            //初始化纹理
            initTexture();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视窗大小及位置
            GLES30.glViewport(0, 0, width, height);

            int w= mBitmap.getWidth();
            int h= mBitmap.getHeight();
            float sWH=w/(float)h; // 图片宽高比
            float sWidthHeight=width/(float)height; // 画布宽高比

            // 单纯画布尺寸调整
            float aspectRatio = width > height ? (float) width / (float) height : (float) height / (float) width;
            if (width > height) {
                Matrix.orthoM(mProjectMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, 0f, 10f);
            } else {
                Matrix.orthoM(mProjectMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, 0f, 10f);
            }

            // 画布尺寸+图片尺寸调整
//            if(width>height){ // 画布宽大于高
//                if(sWH>sWidthHeight){ // 图片宽高比大于画布宽高比
//                    Matrix.orthoM(mProjectMatrix, 0, -sWidthHeight*sWH,sWidthHeight*sWH, -1,1, 0, 10);
//                }else{
//                    Matrix.orthoM(mProjectMatrix, 0, -sWidthHeight/sWH,sWidthHeight/sWH, -1,1, 0, 10);
//                }
//            }else{
//                if(sWH>sWidthHeight){ // 图片宽高比小于画布宽高比
//                    Matrix.orthoM(mProjectMatrix, 0, -1, 1, -1/sWidthHeight*sWH, 1/sWidthHeight*sWH,0f, 10f);
//                }else{
//                    Matrix.orthoM(mProjectMatrix, 0, -1, 1, -sWH/sWidthHeight, sWH/sWidthHeight,0f, 10f);
//                }
//            }

//            Matrix.orthoM(mProjectMatrix, 0, -1,1, -1,1, -1, 1);

            // 不需要加相机的方法
            Matrix.transposeM(mMVPMatrix,0,mProjectMatrix,0);


            // 缩放
//            Matrix.scaleM(mMVPMatrix,0,0.5f,0.5f,1.0f);
            // 旋转
//            Matrix.rotateM(mMVPMatrix,0,90f,0f,0f,1f);

            Log.d("<>matrix mProjectMatrix", LogUtils.print(mProjectMatrix));
//            //设置相机位置
//            Matrix.setLookAtM(mViewMatrix,
//                    0, 0, 0, 1.0f, // 相机方向
//                    0f, 0f, 0f, // 目标方向
//                    0.0f, 1.0f, 0.0f); // 视觉向量
//            Log.d("<>matrix mViewMatrix", LogUtils.print(mViewMatrix));
//            //计算变换矩阵
//            Matrix.multiplyMM(mMVPMatrix,0,mProjectMatrix,0,mViewMatrix,0);
            Log.d("<>matrix mMVPMatrix", LogUtils.print(mMVPMatrix));
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            //清除深度缓冲与颜色缓冲
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT | GLES30.GL_COLOR_BUFFER_BIT);

            //将变换矩阵传入顶点渲染器
            GLES30.glUniformMatrix4fv(uMatrixLocation,1,false,mMVPMatrix,0);

            //启用顶点坐标属性
            GLES30.glEnableVertexAttribArray(aPositionLocation);
            GLES30.glVertexAttribPointer(aPositionLocation, 3, GLES30.GL_FLOAT, false, 0, vertexBuffer);
            //启用纹理坐标属性
            GLES30.glEnableVertexAttribArray(aTextureLocation);
            GLES30.glVertexAttribPointer(aTextureLocation, 2, GLES30.GL_FLOAT, false, 0, mTexVertexBuffer);
            //激活纹理
            GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
            //绑定纹理
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId);
            // 绘制
            GLES30.glDrawElements(GLES20.GL_TRIANGLES, VERTEX_INDEX.length, GLES20.GL_UNSIGNED_SHORT, mVertexIndexBuffer);

            //禁止顶点数组的句柄
            GLES30.glDisableVertexAttribArray(aPositionLocation);
            GLES30.glDisableVertexAttribArray(aTextureLocation);
        }


        public void initTexture() {
            //生成纹理ID
            int[] textures = new int[1];
            GLES30.glGenTextures
                    (
                            1,          //产生的纹理id的数量
                            textures,   //纹理id的数组
                            0           //偏移量
                    );
            textureId = textures[0];
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId);
            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);
            //通过输入流加载图片===============begin===================
            InputStream is = context.getResources().openRawResource(R.raw.i1600x900);
            try {
                mBitmap = BitmapFactory.decodeStream(is);
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //通过输入流加载图片===============end=====================

            //实际加载纹理进显存
            GLUtils.texImage2D
                    (
                            GLES30.GL_TEXTURE_2D, //纹理类型
                            0,                      //纹理的层次，0表示基本图像层，可以理解为直接贴图
                            mBitmap,              //纹理图像
                            0                      //纹理边框尺寸
                    );
            mBitmap.recycle();          //纹理加载成功后释放内存中的纹理图
        }
    }


}
