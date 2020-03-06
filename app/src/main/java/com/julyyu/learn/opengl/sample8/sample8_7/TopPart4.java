package com.julyyu.learn.opengl.sample8.sample8_7;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.julyyu.learn.opengl.Constant;
import com.julyyu.learn.opengl.MatrixState;
import com.julyyu.learn.opengl.sample8.BezierUtil;
import com.julyyu.learn.opengl.sample8.VectorUtil;
import com.julyyu.learn.opengl.util.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

/**
 * @Author: JulyYu
 * @CreateDate: 2020-03-06
 */
public class TopPart4 {

    int mProgram;//锟皆讹拷锟斤拷锟斤拷染锟斤拷锟斤拷锟斤拷色锟斤拷锟斤拷锟斤拷id
    int muMVPMatrixHandle;//锟杰变换锟斤拷锟斤拷锟斤拷锟斤拷
    int maPositionHandle; //锟斤拷锟斤拷位锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
    int maTexCoorHandle; //锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷

    String mVertexShader;//锟斤拷锟斤拷锟斤拷色锟斤拷锟斤拷锟斤拷疟锟�
    String mFragmentShader;//片元锟斤拷色锟斤拷锟斤拷锟斤拷疟锟�

    FloatBuffer mVertexBuffer;//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟捷伙拷锟斤拷
    FloatBuffer mTexCoorBuffer;//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟捷伙拷锟斤拷

    int vCount = 0;
    float xAngle = 0;//锟斤拷x锟斤拷锟斤拷转锟侥角讹拷
    float yAngle = 0;//锟斤拷y锟斤拷锟斤拷转锟侥角讹拷
    float zAngle = 0;//锟斤拷z锟斤拷锟斤拷转锟侥角讹拷

    float scale;

    public TopPart4(GLSurfaceView mv, float scale, int nCol, int nRow) {
        this.scale = scale;
        //锟斤拷锟矫筹拷始锟斤拷锟斤拷锟斤拷锟斤拷锟捷碉拷initVertexData锟斤拷锟斤拷
        initVertexData(scale, nCol, nRow);
        //锟斤拷锟矫筹拷始锟斤拷锟斤拷色锟斤拷锟斤拷intShader锟斤拷锟斤拷
        initShader(mv);
    }

