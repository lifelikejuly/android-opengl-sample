package com.julyyu.learn.opengl.samplex.samplex_4;

import android.content.Context;

import com.julyyu.learn.opengl.BaseGLSurfaceView;

import javax.microedition.khronos.opengles.GL10;

/**
 * @Author: yuhaocan
 * @CreateDate: 2020-02-26
 */
public class SampleX4GLRenderer extends BaseGLSurfaceView<SampleX4> {


    public SampleX4GLRenderer(Context context) {
        super(context);
    }

    @Override
    public SampleX4 createSample() {
        return new SampleX4(this);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);
    }
}
