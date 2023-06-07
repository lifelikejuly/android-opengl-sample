package com.julyyu.learn.opengl.book.sample10.sample10_1;

import android.content.Context;
import android.opengl.GLES30;

import com.julyyu.learn.opengl.R;
import com.julyyu.learn.opengl.book.sample10.MixEffectCommonRenderer;

public class MixEffectRenderer1 extends MixEffectCommonRenderer {
    @Override
    public SceneRenderer createRenderer() {
        return new Renderer();
    }

    public MixEffectRenderer1(Context context) {
        super(context);
    }

    class Renderer extends MixEffectCommonRenderer.SceneRenderer{

        @Override
        public void glMixFunc() {
            //开启混合
            GLES30.glEnable(GLES30.GL_BLEND);
            //设置混合因子,其中第一个为源因子，第二个为目标因子
            GLES30.glBlendFunc(GLES30.GL_SRC_COLOR, GLES30.GL_ONE_MINUS_SRC_COLOR);
        }

        @Override
        public int loadTexture() {
            return initTexture(R.raw.lgq);
        }
    }
}