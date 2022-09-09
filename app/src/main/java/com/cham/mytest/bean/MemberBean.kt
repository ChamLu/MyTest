package com.cham.mytest.bean

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.cham.mytest.BR


/**
 *
 *  @Author       :   Cham
 *  @CreateDate   :   2020/04/27
 *  @Description  :    测试Databinding 更新某个字段值
 *
 */
data class MemberBean( val ssss:String="sssss"): BaseObservable() {
    @get:Bindable
    var memberId:String =""
    set(value) {
        field = value
        notifyPropertyChanged(BR.memberKk)
    }


    @get:Bindable
    var memberKk:String =""
    get() {
        //必须要有默认值 不然序列化的时候会有问题
        field = ""
        field = "$memberId"
        return field
    }

}