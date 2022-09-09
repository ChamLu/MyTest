package com.cham.mytest.testchain

import android.util.Log

class 主管 :IHandler {
    override fun handlerLeave(leave: ILeave) {
        Log.e("主管处理",leave.getName()+leave.getContent())
    }
}