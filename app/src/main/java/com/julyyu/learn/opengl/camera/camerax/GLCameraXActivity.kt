package com.julyyu.learn.opengl.camera.camerax

/**
 * @Author: JulyYu
 * @CreateDate: 2020-03-06
 */

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Matrix
import android.graphics.SurfaceTexture
import android.os.Bundle
import android.util.Size
import android.view.Surface
import android.view.TextureView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraX
import androidx.camera.core.Preview
import androidx.camera.core.PreviewConfig
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.android.example.cameraxbasic.fragments.Renderer
import com.android.example.cameraxbasic.fragments.TextureDrawer
import com.julyyu.learn.opengl.R
import kotlinx.android.synthetic.main.activity_camerax.*


class GLCameraXActivity : AppCompatActivity(), LifecycleOwner {

    private val REQUEST_CODE_PERMISSIONS = 10
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

    private var mOESTextureId = -1
    private var mRenderer: Renderer = Renderer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camerax)
        if (checkAllPermissionsGranted()) {
//            view_finder.post { startCamera() }
            startCamera()
        } else {
            //如果没有权限动态获取
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
        view_finder.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            updateTransform()
        }
    }

    private fun checkAllPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun updateTransform() {
        val matrix = Matrix()

        //计算取景器的中心
        val centerX = view_finder.width / 2f
        val centerY = view_finder.height / 2f

        // 纠正预览输出以适应显示旋转
        val rotationDegrees = when (view_finder.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)
        // 最后，将转换应用于我们的TextureView
        view_finder.setTransform(matrix)
    }


    private fun startCamera() {
        // 为取景器用例创建配置对象
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(Size(640, 480))
            setLensFacing(CameraX.LensFacing.BACK)
        }.build()

        // 构建取景器用例
        val preview = Preview(previewConfig)
        // 每次取景器更新时，重新计算布局
        preview.setOnPreviewOutputUpdateListener {
//            var glCameraSurfaceView = GLCameraSurfaceView(this,it.surfaceTexture)
//            setContentView(glCameraSurfaceView)
//            view_finder.surfaceTexture = it.surfaceTexture
            view_finder.surfaceTextureListener = object : TextureView.SurfaceTextureListener {

                override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
                }

                override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
                }

                override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
                    return true
                }

                override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
                    mOESTextureId = TextureDrawer.createOESTextureObject()
                    mRenderer.init(view_finder, mOESTextureId)
                    mRenderer.initOESTexture(it.surfaceTexture)
                }
            }
//            view_finder.surfaceTextureListener = glCameraSurfaceView;
//            var renderer = GLCameraXRender(this, it.surfaceTexture,view_finder)
//            view_finder.surfaceTextureListener = renderer
//            view_finder.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
//                renderer.onSurfaceTextureSizeChanged(
//                    null,
//                    v.width,
//                    v.height
//                )
//            }
        }
//        val imageCapture = ImageCapture(imageCaptureConfig)
//        findViewById<ImageButton>(R.id.capture_button).setOnClickListener {
//            val file = File(externalMediaDirs.first(), "${System.currentTimeMillis()}.jpg")
//            imageCapture.takePicture(file, executor, object : ImageCapture.OnImageSavedListener {
//                override fun onImageSaved(file: File) {
//                    val msg = "成功: ${file.absolutePath}"
//                    Log.d("CameraXApp", msg)
//                    viewFinder.post {
//                        Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onError(
//                    imageCaptureError: ImageCapture.ImageCaptureError,
//                    message: String,
//                    cause: Throwable?
//                ) {
//                    val msg = "失败: $message"
//                    Log.e("CameraXApp", msg, cause)
//                    viewFinder.post {
//                        Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//            })
//        }

        //设置图像分析管道，以计算平均像素亮度
//        val analyzerConfig = ImageAnalysisConfig.Builder().apply {
//            // 在我们的分析中，我们更关注最新图片而不是
//            //分析*每张*图像
//            setImageReaderMode(
//                ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE
//            )
//        }.build()

        // 构建图像分析用例并实例化我们的分析器
//        val analyzerUseCase = ImageAnalysis(analyzerConfig).apply {
//            setAnalyzer(executor, LuminosityAnalyzer())
//        }

        //将用例绑定到生命周期
        // 如果Android Studio抱怨“此”不是LifecycleOwner
        // 尝试重建项目或将appcompat依赖项更新为
        // 1.1.0或更高版本。
        // CameraX.bindToLifecycle(this, preview)//只是预览
        //CameraX.bindToLifecycle(this, preview,imageCapture)//可以预览可以拍照
        CameraX.bindToLifecycle(this, preview)//可以预览、拍照、分析图片亮度
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (checkAllPermissionsGranted()) {
                view_finder.post { startCamera() }
            } else {
                Toast.makeText(applicationContext, "您没有同意权限", Toast.LENGTH_SHORT).show()
            }
        }
    }


}