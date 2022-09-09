package com.cham.service

import android.app.IntentService
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/7/13 18:00
 * @UpdateUser:
 * @UpdateDate:     2021/7/13 18:00
 * @UpdateRemark:
 */
class 后台服务 :IntentService("intentservice"){
    override fun onHandleIntent(intent: Intent?) {

        GlobalScope.launch {
            flow<Int> {
                while (true) {
                    delay(5 * 1000L)
                    emit(1)
                }
            }.flowOn(Dispatchers.IO)
                .collect {
                    Log.e("TAG", "onHandleIntent: "+ it  )

                }
        }


    }
}