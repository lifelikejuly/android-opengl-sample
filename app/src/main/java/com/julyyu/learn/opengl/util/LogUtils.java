package com.julyyu.learn.opengl.util;

/**
 * @author julyyu
 * @date 2021/9/29.
 * description：
 */
public class LogUtils {

    public static String print(float[] floats){
        StringBuilder log = new StringBuilder();
        log.append(" \n");
       for(int i =0;i<floats.length;i++){
           if(i == floats.length - 1){
               log.append(floats[i] + "");
           }else{
               log.append(floats[i] + " , ");
           }
           if( (i+1) % 4 == 0 && i != floats.length - 1){
               log.append("\n");
           }
       }
       return log.toString();
    }
}
