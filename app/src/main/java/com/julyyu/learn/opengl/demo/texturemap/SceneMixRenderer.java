package com.julyyu.learn.opengl.demo.texturemap;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.julyyu.learn.opengl.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class SceneMixRenderer implements GLSurfaceView.Renderer {

    private final Context mContext;

    private int mProgram1;
    private int mProgram2;
    private int mPositionHandle;
    private int mTexCoordHandle;
    private int mTextureHandle1;
    private int mTextureHandle2;
    private int mMVPMatrixHandle;
    private FloatBuffer mVertexBuffer;
    private FloatBuffer mTexCoordBuffer;
    private float[] mMVPMatrix = new float[16];
    private float[] mProjectionMatrix = new float[16];
    private float[] mViewMatrix = new float[16];
    private float[] mModelMatrix = new float[16];
    private int mTextureId1;
    private int mTextureId2;

    private final String vertexShaderCode1 =
        "attribute vec4 a_Position;" +
        "attribute vec2 a_TexCoord;" +
        "varying vec2 v_TexCoord;" +
        "uniform mat4 u_MVPMatrix;" +
        "void main() {" +
        "  gl_Position = u_MVPMatrix * a_Position;" +
        "  v_TexCoord = a_TexCoord;" +
        "}";

    private final String fragmentShaderCode1 =
        "precision mediump float;" +
        "uniform sampler2D u_Texture;" +
        "varying vec2 v_TexCoord;" +
        "void main() {" +
        "  gl_FragColor = texture2D(u_Texture, v_TexCoord);" +
        "}";

    private final String vertexShaderCode2 =
        "attribute vec4 a_Position;" +
        "attribute vec2 a_TexCoord;" +
        "varying vec2 v_TexCoord;" +
        "void main() {" +
        "  gl_Position = a_Position;" +
        "  v_TexCoord = a_TexCoord;" +
        "}";

    private final String fragmentShaderCode2 =
        "precision mediump float;" +
        "uniform sampler2D u_Texture;" +
        "varying vec2 v_TexCoord;" +
        "void main() {" +
        "  gl_FragColor = texture2D(u_Texture, v_TexCoord);" +
        "}";

    private final float[] vertices = {
        // 顶点坐标
        -1.0f,  1.0f, 0.0f,
        -1.0f, -1.0f, 0.0f,
         1.0f, -1.0f, 0.0f,
         1.0f,  1.0f, 0.0f,
    };

    private final float[] texCoords = {
        // 纹理坐标
        0.0f, 1.0f,
        0.0f, 0.0f,
        1.0f, 0.0f,
        1.0f, 1.0f,
    };

    public SceneMixRenderer(final Context context) {
        mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // 加载纹理
        mTextureId1 = loadTexture(R.raw.i900x1600);
        mTextureId2 = loadTexture(R.raw.i1600x900);

        // 编译着色器程序1
        int vertexShader1 = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode1);
        int fragmentShader1 = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode1);
        mProgram1 = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram1, vertexShader1);
        GLES20.glAttachShader(mProgram1, fragmentShader1);
        GLES20.glLinkProgram(mProgram1);

        // 编译着色器程序2
        int vertexShader2 = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode2);
        int fragmentShader2 = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode2);
        mProgram2 = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram2, vertexShader2);
        GLES20.glAttachShader(mProgram2, fragmentShader2);
        GLES20.glLinkProgram(mProgram2);

        // 设置顶点坐标和纹理坐标
        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());
        mVertexBuffer = bb.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);
        ByteBuffer tb = ByteBuffer.allocateDirect(texCoords.length * 4);
        tb.order(ByteOrder.nativeOrder());
        mTexCoordBuffer = tb.asFloatBuffer();
        mTexCoordBuffer.put(texCoords);
        mTexCoordBuffer.position(0);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        // 设置投影矩阵
        float ratio = (float) width / height;
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

        // 设置视图矩阵
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, 5, 0, 0, 0, 0, 1, 0);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // 绘制第一个纹理
        GLES20.glUseProgram(mProgram1);
        mPositionHandle = GLES20.glGetAttribLocation(mProgram1, "a_Position");
        mTexCoordHandle = GLES20.glGetAttribLocation(mProgram1, "a_TexCoord");
        mTextureHandle1 = GLES20.glGetUniformLocation(mProgram1, "u_Texture");
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram1, "u_MVPMatrix");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, mVertexBuffer);
        GLES20.glEnableVertexAttribArray(mTexCoordHandle);
        GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, mTexCoordBuffer);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId1);
        GLES20.glUniform1i(mTextureHandle1, 0);
        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);

        // 绘制第二个纹理
        GLES20.glUseProgram(mProgram2);
        mPositionHandle = GLES20.glGetAttribLocation(mProgram2, "a_Position");
        mTexCoordHandle = GLES20.glGetAttribLocation(mProgram2, "a_TexCoord");
        mTextureHandle2 = GLES20.glGetUniformLocation(mProgram2, "u_Texture");
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram2, "u_MVPMatrix");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, mVertexBuffer);
        GLES20.glEnableVertexAttribArray(mTexCoordHandle);
        GLES20.glVertexAttribPointer(mTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, mTexCoordBuffer);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId2);
        GLES20.glUniform1i(mTextureHandle2, 1);
        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, 1.5f, 0, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
    }

    private int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    private int loadTexture(int resourceId) {
        final int[] textureHandle = new int[1];
        GLES20.glGenTextures(1, textureHandle, 0);
        if (textureHandle[0] != 0) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            final Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), resourceId, options);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
            bitmap.recycle();
        }
        if (textureHandle[0] == 0) {
            throw new RuntimeException("Error loading texture.");
        }
        return textureHandle[0];
    }
}
