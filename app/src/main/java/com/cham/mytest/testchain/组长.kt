package com.cham.mytest.testchain

import android.util.Log

class 组长 : IHandler {

    override fun handlerLeave(leave: ILeave) {


        Log.e("组长处理",leave.getName()+leave.getContent())

    }
}