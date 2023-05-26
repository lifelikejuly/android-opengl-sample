package com.julyyu.learn.opengl

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.julyyu.learn.opengl.camera.camerax.GLCameraXActivity
import com.julyyu.learn.opengl.camera.demo.CameraDemoActivity
import com.julyyu.learn.opengl.camera.water.ContinuousRecordActivity
import com.julyyu.learn.opengl.demo.GLDemoActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var datas = arrayOf(
        "Triangle",
        "SixStar",
        "SixStar2",
        "Cube",
        "Cube2",
        "Cube3",
        "PointOrLine",
        "BeltCircle",
        "Belt",
        "BeltCircleGLDrawElements",
        "CircleGLDrawRangeElementsRenderer",
        "LayoutRenderer",
        "VertexAttrib",
        "Perspective",
        "FrustumM",
        "Offset",
        "Clip",
        "Ball",
        "Ambient",
        "Diffuse",

        "TextureMap",
        "TextureMapGreen",
        "TextureMapStretch",
        "TextureMapSample",
        "TextureMapMore",
        "TextureMapCompress",
        "TextureMapPointSprite",
        "TextureMap3D",
        "TextureMap2D",
        "TextureMapSampleConfig",
        "Cylinder",
        "ConeSide",
        "Torus",
        "Spring",
        "Sphere",
        "FootballCarbon",
        "Building",
        "LoadObj",
        "LoadObj2",
        "LoadObj3",
        "LoadObj4",
        "LoadObj5",
        "LoadObj6",
        "SampleX1",
        "SampleX2",
        "SampleX3",
        "SampleX4",
        "SampleX5",
        "SampleX6",
        "Camera"
    )



    val demo = HashMap<String, Array<String>>().also {
        it["书籍Demo"] = arrayOf(
            "Triangle",
            "SixStar",
            "SixStar2",
            "Cube",
            "Cube2",
            "Cube3",
            "PointOrLine",
            "BeltCircle",
            "Belt",
            "BeltCircleGLDrawElements",
            "CircleGLDrawRangeElementsRenderer",
            "LayoutRenderer",
            "VertexAttrib",
            "Perspective",
            "FrustumM",
            "Offset",
            "Clip",
            "Ball",
            "Ambient",
            "Diffuse",

            "TextureMap",
            "TextureMapGreen",
            "TextureMapStretch",
            "TextureMapSample",
            "TextureMapMore",
            "TextureMapCompress",
            "TextureMapPointSprite",
            "TextureMap3D",
            "TextureMap2D",
            "TextureMapSampleConfig",
            "Cylinder",
            "ConeSide",
            "Torus",
            "Spring",
            "Sphere",
            "FootballCarbon",
            "Building",
            "LoadObj",
            "LoadObj2",
            "LoadObj3",
            "LoadObj4",
            "LoadObj5",
            "LoadObj6",
            "LoadGameBoyObj",
            "LoadSciFiBoxObj"
        )
        it["学习例子"] = arrayOf(
            "SampleX1",
            "SampleX2",
            "SampleX3",
            "SampleX4",
            "SampleX5",
            "SampleX6",
            "SampleX7",
            "SampleX8",
            "SampleX9",
            "SampleX10",
            "SampleX11",
            "SampleX12",
            "SampleX13",
            "SampleX14",
            "SampleX15"
        )
        it["相机渲染"] = arrayOf(
            "GLCameraX",
            "Camerax",
            "WaterCamera"
        )
        it["OpenGL Demo 例子"] = arrayOf(
            "纹理映射贴图",
            "纹理映射贴图2",
            "纹理映射贴图3",
            "纹理映射贴图4带灰色滤镜",
            "纹理映射贴图裁切特效"
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        glView = AppGLSurfaceView(this)
//        setContentView(glView)
        val sample = intent.getStringExtra("sample")
        if(TextUtils.isEmpty(sample)){
            list.adapter =
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, demo.keys.toList())
        }else{
            list.adapter =
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, demo[sample])
        }

        list.setOnItemClickListener { parent, view, position, id ->
            run {

                if(TextUtils.isEmpty(sample)){
                    Intent(this, MainActivity::class.java).run {
                        putExtra("sample", demo.keys.toList()[position])
                        startActivity(this)
                    }
                }else{
                    if (sample == "相机渲染") {
                        if(demo[sample]!![position]  == "WaterCamera"){
                            Intent(this, ContinuousRecordActivity::class.java).run {
                                startActivity(this)
                            }
                            return@setOnItemClickListener
                        }
                        Intent(this, GLCameraXActivity::class.java).run {
                            putExtra("gl", datas[position])
                            startActivity(this)
                        }
                        return@setOnItemClickListener
                    }else  if (sample == "OpenGL Demo 例子") {
                        Intent(this, GLDemoActivity::class.java).run {
                            putExtra("demo", demo[sample]!![position])
                            startActivity(this)
                        }
                        return@setOnItemClickListener
                    }
                    Intent(this, GLSurfaceViewActivity::class.java).run {
                        putExtra("gl", demo[sample]!![position])
                        startActivity(this)
                    }
                }
//                if (datas[position] == "Camera") {
//                    Intent(this, CameraActivity::class.java).run {
//                        putExtra("gl", datas[position])
//                        startActivity(this)
//                    }
//                } else {
//                    Intent(this, GLSurfaceViewActivity::class.java).run {
//                        putExtra("gl", datas[position])
//                        startActivity(this)
//                    }
//                }

            }
        }

    }
}
