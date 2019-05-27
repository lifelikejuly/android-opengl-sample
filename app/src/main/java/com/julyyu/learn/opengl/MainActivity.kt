package com.julyyu.learn.opengl

import android.opengl.GLSurfaceView
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    var  glView : GLSurfaceView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        glView = AppGLSurfaceView(this)
        setContentView(glView)
    }
}
