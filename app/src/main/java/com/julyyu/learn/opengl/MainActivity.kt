package com.julyyu.learn.opengl

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.julyyu.learn.opengl.book.OpenGLES3XActivity
import com.julyyu.learn.opengl.demo.GLDemoActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    val demo = HashMap<String, Array<String>>().also {
        it["OpenGL ES 3.x游戏开发范例"] = OpenGLES3XActivity.bookContents.keys.toTypedArray()
        it["OpenGL ES 实践"] = GLDemoActivity.glDemos.keys.toTypedArray()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sample = intent.getStringExtra("sample")
        val sampleTitle = intent.getStringExtra("sample_title")
        val sampleChild = intent.getStringArrayListExtra("sample_child")
        if(sampleChild != null && sampleChild.isNotEmpty()){
            list.adapter =
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, sampleChild)
        }else if (TextUtils.isEmpty(sample)) {
            list.adapter =
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, demo.keys.toList())
        } else {
            list.adapter =
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, demo[sample])
        }

        list.setOnItemClickListener { parent, view, position, id ->
            run {
                if (TextUtils.isEmpty(sample)) {
                    Intent(this, MainActivity::class.java).run {
                        putExtra("sample", demo.keys.toList()[position])
                        startActivity(this)
                    }
                } else {
                    when (sample) {
                        "OpenGL ES 实践" -> {
                            Intent(this, GLDemoActivity::class.java).run {
                                putExtra("demo", demo[sample]!![position])
                                startActivity(this)
                            }
                            return@setOnItemClickListener
                        }
                        "OpenGL ES 3.x游戏开发范例" -> {
                            if (sampleChild == null || sampleChild.isEmpty()) {
                                Intent(this, MainActivity::class.java).run {
                                    putExtra("sample",sample)
                                    putExtra("sample_title",demo[sample]!![position])
                                    putStringArrayListExtra(
                                        "sample_child",
                                        ArrayList(OpenGLES3XActivity.bookContents[demo[sample]!![position]]!!.keys.toList())
                                    )
                                    startActivity(this)
                                }
                            } else {
                                Intent(this, OpenGLES3XActivity::class.java).run {
                                    putExtra("sample_title", sampleTitle)
                                    putExtra(
                                        "sample_child",
                                        OpenGLES3XActivity.bookContents[sampleTitle]!!.keys.toList()[position]
                                    )
                                    startActivity(this)
                                }
                            }
                        }
                    }
                }

            }
        }

    }
}
