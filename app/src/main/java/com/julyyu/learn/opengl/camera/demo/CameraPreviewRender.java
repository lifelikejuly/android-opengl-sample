package com.julyyu.learn.opengl.camera.demo;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES11Ext;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.julyyu.learn.opengl.R;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_CLAMP_TO_EDGE;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_NEAREST;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_WRAP_S;
import static android.opengl.GLES20.GL_TEXTURE_WRAP_T;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glTexParameterf;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

/**
 * Author: yeliang
 * Date: 2019/9/5
 * Time: 1:19 PM
 * Description:
 */

public class CameraPreviewRender implements GLSurfaceView.Renderer {

    private static final String VERTEX_ATTRIB_POSITION = "a_Position";
    private static final int VERTEX_ATTRIB_POSITION_SIZE = 3;
    private static final String VERTEX_ATTRIB_TEXTURE_POSITION = "a_texCoord";
    private static final int VERTEX_ATTRIB_TEXTURE_POSITION_SIZE = 2;
    private static final String UNIFORM_TEXTURE = "s_texture";


    private float[] vertexCoord = {
            -1f, 1f, 0.0f,
            -1f, -1f, 0.0f,
            1f, -1f, 0.0f,
            1f, 1f, 0.0f
    };

    private float[] textureCoord = {
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 0.0f
    };

    private FloatBuffer vertexBuffer;
    private FloatBuffer textureCoordBuffer;

    private int program;

    private Context mContext;

    //接收相机数据的纹理
    private int[] textureId = new int[1];

    //接收相机数据的SurfaceTexture
    private SurfaceTexture surfaceTexture;

    public CameraPreviewRender(Context context) {
        mContext = context;
        initVertexAttrib();
    }

    private void initVertexAttrib() {
        vertexBuffer = GLUtil.getFloatBuffer(vertexCoord);
        textureCoordBuffer = GLUtil.getFloatBuffer(textureCoord);
    }

    public SurfaceTexture getSurfaceTexture() {
        return surfaceTexture;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.i("===TAG===","onSurfaceCreated");
        glGenTextures(textureId.length, textureId, 0);
        surfaceTexture = new SurfaceTexture(textureId[0]);
        program = GLUtil.linkProgram(R.raw.vertex_shader, R.raw.fragment_shader, mContext);
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.i("===TAG===","onDrawFrame");
        surfaceTexture.updateTexImage();
        glClear(GL_COLOR_BUFFER_BIT);
        glUseProgram(program);

        int vertexLocation = glGetAttribLocation(program, VERTEX_ATTRIB_POSITION);
        int textureLocation = glGetAttribLocation(program, VERTEX_ATTRIB_TEXTURE_POSITION);

        glEnableVertexAttribArray(vertexLocation);
        glEnableVertexAttribArray(textureLocation);

        glVertexAttribPointer(
                vertexLocation,
                VERTEX_ATTRIB_POSITION_SIZE,
                GL_FLOAT,
                false,
                0,
                vertexBuffer
        );

        glVertexAttribPointer(
                textureLocation,
                VERTEX_ATTRIB_TEXTURE_POSITION_SIZE,
                GL_FLOAT,
                false,
                0,
                textureCoordBuffer
        );

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId[0]);

        glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        int uTextureLoc = glGetUniformLocation(program, UNIFORM_TEXTURE);
        glUniform1i(uTextureLoc, 0);

        glDrawArrays(GL_TRIANGLE_FAN, 0, vertexCoord.length / 3);

        glDisableVertexAttribArray(textureLocation);
        glDisableVertexAttribArray(vertexLocation);
    }
}
