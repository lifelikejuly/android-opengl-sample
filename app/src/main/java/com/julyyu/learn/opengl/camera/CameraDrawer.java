package com.julyyu.learn.opengl.camera;

import android.content.Context;
import android.opengl.GLES11Ext;
import android.opengl.GLES30;

import com.julyyu.learn.opengl.util.BufferUtil;
import com.julyyu.learn.opengl.util.ShaderUtil;

import java.nio.FloatBuffer;

/**
 * @Author: JulyYu
 * @CreateDate: 2020-03-12
 */
public class CameraDrawer {

    private static final int VERTEX_ATTRIB_POSITION_SIZE = 3;
    private static final int VERTEX_ATTRIB_TEXTURE_POSITION_SIZE = 2;

    private float[] vertex = {
            -1f, 1f, 0.0f,//左上
            -1f, -1f, 0.0f,//左下
            1f, -1f, 0.0f,//右下
            1f, 1f, 0.0f//右上
    };

    //纹理坐标，（s,t），t坐标方向和顶点y坐标反着
    public float[] textureCoord = {
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 0.0f
    };

    private FloatBuffer vertexBuffer;
    private FloatBuffer textureCoordBuffer;
    private int program;

    private Context context;


    public CameraDrawer(Context context) {
        this.context = context;

        initVertexAttrib(); //初始化顶点数据

        program = ShaderUtil.createProgram("camera.vsh", "camera.fsh");
        GLES30.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
    }

    private void initVertexAttrib() {
        textureCoordBuffer = BufferUtil.creatFloatBuffer(textureCoord);
        vertexBuffer = BufferUtil.creatFloatBuffer(vertex);
    }

    public void draw(int textureId) {
        GLES30.glUseProgram(program);
        //初始化句柄
        int vertexLoc = GLES30.glGetAttribLocation(program, "a_Position");
        int textureLoc = GLES30.glGetAttribLocation(program, "a_texCoord");

        GLES30.glEnableVertexAttribArray(vertexLoc);
        GLES30.glEnableVertexAttribArray(textureLoc);

        GLES30.glVertexAttribPointer(vertexLoc,
                VERTEX_ATTRIB_POSITION_SIZE,
                GLES30.GL_FLOAT,
                false,
                0,
                vertexBuffer);

        GLES30.glVertexAttribPointer(textureLoc,
                VERTEX_ATTRIB_TEXTURE_POSITION_SIZE,
                GLES30.GL_FLOAT,
                false,
                0,
                textureCoordBuffer);

        //纹理绑定
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textureId);
        GLES30.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
        GLES30.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);
        int uTextureLoc = GLES30.glGetUniformLocation(program, "s_texture");
        GLES30.glUniform1i(uTextureLoc, 0);
        //绘制
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 0, vertex.length / 3);
        //禁用顶点
        GLES30.glDisableVertexAttribArray(vertexLoc);
        GLES30.glDisableVertexAttribArray(textureLoc);
    }

}
