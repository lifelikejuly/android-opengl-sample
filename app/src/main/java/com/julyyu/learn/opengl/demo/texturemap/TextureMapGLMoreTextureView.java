package com.julyyu.learn.opengl.demo.texturemap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.julyyu.learn.opengl.R;
import com.julyyu.learn.opengl.util.BuffetUtils;
import com.julyyu.learn.opengl.util.GLESUtils;
import com.julyyu.learn.opengl.util.LogUtils;
import com.julyyu.learn.opengl.util.ShaderUtil;

import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author julyyu
 * @date 2021/9/22.
 * description：一个渲染器同时加载两个纹理
 */
public class TextureMapGLMoreTextureView extends GLSurfaceView {



    public static String fsCode2 = "#version 300 es\n" +
            "precision mediump float;\n" +
            "uniform sampler2D uTextureUnit;\n" +
            "uniform sampler2D uTextureUnit2;\n" +
            "in vec2 vTexCoord;\n" +
            "out vec4 vFragColor;\n" +
            "void main() {\n" +
            "    vFragColor =  mix(texture(uTextureUnit2, vTexCoord), texture(uTextureUnit, vTexCoord), 0.5);\n" +
            "}\n";


    SceneRendererTwo mRenderer;


    public TextureMapGLMoreTextureView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);    //设置使用OPENGL ES3.0
        mRenderer = new SceneRendererTwo(context);    //创建场景渲染器
        setRenderer(mRenderer);                //设置渲染器
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染
    }


    static class SceneRendererTwo implements Renderer {


        Context context;

        FloatBuffer vertexBuffer, mTexVertexBuffer;
        ShortBuffer mVertexIndexBuffer;


        int mProgram;
        int textureId1,textureId2;//系统分配的纹理id

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
        //纹理坐标
        private int aTextureLocation;
        // 纹理
        private int uTextureLoc1,uTextureLoc2;

        /**
         * 顶点坐标
         * (x,y,z)
         *
         * -1，1 （1）           1，1 （0）
         *
         *
         * -1，-1 （2）            1，-1（3）
         */
        private static final float[] POSITION_VERTEX = new float[]{
                1f, 1f, 0f,     //顶点坐标V1
                -1f, 1f, 0f,    //顶点坐标V2
                -1f, -1f, 0f,   //顶点坐标V3
                1f, -1f, 0f     //顶点坐标V4
        };

        /**
         * 纹理坐标
         * (s,t)
         * 0，1	（3） 		1，1（2）
         *
         *
         * 0，0	（1）		1，0  （0）
         *
         */
        private static final float[] TEX_VERTEX = {
                1f, 0f,     //纹理坐标V1
                0f, 0f,     //纹理坐标V2
                0f, 1.0f,   //纹理坐标V3
                1f, 1.0f    //纹理坐标V4
        };

        /**
         * 绘制顺序索引
         */
        private static final short[] VERTEX_INDEX = {
                0, 1, 3,  //V0,V1,V2 三个顶点组成一个三角形
                1, 2, 3,  //V0,V2,V3 三个顶点组成一个三角形
        };

        private Bitmap mBitmap1,mBitmap2;


        public SceneRendererTwo(Context context) {
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
            // 加载顶点 加载片元 加载程序 返回程序
            mProgram = ShaderUtil.createProgram(ShaderUtil.vsCode,fsCode2);
            //在OpenGLES环境中使用程序
            GLES30.glUseProgram(mProgram);
            //获取属性位置
            uMatrixLocation = GLES30.glGetUniformLocation(mProgram, "u_Matrix");
            aPositionLocation = GLES30.glGetAttribLocation(mProgram, "vPosition");
            aTextureLocation = GLES30.glGetAttribLocation(mProgram, "aTextureCoord");
            uTextureLoc1 = GLES30.glGetUniformLocation(mProgram,"uTextureUnit");
            uTextureLoc2 = GLES30.glGetUniformLocation(mProgram,"uTextureUnit2");
            //初始化纹理
            //通过输入流加载图片===============begin===================
            InputStream is1 = context.getResources().openRawResource(R.raw.i1600x900);
            InputStream is2 = context.getResources().openRawResource(R.raw.i900x1600);
            try {
                mBitmap1 = BitmapFactory.decodeStream(is1);
                mBitmap2 = BitmapFactory.decodeStream(is2);
            } finally {
                try {
                    is1.close();
                    is2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //通过输入流加载图片===============end=====================
            int[] textureIds  = GLESUtils.loadTexture(mBitmap1,mBitmap2);
            textureId1 = textureIds[0];
            textureId2 = textureIds[1];
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视窗大小及位置
            GLES30.glViewport(0, 0, width, height);

            int w= mBitmap2.getWidth();
            int h= mBitmap2.getHeight();
            float sWH=w/(float)h; // 图片宽高比
            float sWidthHeight=width/(float)height; // 画布宽高比

            // 画布尺寸+图片尺寸调整
            if(width>height){ // 画布宽大于高
                if(sWH>sWidthHeight){ // 图片宽高比大于画布宽高比
                    Matrix.orthoM(mProjectMatrix, 0, -sWidthHeight*sWH,sWidthHeight*sWH, -1,1, 0, 1f);
                }else{
                    Matrix.orthoM(mProjectMatrix, 0, -sWidthHeight/sWH,sWidthHeight/sWH, -1,1, 0, 1f);
                }
            }else{
                if(sWH>sWidthHeight){ // 图片宽高比小于画布宽高比
                    Matrix.orthoM(mProjectMatrix, 0, -1, 1, -1/sWidthHeight*sWH, 1/sWidthHeight*sWH,0f, 1f);
                }else{
                    Matrix.orthoM(mProjectMatrix, 0, -1, 1, -sWH/sWidthHeight, sWH/sWidthHeight,0f, 1f);
                }
            }

            // 不需要加相机的方法
            Matrix.transposeM(mMVPMatrix,0,mProjectMatrix,0);


            // 缩放
//            Matrix.scaleM(mMVPMatrix,0,0.5f,0.5f,1.0f);
            // 旋转
//            Matrix.rotateM(mMVPMatrix,0,45f,0f,0f,1f);

            Log.d("<>matrix mProjectMatrix", LogUtils.print(mProjectMatrix));
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

            GLES30.glUniform1i(uTextureLoc1,0);
            GLES30.glUniform1i(uTextureLoc2,1);

            //激活纹理
            GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
            //绑定纹理 到激活纹理上
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId1);

            //激活纹理
            GLES30.glActiveTexture(GLES30.GL_TEXTURE1);
            //绑定纹理 到激活纹理上
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureId2);


            // 绘制
            GLES30.glDrawElements(GLES20.GL_TRIANGLES, VERTEX_INDEX.length, GLES20.GL_UNSIGNED_SHORT, mVertexIndexBuffer);

            //禁止顶点数组的句柄
            GLES30.glDisableVertexAttribArray(aPositionLocation);
            GLES30.glDisableVertexAttribArray(aTextureLocation);
        }


    }


}
