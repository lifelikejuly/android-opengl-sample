package com.julyyu.learn.opengl.book.sample6;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * @author julyyu
 * @date 2023/6/6.
 * description：
 */
public class Sample6Renderer extends GLSurfaceView {
    protected final float TOUCH_SCALE_FACTOR = 180.0f / 320;//角度缩放比例
    protected float lightOffset = -4;//灯光的位置或方向的偏移量
    protected float mPreviousY;//上次的触控位置Y坐标
    protected float mPreviousX;//上次的触控位置X坐标


    public Sample6Renderer(Context context) {
        super(context);
    }

    double dx = 0;

    //复用触摸事件回调方法
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dx = x;
                break;
            case MotionEvent.ACTION_MOVE:
                if (y > 400) {
                    float dy = y - mPreviousY;//计算触控笔Y位移
                    float dx = x - mPreviousX;//计算触控笔X位移
                    touchEventCallback(dx * TOUCH_SCALE_FACTOR,dy * TOUCH_SCALE_FACTOR);
                }else{
                    lightOffset = (float) (Math.max(-300, Math.min(300, (x - dx))) / 300.f * -4.0f);
                }
        }
        mPreviousY = y;//记录触控笔位置
        mPreviousX = x;//记录触控笔位置
        return true;
    }

    protected void touchEventCallback(double y,double x){

    }
}
