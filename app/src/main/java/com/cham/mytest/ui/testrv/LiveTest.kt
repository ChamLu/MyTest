package com.cham.mytest.ui.testrv

import androidx.lifecycle.MutableLiveData

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2020/8/1 16:31
 * @UpdateUser:
 * @UpdateDate:     2020/8/1 16:31
 * @UpdateRemark:
 */
class LiveTest {

    var s = MutableLiveData<String>()
    fun sss(){
        s.value="sssss"
    }
}