//package com.julyyu.learn.opengl.camera;
//
///**
// * @Author: JulyYu
// * @CreateDate: 2020-03-06
// */
//import android.app.Activity;
//import android.os.Bundle;
//
//import com.julyyu.learn.opengl.R;
//
//public class CameraActivity extends Activity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_camera);
//        if (null == savedInstanceState) {
//            getFragmentManager().beginTransaction()
//                    .replace(R.id.container, Camera2VideoFragment.newInstance())
//                    .commit();
//        }
//    }
//
//}