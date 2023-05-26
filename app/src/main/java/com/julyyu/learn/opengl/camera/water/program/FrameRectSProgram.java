package com.julyyu.learn.opengl.camera.water.program;


import android.opengl.GLES20;

import com.julyyu.learn.opengl.camera.water.gles.GlUtil;


/**
 * Created by zzr on 2018/5/21.
 */

public class FrameRectSProgram extends ShaderProgram {

    private static final String VERTEX_SHADER =
                    "uniform mat4 uMVPMatrix;\n" +
                    "attribute vec4 aPosition;\n" +
                    "uniform mat4 uTexMatrix;\n" +
                    "attribute vec4 aTextureCoord;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "void main() {\n" +
                    "    gl_Position = uMVPMatrix * aPosition;\n" +
                    "    vTextureCoord = (uTexMatrix * aTextureCoord).xy;\n" +
                    "}\n";

    private static final String FRAGMENT_SHADER_EXT =
                    "#extension GL_OES_EGL_image_external : require\n" +
                    "precision mediump float;\n" +
                    "varying vec2 vTextureCoord;\n" +
                    "uniform samplerExternalOES sTextureOES;\n" +
                    "uniform sampler2D sTexture;\n" +
                    "void main() {\n" +
                    "    gl_FragColor = texture2D(sTextureOES, vTextureCoord);\n" +
                    "    //vec4 texOES = texture2D(sTextureOES, vTextureCoord);\n"+
                    "    //vec4 tex = texture2D(sTexture, vTextureCoord);\n"+
                    "    //gl_FragColor = mix(texOES, tex, 0.5);\n"+
                    "}\n";

    public FrameRectSProgram() {
        super(VERTEX_SHADER, FRAGMENT_SHADER_EXT);

        uMVPMatrixLoc = GLES20.glGetUniformLocation(programId, "uMVPMatrix");
        GlUtil.checkLocation(uMVPMatrixLoc, "uMVPMatrix");
        aPositionLoc = GLES20.glGetAttribLocation(programId, "aPosition");
        GlUtil.checkLocation(aPositionLoc, "aPosition");
        uTexMatrixLoc = GLES20.glGetUniformLocation(programId, "uTexMatrix");
        GlUtil.checkLocation(uTexMatrixLoc, "uTexMatrix");
        aTextureCoordLoc = GLES20.glGetAttribLocation(programId, "aTextureCoord");
        GlUtil.checkLocation(aTextureCoordLoc, "aTextureCoord");
        sTextureOESLoc = GLES20.glGetUniformLocation(programId, "sTextureOES");
        GlUtil.checkLocation(sTextureOESLoc, "sTextureOES");
        //sTextureLoc = GLES20.glGetUniformLocation(programId, "sTexture");
        //GlUtil.checkLocation(sTextureOESLoc, "sTexture");
    }

    public int uMVPMatrixLoc;
    public int aPositionLoc;
    public int uTexMatrixLoc;
    public int aTextureCoordLoc;
    public int sTextureOESLoc;
    public int sTextureLoc;
}
