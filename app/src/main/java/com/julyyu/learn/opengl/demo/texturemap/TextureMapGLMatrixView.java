package com.julyyu.learn.opengl.demo.texturemap;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.julyyu.learn.opengl.R;
import com.julyyu.learn.opengl.camera.water.FrameBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author julyyu
 * @date 2021/9/22.
 * description：纹理分离 每个渲染器一个纹理*
 *
 */
public class TextureMapGLMatrixView extends GLSurfaceView {

    SceneRendererMuch1 mRenderer1;
    SceneRendererMuch1 mRenderer2;
    SceneRendererMuch1 mRenderer4;
    SceneFBORenderer mRenderer3;

    int width,height;
    private FrameBuffer fbo;

    public TextureMapGLMatrixView(Context context) {
        super(context);
        this.setEGLContextClientVersion(3);    //设置使用OPENGL ES3.0
        mRenderer1 = new SceneRendererMuch1(context,R.raw.i1000x1000);
        mRenderer2 = new SceneRendererMuch1(context,R.raw.jay);
        mRenderer2.setScale(0.5f);
        mRenderer2.setDx(0.75f);
        mRenderer2.setDy(1.75f);
        mRenderer4 = new SceneRendererMuch1(context,R.raw.i800x600);
        mRenderer4.setScale(0.5f);
        mRenderer4.setDx(-0.75f);
        mRenderer4.setDy(-1.75f);

        mRenderer3 = new SceneFBORenderer(context);
        setRenderer(new Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                mRenderer2.onSurfaceCreated();
                mRenderer1.onSurfaceCreated();
                mRenderer4.onSurfaceCreated();
                mRenderer3.onSurfaceCreated();
                fbo = new FrameBuffer();

            }

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {
                GLES30.glViewport(0, 0, width, height);
                TextureMapGLMatrixView.this.width = width;
                TextureMapGLMatrixView.this.height = height;
                mRenderer2.onSurfaceChanged(width,height);
                mRenderer1.onSurfaceChanged(width,height);
                mRenderer4.onSurfaceChanged(width,height);
                mRenderer3.onSurfaceChanged(width,height);

                fbo.setup(width, height);
            }

            @Override
            public void onDrawFrame(GL10 gl) {
                fbo.begin();
                GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
                GLES20.glEnable(GLES20.GL_BLEND);
                GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                mRenderer1.onDrawFrame();
                mRenderer2.onDrawFrame();
                mRenderer4.onDrawFrame();
                fbo.end();
                mRenderer3.onDrawFrame(fbo.getTextureId());
            }
        });                //设置渲染器
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染
    }




}
