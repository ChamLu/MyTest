package com.cham.mytest


import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Bundle

import com.cham.mytest.utils.logeMsg

class MyApp : Application() {

    //单例模式
    companion object {
        const val TAG = "MyApp"
        private var instance: MyApp? = null
            get() {
                if (field == null) {
                    field = MyApp()
                }
                return field

            }

        fun get(): MyApp {
            return instance!!
        }

    }

    /**
     * 每新开一个进程  都会重走一边
     * */
    override fun onCreate() {
        super.onCreate()
//        if (getProcesssName(this) != BuildConfig.APPLICATION_ID) {
//            return
//        }
//        Log.e(TAG, "当前进程名字: " + getProcesssName(this))
        registerActivityLifecycleCallbacks(appAllLife)


    }


    fun getProcesssName(context: Context): String {
        val pid = android.os.Process.myPid()//获取进程pid
        var processName = ""
        //获取系统的ActivityManager服务
        //通过Binder去和AMS进行交互
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (appProcess in am.runningAppProcesses) {
            if (appProcess.pid == pid) {
                processName = appProcess.processName
                break
            }
        }
        return processName
    }


    val appAllLife = object : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

            logeMsg(message = "每个Aty启动", "MyApp")

        }

        override fun onActivityStarted(activity: Activity) {

        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            logeMsg(message = "每个Aty关闭")
        }

    }

}