    //锟皆讹拷锟斤拷某锟绞硷拷锟斤拷锟斤拷锟斤拷锟斤拷莸姆锟斤拷锟�
    public void initVertexData(float scale, int nCol, int nRow //锟斤拷小锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷要锟斤拷证锟斤拷锟皆憋拷1锟斤拷锟斤拷锟斤拷
    ) {
        //锟斤拷员锟斤拷锟斤拷锟斤拷始锟斤拷
        float angdegSpan = 360.0f / nCol;
        vCount = 3 * nCol * nRow * 2;//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟絥Column*nRow*2锟斤拷锟斤拷锟斤拷锟轿ｏ拷每锟斤拷锟斤拷锟斤拷锟轿讹拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
        //锟斤拷锟斤拷锟斤拷锟捷筹拷始锟斤拷
        ArrayList<Float> alVertix = new ArrayList<Float>();//原锟斤拷锟斤拷锟叫憋拷锟斤拷未锟斤拷锟狡ｏ拷
        ArrayList<Integer> alFaceIndex = new ArrayList<Integer>();//锟斤拷织锟斤拷锟斤拷亩锟斤拷锟斤拷锟斤拷锟斤拷值锟叫憋拷锟斤拷锟斤拷锟斤拷时锟斤拷锟斤拷疲锟�

        //锟斤拷锟斤拷锟角憋拷锟斤拷锟斤拷锟斤拷锟竭碉拷实锟街达拷锟斤拷
        BezierUtil.al.clear();//锟斤拷锟斤拷锟斤拷莸锟斤拷斜锟�

        //锟斤拷锟斤拷锟斤拷锟捷碉拷
        BezierUtil.al.add(new BNPosition(97, 35));
        BezierUtil.al.add(new BNPosition(98, 61));
        BezierUtil.al.add(new BNPosition(100, 92));
        BezierUtil.al.add(new BNPosition(75, 121));
        BezierUtil.al.add(new BNPosition(167, 104));
        BezierUtil.al.add(new BNPosition(81, 74));
        BezierUtil.al.add(new BNPosition(97, 119));
        BezierUtil.al.add(new BNPosition(97, 130));


        //通锟斤拷锟斤拷锟捷点，锟斤拷取锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟较的碉拷锟斤拷斜锟�
        ArrayList<BNPosition> alCurve = BezierUtil.getBezierData(1.0f / nRow);
        //锟斤拷锟斤拷
        for (int i = 0; i < nRow + 1; i++) {
            double r = alCurve.get(i).x * Constant.DATA_RATIO * scale;//锟斤拷前圆锟侥半径
            float y = alCurve.get(i).y * Constant.DATA_RATIO * scale;//锟斤拷前y值
            for (float angdeg = 0; Math.ceil(angdeg) < 360 + angdegSpan; angdeg += angdegSpan)//锟截革拷锟斤拷一锟叫讹拷锟姐，锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟侥硷拷锟斤拷
            {
                double angrad = Math.toRadians(angdeg);//锟斤拷前锟叫伙拷锟斤拷
                float x = (float) (-r * Math.sin(angrad));
                float z = (float) (-r * Math.cos(angrad));
                //锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟絏YZ锟斤拷锟斤拷锟斤拷锟斤拷哦锟斤拷锟斤拷锟斤拷锟斤拷ArrayList
                alVertix.add(x);
                alVertix.add(y);
                alVertix.add(z);
            }
        }
        //锟斤拷锟斤拷
        for (int i = 0; i < nRow; i++) {
            for (int j = 0; j < nCol; j++) {
                int index = i * (nCol + 1) + j;//锟斤拷前锟斤拷锟斤拷
                //锟斤拷锟斤拷锟斤拷锟斤拷
                alFaceIndex.add(index + 1);//锟斤拷一锟斤拷---1
                alFaceIndex.add(index + nCol + 2);//锟斤拷一锟斤拷锟斤拷一锟斤拷---3
                alFaceIndex.add(index + nCol + 1);//锟斤拷一锟斤拷---2

                alFaceIndex.add(index + 1);//锟斤拷一锟斤拷---1
                alFaceIndex.add(index + nCol + 1);//锟斤拷一锟斤拷---2
                alFaceIndex.add(index);//锟斤拷前---0
            }
        }
        //锟斤拷锟斤拷锟斤拷贫锟斤拷锟�
        float[] vertices = new float[vCount * 3];
        vertices = VectorUtil.calVertices(alVertix, alFaceIndex);

        //锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟捷筹拷始锟斤拷
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟捷伙拷锟斤拷
        vbb.order(ByteOrder.nativeOrder());//锟斤拷锟斤拷锟街斤拷顺锟斤拷
        mVertexBuffer = vbb.asFloatBuffer();//转锟斤拷为float锟酵伙拷锟斤拷
        mVertexBuffer.put(vertices);//锟津缓筹拷锟斤拷锟叫凤拷锟诫顶锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
        mVertexBuffer.position(0);//锟斤拷锟矫伙拷锟斤拷锟斤拷锟斤拷始位锟斤拷

        //锟斤拷锟斤拷
        ArrayList<Float> alST = new ArrayList<Float>();//原锟斤拷锟斤拷锟叫憋拷锟斤拷未锟斤拷锟狡ｏ拷

        float yMin = 999999999;//y锟斤拷小值
        float yMax = 0;//y锟斤拷锟街�
        for (BNPosition pos : alCurve) {
            yMin = Math.min(yMin, pos.y);//y锟斤拷小值
            yMax = Math.max(yMax, pos.y);//y锟斤拷锟街�
        }
        for (int i = 0; i < nRow + 1; i++) {
            float y = alCurve.get(i).y;//锟斤拷前y值
            float t = 1 - (y - yMin) / (yMax - yMin);//t锟斤拷锟斤拷
            for (float angdeg = 0; Math.ceil(angdeg) < 360 + angdegSpan; angdeg += angdegSpan)//锟截革拷锟斤拷一锟斤拷锟斤拷锟斤拷锟斤拷锟疥，锟斤拷锟斤拷锟斤拷锟侥硷拷锟斤拷
            {
                float s = angdeg / 360;//s锟斤拷锟斤拷
                //锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟絊T锟斤拷锟斤拷锟斤拷锟斤拷哦锟斤拷锟斤拷锟斤拷锟斤拷ArrayList
                alST.add(s);
                alST.add(t);
            }
        }
        //锟斤拷锟斤拷锟斤拷坪锟斤拷锟斤拷锟斤拷锟斤拷锟�
        float[] textures = VectorUtil.calTextures(alST, alFaceIndex);
        ByteBuffer tbb = ByteBuffer.allocateDirect(textures.length * 4);//锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟捷伙拷锟斤拷
        tbb.order(ByteOrder.nativeOrder());//锟斤拷锟斤拷锟街斤拷顺锟斤拷
        mTexCoorBuffer = tbb.asFloatBuffer();//转锟斤拷为float锟酵伙拷锟斤拷
        mTexCoorBuffer.put(textures);//锟津缓筹拷锟斤拷锟叫凤拷锟诫顶锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
        mTexCoorBuffer.position(0);//锟斤拷锟矫伙拷锟斤拷锟斤拷锟斤拷始位锟斤拷
    }

