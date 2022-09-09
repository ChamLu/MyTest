package com.cham.mytest.testchain

import android.util.Log

class 领导 : IHandler {
    override fun handlerLeave(leave: ILeave) {

        Log.e("领导处理", leave.getName() + leave.getContent())
    }
}