//package com.julyyu.learn.opengl.camera.camerax;
//
//import android.util.Size;
//
//import androidx.camera.core.CameraX;
//import androidx.camera.core.Preview;
//import androidx.camera.core.PreviewConfig;
//import androidx.lifecycle.LifecycleOwner;
//
///**
// * @author julyyu
// * @date 2021-01-20.
// * description：
// */
//public class CameraHelper {
//
//    private static CameraX.LensFacing currentFacing = CameraX.LensFacing.BACK;
//    public static  void init(LifecycleOwner lifecycleOwner, Preview.OnPreviewOutputUpdateListener listener){
//        CameraX.bindToLifecycle(lifecycleOwner,getPreview(listener));
//    }
//
//
//    public static  Preview getPreview(Preview.OnPreviewOutputUpdateListener listener){
//        PreviewConfig previewConfig = new PreviewConfig.Builder()
//                .setTargetResolution(new Size(640,480))
//                .setLensFacing(currentFacing)
//                .build();
//        Preview preview = new Preview(previewConfig);
//        preview.setOnPreviewOutputUpdateListener(listener);
//        return preview;
//
//    }
//}
