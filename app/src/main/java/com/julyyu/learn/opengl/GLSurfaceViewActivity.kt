package com.julyyu.learn.opengl

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.julyyu.learn.opengl.sample3.sample3_1.TriangleGLRenderer
import com.julyyu.learn.opengl.sample5.sample5_1.SixStartGLRenderer
import com.julyyu.learn.opengl.sample5.sample5_10.CircleGLDrawRangeElementsRenderer
import com.julyyu.learn.opengl.sample5.sample5_11.LayoutRenderer
import com.julyyu.learn.opengl.sample5.sample5_12.VertexAttribRenderer
import com.julyyu.learn.opengl.sample5.sample5_13.PerspectiveGLRenderer
import com.julyyu.learn.opengl.sample5.sample5_14.FrustumMRenderer
import com.julyyu.learn.opengl.sample5.sample5_15.OffsetRenderer
import com.julyyu.learn.opengl.sample5.sample5_16.ClipRenderer
import com.julyyu.learn.opengl.sample5.sample5_3.CubeGLRenderer
import com.julyyu.learn.opengl.sample5.sample5_4.CubeGLRenderer2
import com.julyyu.learn.opengl.sample5.sample5_5.CubeGLRenderer3
import com.julyyu.learn.opengl.sample5.sample5_6.PointLinerGLRenderer
import com.julyyu.learn.opengl.sample5.sample5_7.BeltCircleGLRenderer
import com.julyyu.learn.opengl.sample5.sample5_8.BeltGLRenderer
import com.julyyu.learn.opengl.sample5.sample5_9.BeltCircleGLDrawElementsRenderer
import com.julyyu.learn.opengl.sample6.sample6_1.BallRenderer
import com.julyyu.learn.opengl.sample6.sample6_2.AmbientRenderer
import com.julyyu.learn.opengl.sample6.sample6_3.DiffuseRenderer
import com.julyyu.learn.opengl.sample7.sample7_1.TextureMapRenderer
import com.julyyu.learn.opengl.sample7.sample7_10.TextureMapSampleConfigRenderer
import com.julyyu.learn.opengl.sample7.sample7_2.TextureMapGreenRenderer
import com.julyyu.learn.opengl.sample7.sample7_3.TextureMapStretchRenderer
import com.julyyu.learn.opengl.sample7.sample7_4.TextureMapSampleRenderer
import com.julyyu.learn.opengl.sample7.sample7_5.TextureMapMoreRenderer
import com.julyyu.learn.opengl.sample7.sample7_6.TextureMapCompressionGenderer
import com.julyyu.learn.opengl.sample7.sample7_7.TextureMapPointSpriteRenderer
import com.julyyu.learn.opengl.sample7.sample7_8.TextureMap3DRenderer
import com.julyyu.learn.opengl.sample7.sample7_9.TextureMap2DRenderer
import com.julyyu.learn.opengl.sample8.sample8_1.CylinderRenderer
import com.julyyu.learn.opengl.sample8.sample8_2.ConeSideRenderer
import com.julyyu.learn.opengl.sample8.sample8_3.TorusRenderer
import com.julyyu.learn.opengl.sample8.sample8_4.SpringRenderer
import com.julyyu.learn.opengl.sample8.sample8_5.SphereRenderer
import com.julyyu.learn.opengl.sample8.sample8_6.FootballCarbonRenderer
import com.julyyu.learn.opengl.sample8.sample8_7.BuildingRenderer
import com.julyyu.learn.opengl.sample9.sample9_1.LoadObjRenderer
import com.julyyu.learn.opengl.sample9.sample9_2.LoadObj2Renderer
import com.julyyu.learn.opengl.sample9.sample9_3.LoadObj3Renderer
import com.julyyu.learn.opengl.sample9.sample9_4.LoadObj4Renderer
import com.julyyu.learn.opengl.sample9.sample9_5.LoadObj5Renderer
import com.julyyu.learn.opengl.sample9.sample9_6.LoadObj6Renderer
import com.julyyu.learn.opengl.samplex.samplex_1.SampleX1GLRenderer
import com.julyyu.learn.opengl.samplex.samplex_2.SampleX2GLRenderer
import com.julyyu.learn.opengl.samplex.samplex_3.SampleX3GLRenderer
import com.julyyu.learn.opengl.samplex.samplex_4.SampleX4GLRenderer
import com.julyyu.learn.opengl.samplex.samplex_5.SampleX5GLRenderer

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
                "TextureMapStretch" -> {
                    glView = TextureMapStretchRenderer(this@GLSurfaceViewActivity)
                }
                "TextureMapSample" -> {
                    glView = TextureMapSampleRenderer(this@GLSurfaceViewActivity)
                }
                "TextureMapMore" -> {
                    glView = TextureMapMoreRenderer(this@GLSurfaceViewActivity)
                }
                "TextureMapCompress" -> {
                    glView = TextureMapCompressionGenderer(this@GLSurfaceViewActivity)
                }
                "TextureMapPointSprite" -> {
                    glView = TextureMapPointSpriteRenderer(this@GLSurfaceViewActivity)
                }
                "TextureMap3D" -> {
                    glView = TextureMap3DRenderer(this@GLSurfaceViewActivity)
                }
                "TextureMap2D" -> {
                    glView = TextureMap2DRenderer(this@GLSurfaceViewActivity)
                }
                "TextureMapSampleConfig" -> {
                    glView = TextureMapSampleConfigRenderer(this@GLSurfaceViewActivity)
                }
                "Cylinder" -> {
                    glView = CylinderRenderer(this@GLSurfaceViewActivity)
                }
                "ConeSide" -> {
                    glView = ConeSideRenderer(this@GLSurfaceViewActivity)
                }
                "Torus" ->{
                    glView = TorusRenderer(this@GLSurfaceViewActivity)
                }
                "Spring" ->{
                    glView = SpringRenderer(this@GLSurfaceViewActivity)
                }
                "Sphere" ->{
                    glView = SphereRenderer(this@GLSurfaceViewActivity)
                }
                "FootballCarbon" ->{
                    glView = FootballCarbonRenderer(this@GLSurfaceViewActivity)
                }
                "Building" ->{
                    glView = BuildingRenderer(this@GLSurfaceViewActivity)
                }
                "LoadObj" ->{
                    glView = LoadObjRenderer(this@GLSurfaceViewActivity)
                }
                "LoadObj2" ->{
                    glView = LoadObj2Renderer(this@GLSurfaceViewActivity)
                }
                "LoadObj3" ->{
                    glView = LoadObj3Renderer(this@GLSurfaceViewActivity)
                }
                "LoadObj4" ->{
                    glView = LoadObj4Renderer(this@GLSurfaceViewActivity)
                }
                "LoadObj5" ->{
                    glView = LoadObj5Renderer(this@GLSurfaceViewActivity)
                }
                "LoadObj6" ->{
                    glView = LoadObj6Renderer(this@GLSurfaceViewActivity)
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
                "SampleX5" -> {
                    glView = SampleX5GLRenderer(this@GLSurfaceViewActivity)
                }
            }
        }

        setContentView(glView)
    }

    override fun onResume() {
        super.onResume()
        glView?.onResume()
        Constant.threadFlag = true
    }

    override fun onPause() {
        super.onPause()
        glView?.onPause()
        Constant.threadFlag = false
    }

}