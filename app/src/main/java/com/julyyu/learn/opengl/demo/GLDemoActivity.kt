package com.julyyu.learn.opengl.demo

import android.graphics.Bitmap
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.julyyu.learn.opengl.demo.texturemap.TextureMapGLView
import com.julyyu.learn.opengl.demo.texturemap.TextureMapGLView2
import com.julyyu.learn.opengl.demo.texturemap.TextureMapGLView3
import com.julyyu.learn.opengl.demo.texturemap.TextureMapGLView4
import com.julyyu.learn.opengl.demo.texurecut.TextureCutGLView
import java.nio.ByteBuffer
import java.nio.ByteOrder


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

            setContentView(RelativeLayout(this@GLDemoActivity).apply {
                addView(glView)
                addView(Button(this@GLDemoActivity).apply {
                    text = "获取画面bitmap"
                    setOnClickListener {
                        glView?.apply {
                            val width: Int = getWidth()
                            val height: Int = getHeight()
                            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                            queueEvent(Runnable { // 创建一个ByteBuffer对象来存储像素数据
                                val buffer: ByteBuffer = ByteBuffer.allocateDirect(width * height * 4)
                                buffer.order(ByteOrder.nativeOrder())

                                // 从OpenGL渲染缓冲区中读取像素数据
                                GLES20.glReadPixels(
                                    0,
                                    0,
                                    width,
                                    height,
                                    GLES20.GL_RGBA,
                                    GLES20.GL_UNSIGNED_BYTE,
                                    buffer
                                )

                                // 将像素数据复制到Bitmap对象中
                                buffer.rewind()
                                bitmap.copyPixelsFromBuffer(buffer)
                                print("!!!!")
                            })
                        }

                    }
                }, RelativeLayout.LayoutParams(-2, -2).apply {
                    addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                })
            })
        }
    }
}