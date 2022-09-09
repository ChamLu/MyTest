package com.cham.app

import android.app.Application


/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/4/7 18:10
 * @UpdateUser:
 * @UpdateDate:     2021/4/7 18:10
 * @UpdateRemark:
 */

class MApp : Application() {


    //单例模式
    companion object {
        const val TAG = "MApp"
        private var instance: MApp? = null
            get() {
                if (field == null) {
                    field = MApp()
                }
                return field

            }

        fun get(): MApp {
            return instance!!
        }

    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }


}

