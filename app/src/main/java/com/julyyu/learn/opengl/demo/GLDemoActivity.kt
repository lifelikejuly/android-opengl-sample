package com.julyyu.learn.opengl.demo

import android.graphics.Bitmap
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.julyyu.learn.opengl.demo.texturemap.*
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


    companion object {
        val glDemos = mapOf(
            "纹理映射贴图" to TextureMapGLView::class.java,
            "纹理映射贴图+双纹理合并" to TextureMapGLMoreTextureView::class.java,
            "纹理映射贴图+矩阵变化" to TextureMapGLMatrixView::class.java,
            "纹理映射贴图+带灰色滤镜" to TextureMapGLColorEffectView::class.java,
            "纹理映射贴图+混合模式" to TextureMapGLMixModeView::class.java,
            "纹理映射贴图+裁切特效" to TextureCutGLView::class.java
        )
    }


    var glView: GLSurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra("demo").run {
            glView = glDemos[this]?.constructors!![0].newInstance(this@GLDemoActivity) as GLSurfaceView?
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