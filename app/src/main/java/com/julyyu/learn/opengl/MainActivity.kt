package com.julyyu.learn.opengl

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
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
        "SampleX1",
        "SampleX2",
        "SampleX3",
        "SampleX4",
        "10", "11", "12", "13"
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
                Intent(this, GLSurfaceViewActivity::class.java).run {
                    putExtra("gl", datas[position])
                    startActivity(this)
                }
            }
        }

    }
}
