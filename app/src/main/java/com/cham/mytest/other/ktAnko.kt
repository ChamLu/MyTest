package com.cham.mytest.other

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 *
 *  @Author       :   Cham
 *  @CreateDate   :   2020/04/28
 *  @Description  :   ..... ANKO 里的写法
 *
 */
@RequiresApi(Build.VERSION_CODES.M)
fun android.view.View.onContextClick(
    context: CoroutineContext = Dispatchers.Main,
    returnValue: Boolean = false,
    handler: suspend CoroutineScope.(v: android.view.View?) -> Unit
) {
    setOnContextClickListener { v ->
        GlobalScope.launch(context, CoroutineStart.DEFAULT) {
            Log.e("前部分线程明： ",Thread.currentThread().name)
            handler(v)
        }
        returnValue
    }
}

fun android.view.View.onClick(
    context: CoroutineContext = Dispatchers.Main,
    handler: suspend CoroutineScope.(v: android.view.View?) -> Unit
) {
    setOnClickListener { v ->
        GlobalScope.launch(context, CoroutineStart.DEFAULT) {
            Log.e("前部分线程明： ",Thread.currentThread().name)
            handler(v)
        }
    }
}