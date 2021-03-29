//package com.julyyu.learn.opengl.camera.camerax
//
///**
// * @Author: JulyYu
// * @CreateDate: 2020-03-06
// */
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.camera.camera2.Camera2Config
//import androidx.camera.core.CameraSelector
//import androidx.camera.core.CameraXConfig
//import androidx.camera.core.Preview
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.camera.view.PreviewView
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.LifecycleOwner
//import com.google.common.util.concurrent.ListenableFuture
//import kotlinx.android.synthetic.main.activity_camerax.*
//
//class CameraXActivity : AppCompatActivity() , CameraXConfig.Provider{
//
//    private lateinit var cameraProviderFuture : ListenableFuture<ProcessCameraProvider>
//    private lateinit var previewView : PreviewView
//
//    override fun getCameraXConfig(): CameraXConfig {
//        return Camera2Config.defaultConfig()
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_camerax)
//        previewView = PreviewView(this)
//        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//        cameraProviderFuture.addListener(Runnable {
//            val cameraProvider = cameraProviderFuture.get()
//            bindPreview(cameraProvider)
//        }, ContextCompat.getMainExecutor(this))
//
//        setContentView(previewView)
//    }
//
//    private fun bindPreview(cameraProvider : ProcessCameraProvider) {
//
//
//
//        var preview : Preview = Preview.Builder()
//            .build()
//        preview.also {
//        }
////        preview.onPreviewOutputUpdateListener = Preview.OnPreviewOutputUpdateListener {
////            val viewFinder =
////                viewFinderRef.get() ?: return@OnPreviewOutputUpdateListener
////
////            // To update the SurfaceTexture, we have to remove it and re-add it
////            val parent = viewFinder.parent as ViewGroup
////            parent.removeView(viewFinder)
////            parent.addView(viewFinder, 0)
////
////            Log.d("zhy", "OnPreviewOutputUpdateListener")
////
////            // 启用下面的代码正常显示内容
////            // viewFinder.surfaceTexture = it.surfaceTexture
////
////            // 启用下面的代码，走 GL 线程，图像经过黑白滤镜处理
////            viewFinder.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
////
////                override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
////                }
////
////                override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
////                }
////
////                override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
////                    return true
////                }
////
////                override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
////                    mOESTextureId = TextureDrawer.createOESTextureObject()
////                    mRenderer.init(viewFinder, mOESTextureId)
////                    mRenderer.initOESTexture(it.surfaceTexture)
////                }
////            }
////
////            bufferRotation = it.rotationDegrees
////            val rotation = getDisplaySurfaceRotation(viewFinder.display)
////            updateTransform(viewFinder, rotation, it.textureSize, viewFinderDimens)
////        }
//
//
//        var cameraSelector : CameraSelector = CameraSelector.Builder()
//            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
//            .build()
//        preview.setSurfaceProvider(previewView.surfaceProvider)
//
//
//        var camera = cameraProvider.bindToLifecycle(this as LifecycleOwner, cameraSelector, preview)
//    }
//
//}