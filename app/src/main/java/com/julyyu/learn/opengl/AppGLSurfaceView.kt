package com.julyyu.learn.opengl

import android.content.Context
import android.opengl.GLSurfaceView

/**
 *    author : JulyYu
 *    date   : 2019-05-27
 */
class AppGLSurfaceView(context: Context?) : GLSurfaceView(context) {

    lateinit var renderer : AppGLRenderer3

    init {
        setEGLContextClientVersion(3)
        renderer = AppGLRenderer3()
        setRenderer(renderer)
    }
}