package com.cham.mytest.worker

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2020/10/28 14:21
 * @UpdateUser:
 * @UpdateDate:     2020/10/28 14:21
 * @UpdateRemark:
 */
class MyWorker(val context: Context, private val workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    var mCount =0

    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result {
        return try {
            val data =inputData
            val mCountDown = data.getInt(Workkkk.KEY_COUNTDOWM,0)

            flow {
               while (true){
                   delay(mCountDown*1000L)
                   emit(1)
               }

            }.flowOn(Dispatchers.IO)
                .collect {
                    mCount++

                    if(mCount>6){
                        onStopped()
                    }

                    val  myWorker = OneTimeWorkRequestBuilder<M2Work>()
                        .setInputData(
                            workDataOf(
                                Workkkk.KEY_COUNTDOWM to 2)
                        ).build()
                    val  myWorker2 = OneTimeWorkRequestBuilder<M3Work>()
                        .setInputData(
                            workDataOf(
                                Workkkk.KEY_COUNTDOWM to 2)
                        ).build()

                    val s1 = listOf(myWorker,myWorker2)

                    WorkManager.getInstance().enqueue(s1)


                }

            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }


    }
}