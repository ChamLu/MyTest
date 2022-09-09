package com.cham.mytest.ui.testrv

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.cham.mytest.utils.logeMsg

/**
 * @Author:         Cham
 * @Description:    密封类是不能被实例化的
 *                  用来表示受限的类继承结构。
 * @CreateDate:     2020/8/5 9:53
 * @UpdateUser:
 * @UpdateDate:     2020/8/5 9:53
 * @UpdateRemark:
 */
class SealedBean : DefaultLifecycleObserver {




    fun setLifecycleB(life: LifecycleOwner){
        life.lifecycle.addObserver(this)
    }


    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        logeMsg(message = "onResume")

    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        logeMsg(message = "onPause")

    }


}