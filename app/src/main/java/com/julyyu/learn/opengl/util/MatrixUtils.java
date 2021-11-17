package com.julyyu.learn.opengl.util;

import android.opengl.Matrix;

/**
 * @author julyyu
 * @date 2021/9/30.
 * description：
 */
public class MatrixUtils {


    public void calculateMatrix(float[] matrix,int w,int h,int width,int height){

        float sWH=w/(float)h; // 图片宽高比
        float sWidthHeight=width/(float)height; // 画布宽高比
        // 画布尺寸+图片尺寸调整
        if(width>height){ // 画布宽大于高
            if(sWH>sWidthHeight){ // 图片宽高比大于画布宽高比
                Matrix.orthoM(matrix, 0, -sWidthHeight*sWH,sWidthHeight*sWH, -1,1, 0, 10);
            }else{
                Matrix.orthoM(matrix, 0, -sWidthHeight/sWH,sWidthHeight/sWH, -1,1, 0, 10);
            }
        }else{
            if(sWH>sWidthHeight){ // 图片宽高比小于画布宽高比
                Matrix.orthoM(matrix, 0, -1, 1, -1/sWidthHeight*sWH, 1/sWidthHeight*sWH,0f, 10f);
            }else{
                Matrix.orthoM(matrix, 0, -1, 1, -sWH/sWidthHeight, sWH/sWidthHeight,0f, 10f);
            }
        }
    }
}
