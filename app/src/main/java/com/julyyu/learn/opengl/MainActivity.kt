package com.julyyu.learn.opengl

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.julyyu.learn.opengl.camera.CameraActivity
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
        "Camera"

    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        glView = AppGLSurfaceView(this)
//        setContentView(glView)
        list.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas)
        list.setOnItemClickListener { parent, view, position, id ->
            run {
                if (datas[position] == "Camera") {
                    Intent(this, CameraActivity::class.java).run {
                        putExtra("gl", datas[position])
                        startActivity(this)
                    }
                } else {
                    Intent(this, GLSurfaceViewActivity::class.java).run {
                        putExtra("gl", datas[position])
                        startActivity(this)
                    }
                }

            }
        }

    }
}
