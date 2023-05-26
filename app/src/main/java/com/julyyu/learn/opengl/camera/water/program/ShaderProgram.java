package com.julyyu.learn.opengl.camera.water.program;

import android.content.Context;
import android.opengl.GLES20;

import com.julyyu.learn.opengl.camera.water.ShaderHelper;
import com.julyyu.learn.opengl.camera.water.TextResourceReader;



/**
 * Created by ZZR on 2017/2/20.
 */

public class ShaderProgram {

    protected final int programId;

    public ShaderProgram(String vertexShaderResourceStr,
                         String fragmentShaderResourceStr){
        programId = ShaderHelper.buildProgram(
                vertexShaderResourceStr,
                fragmentShaderResourceStr);
    }

    public ShaderProgram(Context context, int vertexShaderResourceId,
                         int fragmentShaderResourceId){
        programId = ShaderHelper.buildProgram(
                TextResourceReader.readTextFileFromResource(context,vertexShaderResourceId),
                TextResourceReader.readTextFileFromResource(context,fragmentShaderResourceId));
    }

    public void userProgram() {
        GLES20.glUseProgram(programId);
    }

    public int getShaderProgramId() {
        return programId;
    }

    public void deleteProgram() {
        GLES20.glDeleteProgram(programId);
    }
}
