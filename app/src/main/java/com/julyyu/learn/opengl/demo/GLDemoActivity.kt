package com.julyyu.learn.opengl.demo

import android.R
import android.content.Intent
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.julyyu.learn.opengl.demo.matrix.AddMatrixGLRenderer
import com.julyyu.learn.opengl.demo.matrix.MatrixSquareGLRenderer
import com.julyyu.learn.opengl.demo.matrix.ChangeMatrixSquareGLRenderer
import com.julyyu.learn.opengl.demo.pointlinesurface.PointGLRenderer
import com.julyyu.learn.opengl.demo.texturemap.*
import com.julyyu.learn.opengl.demo.texurecut.TextureCutGLView
import com.julyyu.learn.opengl.demo.pointlinesurface.PointLineGLRenderer
import com.julyyu.learn.opengl.demo.pointlinesurface.SurfaceGLRenderer
import com.julyyu.learn.opengl.demo.projectionmode.OrthographicProjectionGLRenderer
import com.julyyu.learn.opengl.demo.projectionmode.PerspectiveProjectionGLRenderer
import com.julyyu.learn.opengl.samplex.samplex_2.SampleX2GLRenderer
import com.julyyu.learn.opengl.samplex.samplex_3.SampleX3GLRenderer
import com.julyyu.learn.opengl.samplex.samplex_4.SampleX4GLRenderer
import com.julyyu.learn.opengl.samplex.samplex_5.SampleX5GLRenderer
import com.julyyu.learn.opengl.samplex.samplex_6.SampleX6GLRenderer
import com.julyyu.learn.opengl.samplex.samplex_7.SampleX7GLRenderer


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
            "点线面" to mapOf(
                "点" to PointGLRenderer::class.java,
                "线段" to PointLineGLRenderer::class.java,
                "面" to SurfaceGLRenderer::class.java,
                "形状面" to SampleX2GLRenderer::class.java
            ),
            "矩阵" to mapOf(
                "增加矩阵应用" to AddMatrixGLRenderer::class.java,
                "矩阵正方形" to MatrixSquareGLRenderer::class.java,
                "矩阵变化" to ChangeMatrixSquareGLRenderer::class.java
            ),
            "投影模式" to mapOf(
                "正交投影" to OrthographicProjectionGLRenderer::class.java,
                "透视投影" to PerspectiveProjectionGLRenderer::class.java
            ),
            "纹理贴图" to mapOf(
                "纹理映射贴图" to TextureMapGLView::class.java,
                "纹理映射贴图+双纹理合并" to TextureMapGLMoreTextureView::class.java,
                "纹理映射贴图+双纹理叠加" to TextureMapGLOverLayView::class.java,
                "纹理映射贴图+FBO矩阵变化" to TextureMapGLMatrixView::class.java,
                "纹理映射贴图+FBO带灰色滤镜" to TextureMapGLColorEffectView::class.java,
                "纹理映射贴图+混合模式" to TextureMapGLMixModeView::class.java,
                "纹理映射贴图+裁切特效" to TextureCutGLView::class.java,
                "纹理映射贴图+纹理转动" to SampleX5GLRenderer::class.java,
                "纹理映射贴图+纹理特效集合" to SampleX6GLRenderer::class.java,
                "纹理映射贴图+纹理特效灰度" to SampleX7GLRenderer::class.java
            ),
            "3D" to mapOf(
                "3D魔方" to SampleX3GLRenderer::class.java,
                "3D正方形" to SampleX4GLRenderer::class.java
            )
        )
    }


    var glView: GLSurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.hasExtra("demo_child") && intent.hasExtra("demo")) {
            glView =
                glDemos[intent.getStringExtra("demo")]!![intent.getStringExtra("demo_child")]!!.constructors!![0].newInstance(
                    this@GLDemoActivity
                ) as GLSurfaceView?
            setContentView(RelativeLayout(this@GLDemoActivity).apply {
                addView(glView)
//                addView(Button(this@GLDemoActivity).apply {
//                    text = "获取画面bitmap"
//                    setOnClickListener {
//                        glView?.apply {
//                            val width: Int = getWidth()
//                            val height: Int = getHeight()
//                            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//                            queueEvent(Runnable { // 创建一个ByteBuffer对象来存储像素数据
//                                val buffer: ByteBuffer =
//                                    ByteBuffer.allocateDirect(width * height * 4)
//                                buffer.order(ByteOrder.nativeOrder())
//
//                                // 从OpenGL渲染缓冲区中读取像素数据
//                                GLES20.glReadPixels(
//                                    0,
//                                    0,
//                                    width,
//                                    height,
//                                    GLES20.GL_RGBA,
//                                    GLES20.GL_UNSIGNED_BYTE,
//                                    buffer
//                                )
//
//                                // 将像素数据复制到Bitmap对象中
//                                buffer.rewind()
//                                bitmap.copyPixelsFromBuffer(buffer)
//                                print("!!!!")
//                            })
//                        }
//
//                    }
//                }, RelativeLayout.LayoutParams(-2, -2).apply {
//                    addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
//                })
            })
        } else {
            intent.getStringExtra("demo").also { demo ->
                setContentView(ListView(this@GLDemoActivity).apply {
                    adapter =
                        ArrayAdapter<String>(
                            this@GLDemoActivity,
                            R.layout.simple_list_item_1,
                            glDemos[demo]!!.keys.toList()
                        )
                    setOnItemClickListener { parent, view, position, id ->
                        Intent(this@GLDemoActivity, GLDemoActivity::class.java).run {
                            putExtra("demo", demo)
                            putExtra("demo_child", glDemos[demo]!!.keys.toList()[position])
                            startActivity(this)
                        }
                    }
                })
            }
        }

    }
}