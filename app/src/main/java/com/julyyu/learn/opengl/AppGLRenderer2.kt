package com.julyyu.learn.opengl

import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.util.Log
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 *    author : JulyYu
 *    date   : 2019-05-27
 */
class AppGLRenderer2 : GLSurfaceView.Renderer{

    private lateinit var mTriangle: Triangle
    private lateinit var mSquare: Square

    private val TAG = "AppGLRenderer2"


    override fun onDrawFrame(gl: GL10?) {
        Log.d(TAG,"onDrawFrame")
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
        mTriangle.draw()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.d(TAG,"onSurfaceChanged")
        GLES30.glViewport(0,0,width,height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.d(TAG,"onSurfaceCreated")

        GLES30.glClearColor(0.0f,0.0f,0.0f,1.0f)

        // initialize a triangle
        mTriangle = Triangle()
        // initialize a square
        mSquare = Square()

    }
}