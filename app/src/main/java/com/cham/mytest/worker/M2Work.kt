package com.cham.mytest.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cham.mytest.utils.logeMsg

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2020/10/30 10:59
 * @UpdateUser:
 * @UpdateDate:     2020/10/30 10:59
 * @UpdateRemark:
 */
class M2Work(val context: Context, private val workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {

        return try {


            val mCountDown = inputData.getInt(Workkkk.KEY_COUNTDOWM,0)


            logeMsg("倒计时秒数： $mCountDown","M2Work")


            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }
    }
}