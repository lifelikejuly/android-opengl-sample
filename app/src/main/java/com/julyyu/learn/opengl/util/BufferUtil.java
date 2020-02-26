package com.julyyu.learn.opengl.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * @Author: yuhaocan
 * @CreateDate: 2020-02-19
 */
public class BufferUtil {

    public static FloatBuffer creatFloatBuffer(float[] vertices) {
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序为本地操作系统顺序
        FloatBuffer mVertexBuffer = vbb.asFloatBuffer();//转换为浮点(Float)型缓冲
        mVertexBuffer.put(vertices);//在缓冲区内写入数据
        mVertexBuffer.position(0);//设置缓冲区起始位置
        return mVertexBuffer;
    }
}
