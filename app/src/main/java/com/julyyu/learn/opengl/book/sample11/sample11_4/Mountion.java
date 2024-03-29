package com.julyyu.learn.opengl.book.sample11.sample11_4;

import android.opengl.GLES30;

import com.julyyu.learn.opengl.MatrixState;
import com.julyyu.learn.opengl.util.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Mountion {
    //单位长度
    float UNIT_SIZE = 2.0f;

    //自定义渲染管线的id
    int mProgram;
    //总变化矩阵引用的id
    int muMVPMatrixHandle;
    //顶点位置属性引用id
    int maPositionHandle;
    //顶点纹理坐标属性引用id
    int maTexCoorHandle;

    //草皮纹理的引用
    int sTextureGrassHandle;
    //岩石纹理的引用
    int sTextureRockHandle;
    //过程纹理起始y坐标的引用
    int landStartYYHandle;
    //过程纹理跨度的引用
    int landYSpanHandle;

    //顶点数据缓冲和纹理坐标数据缓冲
    FloatBuffer mVertexBuffer;
    FloatBuffer mTexCoorBuffer;
    //顶点数量
    int vCount = 0;

    public Mountion(TerrainRenderer3 mv, float[][] yArray, int rows, int cols) {
        initVertexData(yArray, rows, cols);
        initShader(mv);
    }

    //初始化顶点数据的方法
    public void initVertexData(float[][] yArray, int rows, int cols) {
        //顶点坐标数据的初始化
        vCount = cols * rows * 2 * 3;//每个格子两个三角形，每个三角形3个顶点
        float vertices[] = new float[vCount * 3];//每个顶点xyz三个坐标
        int count = 0;//顶点计数器
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                //计算当前格子左上侧点坐标
                float zsx = -UNIT_SIZE * cols / 2 + i * UNIT_SIZE;
                float zsz = -UNIT_SIZE * rows / 2 + j * UNIT_SIZE;

                vertices[count++] = zsx;
                vertices[count++] = yArray[j][i];
                vertices[count++] = zsz;

                vertices[count++] = zsx;
                vertices[count++] = yArray[j + 1][i];
                vertices[count++] = zsz + UNIT_SIZE;

                vertices[count++] = zsx + UNIT_SIZE;
                vertices[count++] = yArray[j][i + 1];
                vertices[count++] = zsz;

                vertices[count++] = zsx + UNIT_SIZE;
                vertices[count++] = yArray[j][i + 1];
                vertices[count++] = zsz;

                vertices[count++] = zsx;
                vertices[count++] = yArray[j + 1][i];
                vertices[count++] = zsz + UNIT_SIZE;

                vertices[count++] = zsx + UNIT_SIZE;
                vertices[count++] = yArray[j + 1][i + 1];
                vertices[count++] = zsz + UNIT_SIZE;
            }
        }

        //创建顶点坐标数据缓冲
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mVertexBuffer = vbb.asFloatBuffer();//转换为Float型缓冲
        mVertexBuffer.put(vertices);//向缓冲区中放入顶点坐标数据
        mVertexBuffer.position(0);//设置缓冲区起始位置

        //顶点纹理坐标数据的初始化
        float[] texCoor = generateTexCoor(cols, rows);
        //创建顶点纹理坐标数据缓冲
        ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length * 4);
        cbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mTexCoorBuffer = cbb.asFloatBuffer();//转换为Float型缓冲
        mTexCoorBuffer.put(texCoor);//向缓冲区中放入顶点着色数据
        mTexCoorBuffer.position(0);//设置缓冲区起始位置
    }

    //初始化着色器的方法
    public void initShader(TerrainRenderer3 mv) {
        String mVertexShader = ShaderUtil.loadFromAssetsFile("book/sample11/vertex_sample11_3.sh", mv.getResources());
        String mFragmentShader = ShaderUtil.loadFromAssetsFile("book/sample11/frag_sample11_3.sh", mv.getResources());
        //基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //获取程序中顶点位置属性引用id
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //获取程序中顶点纹理坐标属性引用id
        maTexCoorHandle = GLES30.glGetAttribLocation(mProgram, "aTexCoor");
        //获取程序中总变换矩阵引用id
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");

        //纹理
        //草地
        sTextureGrassHandle = GLES30.glGetUniformLocation(mProgram, "sTextureGrass");
        //石头
        sTextureRockHandle = GLES30.glGetUniformLocation(mProgram, "sTextureRock");
        //x位置
        landStartYYHandle = GLES30.glGetUniformLocation(mProgram, "landStartY");
        //x最大
        landYSpanHandle = GLES30.glGetUniformLocation(mProgram, "landYSpan");
    }

    public void drawSelf(int texId, int rock_textId) {
        //指定使用某套着色器程序
        GLES30.glUseProgram(mProgram);
        //将最终变换矩阵送入渲染管线
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
        //将顶点位置数据送入渲染管线
        GLES30.glVertexAttribPointer
                (
                        maPositionHandle,
                        3,
                        GLES30.GL_FLOAT,
                        false,
                        3 * 4,
                        mVertexBuffer
                );
        //将纹理坐标数据送入渲染管线
        GLES30.glVertexAttribPointer
                (
                        maTexCoorHandle,
                        2,
                        GLES30.GL_FLOAT,
                        false,
                        2 * 4,
                        mTexCoorBuffer
                );
        //启用顶点位置、纹理坐标数据数组
        GLES30.glEnableVertexAttribArray(maPositionHandle);
        GLES30.glEnableVertexAttribArray(maTexCoorHandle);

        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);//绑定草皮纹理
        GLES30.glActiveTexture(GLES30.GL_TEXTURE1);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, rock_textId);//绑定岩石纹理
        GLES30.glUniform1i(sTextureGrassHandle, 0);//草皮纹理编号为0
        GLES30.glUniform1i(sTextureRockHandle, 1); //岩石纹理编号为1

        GLES30.glUniform1f(landStartYYHandle, 0);//传送过程纹理起始y坐标
        GLES30.glUniform1f(landYSpanHandle, 30);//传送过程纹理跨度

        //绘制地形
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
    }

    //自动切分纹理产生纹理数组的方法
    public float[] generateTexCoor(int bw, int bh) {
        float[] result = new float[bw * bh * 6 * 2];
        float sizew = 16.0f / bw;//列数
        float sizeh = 16.0f / bh;//行数
        int c = 0;
        for (int i = 0; i < bh; i++) {
            for (int j = 0; j < bw; j++) {
                //每行列一个矩形，由两个三角形构成，共六个点，12个纹理坐标
                float s = j * sizew;
                float t = i * sizeh;

                result[c++] = s;
                result[c++] = t;

                result[c++] = s;
                result[c++] = t + sizeh;

                result[c++] = s + sizew;
                result[c++] = t;

                result[c++] = s + sizew;
                result[c++] = t;

                result[c++] = s;
                result[c++] = t + sizeh;

                result[c++] = s + sizew;
                result[c++] = t + sizeh;
            }
        }
        return result;
    }
}