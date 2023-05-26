package com.julyyu.learn.opengl.demo

import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.julyyu.learn.opengl.demo.texturemap.TextureMapGLView
import com.julyyu.learn.opengl.demo.texturemap.TextureMapGLView2
import com.julyyu.learn.opengl.demo.texturemap.TextureMapGLView3
import com.julyyu.learn.opengl.demo.texturemap.TextureMapGLView4
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
                "纹理映射贴图2" -> {
                    glView = TextureMapGLView2(this@GLDemoActivity)
                }
                "纹理映射贴图3" -> {
                    glView = TextureMapGLView3(this@GLDemoActivity)
                }
                "纹理映射贴图4带灰色滤镜" -> {
                    glView = TextureMapGLView4(this@GLDemoActivity)
                }
                "纹理映射贴图裁切特效" -> {
                    glView = TextureCutGLView(this@GLDemoActivity)
                }
            }
            setContentView(glView)
        }
    }
}