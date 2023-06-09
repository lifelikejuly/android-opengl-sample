package com.julyyu.learn.opengl.book

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.julyyu.learn.opengl.Constant
import com.julyyu.learn.opengl.book.sample10.sample10_1.MixEffectRenderer1
import com.julyyu.learn.opengl.book.sample10.sample10_1a.MixEffectRenderer1a
import com.julyyu.learn.opengl.book.sample10.sample10_1b.MixEffectRenderer1b
import com.julyyu.learn.opengl.book.sample10.sample10_2.TextureMapMoreMixRenderer
import com.julyyu.learn.opengl.book.sample10.sample10_3.FogRenderer
import com.julyyu.learn.opengl.book.sample10.sample10_4.FogRenderer2
import com.julyyu.learn.opengl.book.sample11.sample11_1.SignBoardRenderer
import com.julyyu.learn.opengl.book.sample11.sample11_2.TerrainRenderer
import com.julyyu.learn.opengl.book.sample11.sample11_3.TerrainRenderer2
import com.julyyu.learn.opengl.book.sample11.sample11_4.TerrainRenderer3
import com.julyyu.learn.opengl.book.sample11.sample11_4a.TerrainRenderer4
import com.julyyu.learn.opengl.book.sample11.sample11_5.SkyBoxRenderer
import com.julyyu.learn.opengl.book.sample11.sample11_6.SkyDomeRenderer
import com.julyyu.learn.opengl.book.sample3.sample3_1.TriangleGLRenderer
import com.julyyu.learn.opengl.book.sample5.sample5_1.SixStartGLRenderer
import com.julyyu.learn.opengl.book.sample5.sample5_10.CircleGLDrawRangeElementsRenderer
import com.julyyu.learn.opengl.book.sample5.sample5_11.LayoutRenderer
import com.julyyu.learn.opengl.book.sample5.sample5_12.VertexAttribRenderer
import com.julyyu.learn.opengl.book.sample5.sample5_13.PerspectiveGLRenderer
import com.julyyu.learn.opengl.book.sample5.sample5_14.FrustumMRenderer
import com.julyyu.learn.opengl.book.sample5.sample5_15.OffsetRenderer
import com.julyyu.learn.opengl.book.sample5.sample5_16.ClipRenderer
import com.julyyu.learn.opengl.book.sample5.sample5_2.SixStartGLRenderer2
import com.julyyu.learn.opengl.book.sample5.sample5_3.CubeGLRenderer
import com.julyyu.learn.opengl.book.sample5.sample5_4.CubeGLRenderer2
import com.julyyu.learn.opengl.book.sample5.sample5_5.CubeGLRenderer3
import com.julyyu.learn.opengl.book.sample5.sample5_6.PointLinerGLRenderer
import com.julyyu.learn.opengl.book.sample5.sample5_7.BeltCircleGLRenderer
import com.julyyu.learn.opengl.book.sample5.sample5_8.BeltGLRenderer
import com.julyyu.learn.opengl.book.sample5.sample5_9.BeltCircleGLDrawElementsRenderer
import com.julyyu.learn.opengl.book.sample6.sample6_1.BallRenderer
import com.julyyu.learn.opengl.book.sample6.sample6_10.Sample6_10Renderer
import com.julyyu.learn.opengl.book.sample6.sample6_2.AmbientRenderer
import com.julyyu.learn.opengl.book.sample6.sample6_3.DiffuseRenderer
import com.julyyu.learn.opengl.book.sample6.sample6_4.SpeculaRenderer
import com.julyyu.learn.opengl.book.sample6.sample6_5.LightRenderer
import com.julyyu.learn.opengl.book.sample6.sample6_6.OrientationRenderer
import com.julyyu.learn.opengl.book.sample6.sample6_7.FaceNormalVectorRenderer
import com.julyyu.learn.opengl.book.sample6.sample6_8.PointNormalVectorRenderer
import com.julyyu.learn.opengl.book.sample6.sample6_9.Sample6_9Renderer
import com.julyyu.learn.opengl.book.sample7.sample7_1.TextureMapRenderer
import com.julyyu.learn.opengl.book.sample7.sample7_10.TextureMapSampleConfigRenderer
import com.julyyu.learn.opengl.book.sample7.sample7_2.TextureMapGreenRenderer
import com.julyyu.learn.opengl.book.sample7.sample7_3.TextureMapStretchRenderer
import com.julyyu.learn.opengl.book.sample7.sample7_4.TextureMapSampleRenderer
import com.julyyu.learn.opengl.book.sample7.sample7_5.TextureMapMoreRenderer
import com.julyyu.learn.opengl.book.sample7.sample7_6.TextureMapCompressionGenderer
import com.julyyu.learn.opengl.book.sample7.sample7_7.TextureMapPointSpriteRenderer
import com.julyyu.learn.opengl.book.sample7.sample7_8.TextureMap3DRenderer
import com.julyyu.learn.opengl.book.sample7.sample7_9.TextureMap2DRenderer
import com.julyyu.learn.opengl.book.sample8.sample8_1.CylinderRenderer
import com.julyyu.learn.opengl.book.sample8.sample8_2.ConeSideRenderer
import com.julyyu.learn.opengl.book.sample8.sample8_3.TorusRenderer
import com.julyyu.learn.opengl.book.sample8.sample8_4.SpringRenderer
import com.julyyu.learn.opengl.book.sample8.sample8_5.SphereRenderer
import com.julyyu.learn.opengl.book.sample8.sample8_6.FootballCarbonRenderer
import com.julyyu.learn.opengl.book.sample8.sample8_7.BuildingRenderer
import com.julyyu.learn.opengl.book.sample9.sample9_1.LoadObjRenderer
import com.julyyu.learn.opengl.book.sample9.sample9_2.LoadObj2Renderer
import com.julyyu.learn.opengl.book.sample9.sample9_3.LoadObj3Renderer
import com.julyyu.learn.opengl.book.sample9.sample9_4.LoadObj4Renderer
import com.julyyu.learn.opengl.book.sample9.sample9_5.LoadObj5Renderer
import com.julyyu.learn.opengl.book.sample9.sample9_6.LoadObj6Renderer


