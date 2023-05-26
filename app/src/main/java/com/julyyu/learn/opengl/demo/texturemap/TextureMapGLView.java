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
        mRenderer = new SceneRenderer(context,R.raw.i900x1600);    //创建场景渲染器
        setRenderer(mRenderer);                //设置渲染器
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染
    }


}
