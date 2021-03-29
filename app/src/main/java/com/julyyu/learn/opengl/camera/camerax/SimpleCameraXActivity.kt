//package com.julyyu.learn.opengl.camera.camerax
//
///**
// * @Author: JulyYu
// * @CreateDate: 2020-03-06
// */
//
//import android.os.Bundle
//import androidx.annotation.Size
//import androidx.appcompat.app.AppCompatActivity
//import androidx.camera.camera2.Camera2Config
//import androidx.camera.core.CameraSelector
//import androidx.camera.core.CameraXConfig
//import androidx.camera.core.Preview
//import androidx.camera.core.impl.PreviewConfig
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.core.content.ContextCompat
//import com.julyyu.learn.opengl.R
//import kotlinx.android.synthetic.main.activity_camerax.*
//
//
//class SimpleCameraXActivity : AppCompatActivity(), CameraXConfig.Provider {
//
//
//    override fun getCameraXConfig(): CameraXConfig {
//        return Camera2Config.defaultConfig()
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_simple_camerax)
//        startCamera()
//
//    }
//
//    private fun startCamera() {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//
//        cameraProviderFuture.addListener(Runnable {
//            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
//            val preview = Preview.Builder()
//                .build()
//            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//            try {
//                cameraProvider.unbindAll()
//                cameraProvider.bindToLifecycle(
//                    this, cameraSelector, preview
//                )
//                preview.setSurfaceProvider(view_finder.surfaceProvider)
//            } catch (exc: Exception) {
//                exc?.toString()
//            }
//        }, ContextCompat.getMainExecutor(this))
//
//
//    }
//
//
//}