/**
 * 书籍目录
 */
class OpenGLES3XActivity : AppCompatActivity() {

    var glView: GLSurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //获得系统的宽度以及高度
        //获得系统的宽度以及高度
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        if (dm.widthPixels > dm.heightPixels) {
            Constant.WIDTH = dm.widthPixels.toFloat()
            Constant.HEIGHT = dm.heightPixels.toFloat()
        } else {
            Constant.WIDTH = dm.heightPixels.toFloat()
            Constant.HEIGHT = dm.widthPixels.toFloat()
        }
        bookContents?.also { contents ->
            intent.getStringExtra("sample_title")?.also { sample ->
                val sample = contents[sample]
                intent.getStringExtra("sample_child")?.also {
                    glView =
                        sample?.get(it)?.constructors!![0].newInstance(this@OpenGLES3XActivity) as GLSurfaceView?
                    setContentView(glView)
                }
            }

        }

    }

    override fun onResume() {
        super.onResume()
        glView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        glView?.onPause()
    }


    companion object {
        val bookContents = mapOf(
            "sample3(入门)" to mapOf(
                "Sample3_1(初认识)" to TriangleGLRenderer::class.java
            ),
            "sample5(投影及变换)" to mapOf(
                "sample5_1(正交投影)" to SixStartGLRenderer::class.java,
                "sample5_2(透视投影)" to SixStartGLRenderer2::class.java,
                "sample5_3(平移)" to CubeGLRenderer::class.java,
                "sample5_4(旋转)" to CubeGLRenderer2::class.java,
                "sample5_5(缩放)" to CubeGLRenderer3::class.java,
                "sample5_6(点线绘制)" to PointLinerGLRenderer::class.java,
                "sample5_7(三角形条带和扇面绘制)" to BeltCircleGLRenderer::class.java,
                "sample5_8(扇面绘制)" to BeltGLRenderer::class.java,
                "sample5_9(DrawElement方式绘制)" to BeltCircleGLDrawElementsRenderer::class.java,
                "sample5_10(DrawRangeElements方式绘制)" to CircleGLDrawRangeElementsRenderer::class.java,
                "sample5_11(layout限定符使用)" to LayoutRenderer::class.java,
                "sample5_12(DrawRangeElements方式绘制)" to VertexAttribRenderer::class.java,
                "sample5_13(设置合理视角)" to PerspectiveGLRenderer::class.java,
                "sample5_14(设置合理透视)" to FrustumMRenderer::class.java,
                "sample5_15(多边形偏移)" to OffsetRenderer::class.java,
                "sample5_16(卷绕和背面剪裁)" to ClipRenderer::class.java
            ),
            "sample6(光照)" to mapOf(
                "sample6_1(3D球体)" to BallRenderer::class.java,
                "sample6_2(环境光)" to AmbientRenderer::class.java,
                "sample6_3(散射光)" to DiffuseRenderer::class.java,
                "sample6_4(镜面光)" to SpeculaRenderer::class.java,
                "sample6_5(定位混合光)" to LightRenderer::class.java,
                "sample6_6(定向混合光)" to OrientationRenderer::class.java,
                "sample6_7(面法向量策略)" to FaceNormalVectorRenderer::class.java,
                "sample6_8(点法向量策略)" to PointNormalVectorRenderer::class.java,
                "sample6_9(每顶点光照)" to Sample6_9Renderer::class.java,
                "sample6_10(每片元光照)" to Sample6_10Renderer::class.java
            ),
            "sample7(纹理映射)" to mapOf(
                "sample7_1(纹理映射)" to TextureMapRenderer::class.java,
                "sample7_2(纹理色彩通道)" to TextureMapGreenRenderer::class.java,
                "sample7_3(纹理拉伸)" to TextureMapStretchRenderer::class.java,
                "sample7_4(纹理采样)" to TextureMapSampleRenderer::class.java,
                "sample7_5(多重纹理)" to TextureMapMoreRenderer::class.java,
                "sample7_6(压缩纹理)" to TextureMapCompressionGenderer::class.java,
                "sample7_7(点精灵)" to TextureMapPointSpriteRenderer::class.java,
                "sample7_8(3D纹理)" to TextureMap3DRenderer::class.java,
                "sample7_9(2D纹理数组)" to TextureMap2DRenderer::class.java,
                "sample7_10(采样器配置对象使用)" to TextureMapSampleConfigRenderer::class.java
            ),
            "sample8(3D形状构建)" to mapOf(
                "sample8_1(圆柱体)" to CylinderRenderer::class.java,
                "sample8_2(圆锥体)" to ConeSideRenderer::class.java,
                "sample8_3(圆环体)" to TorusRenderer::class.java,
                "sample8_4(螺旋管)" to SpringRenderer::class.java,
                "sample8_5(几何球)" to SphereRenderer::class.java,
                "sample8_6(足球碳分子模型)" to FootballCarbonRenderer::class.java,
                "sample8_7(贝塞尔曲线旋转面)" to BuildingRenderer::class.java
            ),
            "sample9(3D模型加载)" to mapOf(
                "sample9_1(3D茶壶)" to LoadObjRenderer::class.java,
                "sample9_2(光照面法向量)" to LoadObj2Renderer::class.java,
                "sample9_3(光照点法向量)" to LoadObj3Renderer::class.java,
                "sample9_4(纹理坐标)" to LoadObj4Renderer::class.java,
                "sample9_5(加载顶点法向量)" to LoadObj5Renderer::class.java,
                "sample9_6(双面光照)" to LoadObj6Renderer::class.java
            ),
            "sample10(混合与雾)" to mapOf(
                "sample10_1(混合模式COLOR)" to MixEffectRenderer1::class.java,
                "sample10_1a(混合模式ALPHA)" to MixEffectRenderer1a::class.java,
                "sample10_1b(ETC2压缩纹理)" to MixEffectRenderer1b::class.java,
                "sample10_2(纹理混合模式实战)" to TextureMapMoreMixRenderer::class.java,
                "sample10_3(雾)" to FogRenderer::class.java,
                "sample10_4(非线性模拟雾)" to FogRenderer2::class.java
            ),
            "sample11(3D开发技巧)" to mapOf(
                "sample11_1(标志板)" to SignBoardRenderer::class.java,
                "sample11_2(灰度地形)" to TerrainRenderer::class.java,
                "sample11_3(过程纹理地形)" to TerrainRenderer2::class.java,
                "sample11_4(Mipmap地形)" to TerrainRenderer3::class.java,
                "sample11_4a(顶点着色器采样纹理地形)" to TerrainRenderer4::class.java,
                "sample11_5(天空盒)" to SkyBoxRenderer::class.java,
                "sample11_6(天空穹)" to SkyDomeRenderer::class.java
//                "sample11_1a(混合模式ALPHA)" to MixEffectRenderer1a::class.java,
//                "sample11_1b(ETC2压缩纹理)" to MixEffectRenderer1b::class.java,
//                "sample11_2(纹理混合模式实战)" to TextureMapMoreMixRenderer::class.java,
//                "sample11_3(雾)" to FogRenderer::class.java,
//                "sample11_4(非线性模拟雾)" to FogRenderer2::class.java
            )
        )
    }

}

