package com.julyyu.learn.opengl.camera.demo;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;

/**
 * Author: yeliang
 * Date: 2019/9/5
 * Time: 2:25 PM
 * Description:
 */

public class GLUtil {

    private static String TAG = "GLUtil";

    private static final int BYTES_PER_FLOAT = 4;

    public static FloatBuffer getFloatBuffer(float[] array) {
        FloatBuffer buffer = ByteBuffer
                .allocateDirect(array.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
        buffer.put(array).position(0);
        return buffer;

    }

    public static int linkProgram(int vertexShaderResId, int fragmentShaderResId, Context context) {
        int vertexShader = loadShader(GL_VERTEX_SHADER, loadShaderSource(vertexShaderResId, context));

        if (vertexShader == 0) {
            Log.e(TAG, "failed to load vertexShader");
            return 0;
        }

        int fragmentShader = loadShader(GL_FRAGMENT_SHADER, loadShaderSource(fragmentShaderResId, context));

        if (fragmentShader == 0) {
            Log.e(TAG, "failed to load vertexShader");
            return 0;
        }

        int program = glCreateProgram();
        if (program == 0) {
            Log.e(TAG, "failed to create program");
        }

        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);

        glLinkProgram(program);

        int[] linked = new int[1];
        glGetProgramiv(program, GL_LINK_STATUS, linked, 0);
        if (linked[0] == 0) {
            glDeleteShader(program);
            Log.e(TAG, "failed to link program");
            return 0;
        }

        return program;
    }

    public static int loadShader(int type, String shaderSource) {
        int shader = glCreateShader(type);

        if (shader == 0) {
            return 0;
        }

        glShaderSource(shader, shaderSource);

        glCompileShader(shader);

        int[] compiled = new int[1];
        glGetShaderiv(shader, GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            Log.e(TAG, glGetShaderInfoLog(shader));
            glDeleteShader(shader);
            return 0;
        }

        return shader;
    }

    public static String loadShaderSource(int resId, Context context) {
        StringBuilder res = new StringBuilder();
        InputStream inputStream = context.getResources().openRawResource(resId);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String nextLine;

        try {
            while ((nextLine = bufferedReader.readLine()) != null) {
                res.append(nextLine);
                res.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res.toString();
    }
}
