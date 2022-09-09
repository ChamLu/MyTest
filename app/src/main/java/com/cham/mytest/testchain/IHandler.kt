package com.cham.mytest.testchain

import android.util.Log

//处理者抽象
interface IHandler {

    fun handlerLeave(leave: ILeave){
        Log.e("ssss","sss"+leave.getNum())


        if(leave.getNum()>4){
            (this as 领导).handlerLeave(leave)
        }else{
            return
        }

    }
}
