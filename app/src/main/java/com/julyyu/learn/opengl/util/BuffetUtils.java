package com.julyyu.learn.opengl.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * @author julyyu
 * @date 2021/9/30.
 * description：
 */
public class BuffetUtils {

    public static FloatBuffer createFloatBuffer(float[] data,int size){
        return ByteBuffer.allocateDirect(data.length * size)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(data);
    }


    public static ShortBuffer createShortBuffer(short[] data, int size){
        return ByteBuffer.allocateDirect(data.length * size)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(data);
    }
}
