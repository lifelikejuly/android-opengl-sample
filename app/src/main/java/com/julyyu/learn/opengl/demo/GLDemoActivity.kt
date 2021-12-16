package com.julyyu.learn.opengl.demo

import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.julyyu.learn.opengl.demo.texturemap.TextureMapGLView
import com.julyyu.learn.opengl.demo.texurecut.TextureCutGLView

/**
 * @author julyyu
 * @date 2021/9/22.
 * description：
 *
 *
 * 1.纹理贴图
 * 2.openGL渲染封装
 * 3.多个纹理贴图
 * 4.FBO纹理渲染
 *
 */


class GLDemoActivity : AppCompatActivity() {

    var glView: GLSurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra("demo").run {
            when (this) {
                "纹理映射贴图" -> {
                    glView = TextureMapGLView(this@GLDemoActivity)
                }
                "纹理映射贴图裁切特效" -> {
                    glView = TextureCutGLView(this@GLDemoActivity)
                }
            }
            setContentView(glView)
        }
    }
}