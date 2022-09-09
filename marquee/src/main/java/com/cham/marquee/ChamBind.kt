package com.cham.marquee

import android.app.Activity
import android.view.View

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/3/9 16:42
 * @UpdateUser:
 * @UpdateDate:     2021/3/9 16:42
 * @UpdateRemark:
 */
object ChamBind {
    //类似ButterKnife方法
    fun bind(target: Activity) {
        val sourceView = target.window.decorView
        createBinding(target, sourceView)
    }
    private fun  createBinding(target: Activity, source: View){

        val targetClass = target::class.java
        val className = targetClass.name

        try {
            //获取类名
            val bindingClass = targetClass.classLoader!!.loadClass(className+ "_Bind")
            //获取构造方法
            val constructor = bindingClass.getConstructor(targetClass,View::class.java)
            //向方法中传入数据 activity 和 View
            constructor.newInstance(target,source)
        }catch (e:Exception){
            e.printStackTrace()
        }

    }
}