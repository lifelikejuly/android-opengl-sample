package com.julyyu.learn.opengl

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.julyyu.learn.opengl.sample3_1.TriangleGLRenderer
import com.julyyu.learn.opengl.sample5_1.SixStartGLRenderer
import com.julyyu.learn.opengl.sample5_10.CircleGLDrawRangeElementsRenderer
import com.julyyu.learn.opengl.sample5_11.LayoutRenderer
import com.julyyu.learn.opengl.sample5_12.VertexAttribRenderer
import com.julyyu.learn.opengl.sample5_13.PerspectiveGLRenderer
import com.julyyu.learn.opengl.sample5_14.FrustumMRenderer
import com.julyyu.learn.opengl.sample5_15.OffsetRenderer
import com.julyyu.learn.opengl.sample5_16.ClipRenderer
import com.julyyu.learn.opengl.sample5_3.CubeGLRenderer
import com.julyyu.learn.opengl.sample5_4.CubeGLRenderer2
import com.julyyu.learn.opengl.sample5_5.CubeGLRenderer3
import com.julyyu.learn.opengl.sample5_6.PointLinerGLRenderer
import com.julyyu.learn.opengl.sample5_7.BeltCircleGLRenderer
import com.julyyu.learn.opengl.sample5_8.BeltGLRenderer
import com.julyyu.learn.opengl.sample5_9.BeltCircleGLDrawElementsRenderer
import com.julyyu.learn.opengl.sample6_1.BallRenderer
import com.julyyu.learn.opengl.sample6_2.AmbientRenderer
import com.julyyu.learn.opengl.sample6_3.DiffuseRenderer
import com.julyyu.learn.opengl.sample7_1.TextureMapRenderer
import com.julyyu.learn.opengl.sample7_2.TextureMapGreenRenderer
import com.julyyu.learn.opengl.sample7_3.TextureMapStretchRenderer
import com.julyyu.learn.opengl.samplex_1.SampleX1GLRenderer
import com.julyyu.learn.opengl.samplex_2.SampleX2GLRenderer
import com.julyyu.learn.opengl.samplex_3.SampleX3GLRenderer
import com.julyyu.learn.opengl.samplex_4.SampleX4GLRenderer

/**
 * @Author:         yuhaocan
 * @CreateDate:     2020-02-14
 */
class GLSurfaceViewActivity : AppCompatActivity() {


    var glView: GLSurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.getStringExtra("gl").run {
            when (this) {
                "Triangle" -> {
                    glView = TriangleGLRenderer(this@GLSurfaceViewActivity)
                }
                "SixStar" -> {
                    glView = SixStartGLRenderer(this@GLSurfaceViewActivity)
                }
                "SixStar2" -> {
                    glView = SixStartGLRenderer(this@GLSurfaceViewActivity, false)
                }
                "Cube" -> {
                    glView = CubeGLRenderer(this@GLSurfaceViewActivity)
                }
                "Cube2" -> {
                    glView = CubeGLRenderer2(this@GLSurfaceViewActivity)
                }
                "Cube3" -> {
                    glView = CubeGLRenderer3(this@GLSurfaceViewActivity)
                }
                "PointOrLine" -> {
//                    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    glView = PointLinerGLRenderer(this@GLSurfaceViewActivity)
                }
                "BeltCircle" -> {
                    glView = BeltCircleGLRenderer(this@GLSurfaceViewActivity)
                }
                "Belt" -> {
                    glView = BeltGLRenderer(this@GLSurfaceViewActivity)
                }
                "BeltCircleGLDrawElements" -> {
                    glView = BeltCircleGLDrawElementsRenderer(this@GLSurfaceViewActivity)
                }
                "CircleGLDrawRangeElementsRenderer" -> {
                    glView = CircleGLDrawRangeElementsRenderer(this@GLSurfaceViewActivity)
                }
                "LayoutRenderer" -> {
                    glView = LayoutRenderer(this@GLSurfaceViewActivity)
                }
                "VertexAttrib" -> {
                    glView = VertexAttribRenderer(this@GLSurfaceViewActivity)
                }
                "Perspective" -> {
                    glView = PerspectiveGLRenderer(this@GLSurfaceViewActivity)
                }
                "FrustumM" -> {
                    glView = FrustumMRenderer(this@GLSurfaceViewActivity)
                }
                "Offset" -> {
                    glView = OffsetRenderer(this@GLSurfaceViewActivity)
                }
                "Clip" -> {
                    glView = ClipRenderer(this@GLSurfaceViewActivity)
                }
                "Ball" -> {
                    glView = BallRenderer(this@GLSurfaceViewActivity)
                }
                "Ambient" -> {
                    glView = AmbientRenderer(this@GLSurfaceViewActivity)
                }
                "Diffuse" -> {
                    glView = DiffuseRenderer(this@GLSurfaceViewActivity)
                }
                "TextureMap" -> {
                    glView = TextureMapRenderer(this@GLSurfaceViewActivity)
                }
                "TextureMapGreen" -> {
                    glView = TextureMapGreenRenderer(this@GLSurfaceViewActivity)
                }
                "TextureMapStretch" ->{
                    glView = TextureMapStretchRenderer(this@GLSurfaceViewActivity)
                }
                "SampleX1" -> {
                    glView = SampleX1GLRenderer(this@GLSurfaceViewActivity)
                }
                "SampleX2" -> {
                    glView = SampleX2GLRenderer(this@GLSurfaceViewActivity)
                }
                "SampleX3" -> {
                    glView = SampleX3GLRenderer(this@GLSurfaceViewActivity)
                }
                "SampleX4" -> {
                    glView = SampleX4GLRenderer(this@GLSurfaceViewActivity)
                }
            }
        }

        setContentView(glView)
    }

    override fun onResume() {
        super.onResume()
        glView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        glView?.onPause()
    }

}