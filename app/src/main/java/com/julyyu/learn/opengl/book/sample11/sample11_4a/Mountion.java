package com.julyyu.learn.opengl.book.sample11.sample11_4a;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES30;

import static com.julyyu.learn.opengl.Constant.LAND_HIGHEST;
import static com.julyyu.learn.opengl.Constant.LAND_HIGH_ADJUST;

import com.julyyu.learn.opengl.MatrixState;
import com.julyyu.learn.opengl.util.ShaderUtil;

public class Mountion {
    //单位长度
    float UNIT_SIZE = 3.0f;

    //自定义渲染管线的id
    int mProgram;
    //总变化矩阵引用的id
    int muMVPMatrixHandle;
    //顶点位置属性引用id
    int maPositionHandle;
    //顶点纹理坐标属性引用id
    int maTexCoorHandle;

    //灰度图顶点纹理坐标属性引用id
    int maTexCoorHandle1;

    //草地的id
    int sTextureGrassHandle;
    //石头的id
    int sTextureRockHandle;

    //灰度图的id
    int sTextureLandHandle;/////////////////////////////////////

    //起始x值
    int landStartYYHandle;
    //长度
    int landYSpanHandle;
    //陆地的高度调整值
    int landHighAdjustHandle;
    //陆地最大高差
    int landHighestHandle;

    //顶点数据缓冲和纹理坐标数据缓冲
    FloatBuffer mVertexBuffer;
    FloatBuffer mTexCoorBuffer;

    FloatBuffer mTexCoorBuffer1;//////////////////////////////////////
    //顶点数量
    int vCount = 0;

    public Mountion(TerrainRenderer4 mv, int rows, int cols) {
        initVertexData(rows, cols);
        initShader(mv);
    }

    //初始化顶点数据
    public void initVertexData(int rows, int cols) {
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
                vertices[count++] = 0;
                vertices[count++] = zsz;

                vertices[count++] = zsx;
                vertices[count++] = 0;
                vertices[count++] = zsz + UNIT_SIZE;

                vertices[count++] = zsx + UNIT_SIZE;
                vertices[count++] = 0;
                vertices[count++] = zsz;

                vertices[count++] = zsx + UNIT_SIZE;
                vertices[count++] = 0;
                vertices[count++] = zsz;

                vertices[count++] = zsx;
                vertices[count++] = 0;
                vertices[count++] = zsz + UNIT_SIZE;

                vertices[count++] = zsx + UNIT_SIZE;
                vertices[count++] = 0;
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
        float[] texCoor = generateTexCoor(cols, rows, 16.0f);
        //创建顶点纹理坐标数据缓冲
        ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length * 4);
        cbb.order(ByteOrder.nativeOrder());//设置字节顺序
        mTexCoorBuffer = cbb.asFloatBuffer();//转换为Float型缓冲
        mTexCoorBuffer.put(texCoor);//向缓冲区中放入顶点着色数据
        mTexCoorBuffer.position(0);//设置缓冲区起始位置
        /////////////////////////////////////////////////
        //灰度图顶点纹理坐标数据的初始化
        float[] texCoor_land = generateTexCoor(cols, rows, 1.0f);
        //创建顶点纹理坐标数据缓冲
        ByteBuffer cbb1 = ByteBuffer.allocateDirect(texCoor_land.length * 4);
        cbb1.order(ByteOrder.nativeOrder());//设置字节顺序
        mTexCoorBuffer1 = cbb1.asFloatBuffer();//转换为Float型缓冲
        mTexCoorBuffer1.put(texCoor_land);//向缓冲区中放入顶点着色数据
        mTexCoorBuffer1.position(0);//设置缓冲区起始位置
    }

    //初始化着色器的方法
    public void initShader(TerrainRenderer4 mv) {
        String mVertexShader = ShaderUtil.loadFromAssetsFile("book/sample11/vertex_sample11_4a.sh", mv.getResources());
        String mFragmentShader = ShaderUtil.loadFromAssetsFile("book/sample11/frag_sample11_4a.sh", mv.getResources());
        //基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //获取程序中顶点位置属性引用id
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //获取程序中顶点纹理坐标属性引用id
        maTexCoorHandle = GLES30.glGetAttribLocation(mProgram, "aTexCoor");

        //获取程序中灰度图顶点纹理坐标属性引用id
        maTexCoorHandle1 = GLES30.glGetAttribLocation(mProgram, "aTexLandCoor");////////////////////////

        //获取程序中总变换矩阵引用id
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");

        //纹理
        //草地
        sTextureGrassHandle = GLES30.glGetUniformLocation(mProgram, "sTextureGrass");
        //石头
        sTextureRockHandle = GLES30.glGetUniformLocation(mProgram, "sTextureRock");
        //灰度图
        sTextureLandHandle = GLES30.glGetUniformLocation(mProgram, "sTextureLand");/////////////////////////////

        //x位置
        landStartYYHandle = GLES30.glGetUniformLocation(mProgram, "landStartY");
        //x最大
        landYSpanHandle = GLES30.glGetUniformLocation(mProgram, "landYSpan");
        //陆地的高度调整值
        landHighAdjustHandle = GLES30.glGetUniformLocation(mProgram, "landHighAdjust");
        //陆地最大高差
        landHighestHandle = GLES30.glGetUniformLocation(mProgram, "landHighest");
    }

    public void drawSelf(int texId, int rock_textId, int land_textId) {
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
        ////////////////////////////////////////////////////////
        //将灰度图顶点纹理坐标数据送入渲染管线
        GLES30.glVertexAttribPointer
                (
                        maTexCoorHandle1,
                        2,
                        GLES30.GL_FLOAT,
                        false,
                        2 * 4,
                        mTexCoorBuffer1
                );
        /////////////////////////////////////////////////////////////
        //启用顶点位置数据数组
        GLES30.glEnableVertexAttribArray(maPositionHandle);
        //启用纹理坐标数据数组
        GLES30.glEnableVertexAttribArray(maTexCoorHandle);

        GLES30.glEnableVertexAttribArray(maTexCoorHandle1);////////////////////////

        //绑定纹理
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);
        GLES30.glActiveTexture(GLES30.GL_TEXTURE1);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, rock_textId);

        GLES30.glActiveTexture(GLES30.GL_TEXTURE2);///////////////
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, land_textId);////////////

        GLES30.glUniform1i(sTextureGrassHandle, 0);//使用0号纹理
        GLES30.glUniform1i(sTextureRockHandle, 1); //使用1号纹理

        GLES30.glUniform1i(sTextureLandHandle, 2); //使用2号纹理///////////////////

        //传送相应的x参数
        GLES30.glUniform1f(landStartYYHandle, 0);
        GLES30.glUniform1f(landYSpanHandle, 50);
        GLES30.glUniform1f(landHighAdjustHandle, LAND_HIGH_ADJUST);
        GLES30.glUniform1f(landHighestHandle, LAND_HIGHEST);

        //绘制地形
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
    }

    //自动切分纹理产生纹理数组的方法
    public float[] generateTexCoor(int bw, int bh, float size) {
        float[] result = new float[bw * bh * 6 * 2];
        float sizew = size / bw;//列数
        float sizeh = size / bh;//行数
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