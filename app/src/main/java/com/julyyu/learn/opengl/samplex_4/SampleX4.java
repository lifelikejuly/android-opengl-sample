package com.julyyu.learn.opengl.samplex_4;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.julyyu.learn.opengl.BaseDrawSample;
import com.julyyu.learn.opengl.util.BufferUtil;

/**
 * @Author: yuhaocan
 * @CreateDate: 2020-02-26
 */
public class SampleX4 extends BaseDrawSample {


    public SampleX4(GLSurfaceView mv) {
        super(mv);
    }

    @Override
    public void initVertexData() {

        float[] vertexs = {
                //正面
                -1, 1, 1,
                1, 1, 1,
                1, -1, 1,
                -1, -1, 1,

                //左侧面
                -1, 1, 1,
                -1, 1, -1,
                -1, -1, -1,
                -1, -1, 1,


                //右侧面
                1, 1, 1,
                1, 1, -1,
                1, -1, -1,
                1, -1, 1,

                //反面
                -1, 1, -1,
                1, 1, -1,
                1, -1, -1,
                -1, -1, -1,

                //上面
                -1, 1, -1,
                1, 1, -1,
                1, 1, 1,
                -1, 1, 1,


                //下面
                -1, -1, -1,
                1, -1, -1,
                1, -1, 1,
                -1, -1, 1,
        };
        mVertexBuffer = BufferUtil.creatFloatBuffer(vertexs);
        float white[] = {
                1, 1, 1, 0
        };

        float colors[] = new float[vertexs.length * 4 / 3];//顶点颜色数组
        for (int i = 0; i < vertexs.length / 3; i++) {
            System.arraycopy(white, 0, colors, i * 4, white.length);
        }
        mColorBuffer = BufferUtil.creatFloatBuffer(colors);
    }

    @Override
    public void drawSelf() {
        super.drawSelf();
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 0, 4);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 4, 4);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 8, 4);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 12, 4);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 16, 4);
    }
}
