package com.julyyu.learn.opengl.book.sample10.sample10_1;

import com.julyyu.learn.opengl.book.sample10.MixEffectCommonRenderer;

public class KeyThread extends Thread {

    MixEffectCommonRenderer mv;
    public static boolean flag = true;
    // 表示按钮状态的常量
    public static final int Stop = 0;
    public static final int up = 1;
    public static final int down = 2;
    public static final int left = 3;
    public static final int right = 4;

    public KeyThread(MixEffectCommonRenderer mv) {
        this.mv = mv;
    }

    public void run() {
        while (flag) {
            if (mv.rectState == up) {// 上
                mv.rectY += mv.moveSpan;
            } else if (mv.rectState == down) {// 下
                mv.rectY -= mv.moveSpan;
            } else if (mv.rectState == left) {// 左
                mv.rectX -= mv.moveSpan;
            } else if (mv.rectState == right) {// 右
                mv.rectX += mv.moveSpan;
            }
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}