    //锟皆讹拷锟斤拷锟绞硷拷锟斤拷锟缴�锟斤拷initShader锟斤拷锟斤拷
    public void initShader(GLSurfaceView mv) {
        //锟斤拷锟截讹拷锟斤拷锟斤拷色锟斤拷锟侥脚憋拷锟斤拷锟斤拷
        mVertexShader = ShaderUtil.loadFromAssetsFile("vertex_tex_sample8_7.vsh", mv.getResources());
        //锟斤拷锟斤拷片元锟斤拷色锟斤拷锟侥脚憋拷锟斤拷锟斤拷
        mFragmentShader = ShaderUtil.loadFromAssetsFile("frag_tex_sample8_7.fsh", mv.getResources());
        //锟斤拷锟节讹拷锟斤拷锟斤拷色锟斤拷锟斤拷片元锟斤拷色锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //锟斤拷取锟斤拷锟斤拷锟叫讹拷锟斤拷位锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷id
        maPositionHandle = GLES30.glGetAttribLocation(mProgram, "aPosition");
        //锟斤拷取锟斤拷锟斤拷锟叫讹拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷id
        maTexCoorHandle = GLES30.glGetAttribLocation(mProgram, "aTexCoor");
        //锟斤拷取锟斤拷锟斤拷锟斤拷锟杰变换锟斤拷锟斤拷锟斤拷锟斤拷id
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");
    }

    public void drawSelf(int texId) {
        MatrixState.rotate(xAngle, 1, 0, 0);
        MatrixState.rotate(yAngle, 0, 1, 0);
        MatrixState.rotate(zAngle, 0, 0, 1);

        //锟狡讹拷使锟斤拷某锟斤拷shader锟斤拷锟斤拷
        GLES30.glUseProgram(mProgram);

        //锟斤拷锟斤拷锟秸变换锟斤拷锟斤拷锟斤拷shader锟斤拷锟斤拷
        GLES30.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);

        //锟斤拷锟酵讹拷锟斤拷位锟斤拷锟斤拷锟斤拷
        GLES30.glVertexAttribPointer
                (
                        maPositionHandle,
                        3,
                        GLES30.GL_FLOAT,
                        false,
                        3 * 4,
                        mVertexBuffer
                );
        //锟斤拷锟酵讹拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
        GLES30.glVertexAttribPointer
                (
                        maTexCoorHandle,
                        2,
                        GLES30.GL_FLOAT,
                        false,
                        2 * 4,
                        mTexCoorBuffer
                );

        //锟斤拷锟矫讹拷锟斤拷位锟斤拷锟斤拷锟斤拷
        GLES30.glEnableVertexAttribArray(maPositionHandle);
        //锟斤拷锟矫讹拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
        GLES30.glEnableVertexAttribArray(maTexCoorHandle);

        //锟斤拷锟斤拷锟斤拷
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, texId);

        //锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vCount);
    }
}
