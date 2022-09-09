package com.cc.mediacodec

import android.app.Application
import android.graphics.Typeface

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()


    }

    /**
     * 可全局设置 字体
     * 定义app的style（主要是<item name="android:typeface">monospace</item>）
     * */
    private fun initTypeFace() {
        val harmonyTypeFace = Typeface.createFromAsset(assets, "fonts/HarmonyOS_Sans_Medium_Italic.ttf")

        val monospace=Typeface::class.java.getDeclaredField("MONOSPACE")

        monospace.isAccessible=true
        monospace.set(null,harmonyTypeFace)

    }
}