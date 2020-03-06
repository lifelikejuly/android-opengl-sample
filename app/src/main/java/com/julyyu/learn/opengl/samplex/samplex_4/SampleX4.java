package com.julyyu.learn.opengl.samplex.samplex_4;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.julyyu.learn.opengl.BaseDrawSample;
import com.julyyu.learn.opengl.util.BufferUtil;

/**
 * @Author: yuhaocan
 * @CreateDate: 2020-02-26
 */

/// 正方形
public class SampleX4 extends BaseDrawSample {


    public SampleX4(GLSurfaceView mv) {
        super(mv);
    }

    @Override
    public void initVertexData() {

        float[] vertexs = {
//                //正面
//                -1, 1, 1,
//                1, 1, 1,
//                1, -1, 1,
//                -1, -1, 1,
//
//                //左侧面
//                -1, 1, 1,
//                -1, 1, -1,
//                -1, -1, -1,
//                -1, -1, 1,
//
//
//                //右侧面
//                1, 1, 1,
//                1, 1, -1,
//                1, -1, -1,
//                1, -1, 1,
//
//                //反面
//                -1, 1, -1,
//                1, 1, -1,
//                1, -1, -1,
//                -1, -1, -1,
//
//                //上面
//                -1, 1, -1,
//                1, 1, -1,
//                1, 1, 1,
//                -1, 1, 1,
//
//
//                //下面
//                -1, -1, -1,
//                1, -1, -1,
//                1, -1, 1,
//                -1, -1, 1,
                //正面
                -1, 1, 1,
                -1, -1, 1,
                1, 1, 1,
                1, -1, 1,
                //后面
                -1, 1, -1,
                -1, -1, -1,
                1, 1, -1,
                1, -1, -1,
                //左侧面
                -1, 1, -1,
                -1, -1, -1,
                -1, 1, 1,
                -1, -1, 1,
                // 右侧面
                1, 1, -1,
                1, -1, -1,
                1, 1, 1,
                1, -1, 1,
                //下面
                -1, -1, 1,
                -1, -1, -1,
                1, -1, 1,
                1, -1, -1,
                //上面
                -1, 1, 1,
                -1, 1, -1,
                1, 1, 1,
                1, 1, -1,

        };

        for (int i = 0; i < vertexs.length; i++) {
            vertexs[i] = vertexs[i] * 0.6f;
        }

        mVertexBuffer = BufferUtil.creatFloatBuffer(vertexs);
        float white[] = {
                1, 0, 0, 0
        };

        float colors[] = new float[vertexs.length * 4 / 3];//顶点颜色数组
        int num = 0;
        for (int i = 0; i < vertexs.length / 3; i++) {
            num++;
            if (num == 4) {
                white[0] = 0;
                white[1] = 0;
                white[2] = 0;
                white[i % 3] = 1;
                num = 0;
            }
            System.arraycopy(white, 0, colors, i * 4, white.length);
        }
        mColorBuffer = BufferUtil.creatFloatBuffer(colors);
    }

    @Override
    public void drawSelf() {
        super.drawSelf();
        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 0, 4);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 4, 4);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 8, 4);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 12, 4);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 16, 4);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_STRIP, 20, 4);
    }
}
