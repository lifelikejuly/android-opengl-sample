package com.julyyu.learn.opengl.book.sample10.sample10_1b;

import android.content.Context;
import android.opengl.GLES30;

import com.julyyu.learn.opengl.MatrixState;
import com.julyyu.learn.opengl.R;
import com.julyyu.learn.opengl.book.sample10.MixEffectCommonRenderer;
import com.julyyu.learn.opengl.book.sample10.sample10_1.LoadUtil;
import com.julyyu.learn.opengl.book.sample10.sample10_1.TextureRect;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MixEffectRenderer1b extends MixEffectCommonRenderer {
    @Override
    public SceneRenderer createRenderer() {
        return new MixEffectRenderer1b.Renderer();
    }

    public MixEffectRenderer1b(Context context) {
        super(context);
    }

    class Renderer extends SceneRenderer {




        @Override
        public void glMixFunc() {

            //此处省略了绘制场景中物体的代码，读者可自行查看随书中的源代码
            GLES30.glEnable(GLES30.GL_BLEND);            //开启混合
            GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA,     //设置混合因子，其中第一个为源因子，
                    GLES30.GL_ONE_MINUS_SRC_ALPHA);        //第二个为目标因子
        }

        @Override
        public int loadTexture() {
            return BnETC2Util.initTextureETC2("book/sample10/pkm/lgq.pkm",getResources());
        }
    }
}