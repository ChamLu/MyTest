package com.cham.mytest.worker

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.*
import com.cham.mytest.utils.logeMsg
import java.security.AccessControlContext

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2020/10/29 15:12
 * @UpdateUser:
 * @UpdateDate:     2020/10/29 15:12
 * @UpdateRemark:
 */
object Workkkk {


    const val TAG = "Workkkk"

    const val KEY_COUNTDOWM="key_countdowm"

    @JvmStatic
    val  myWorker = OneTimeWorkRequestBuilder<MyWorker>()
        .setInputData(workDataOf(
            KEY_COUNTDOWM to 2))
        .addTag(TAG)
        .build()



    @SuppressLint("RestrictedApi")
    @JvmStatic
    fun doMyWork( countDownS : Int  =2,context: Context): WorkRequest {
        //传值
//        val data = workDataOf(
//            KEY_COUNTDOWM to countDownS)
//      val  myWorker = OneTimeWorkRequestBuilder<MyWorker>()
//          .setInputData(data)
//          .build()

       logeMsg(message = ""+ myWorker.workSpec.state)
        WorkManager.getInstance(context).enqueueUniqueWork("1",ExistingWorkPolicy.REPLACE,myWorker)
        return myWorker
    }


    @SuppressLint("RestrictedApi")
    @JvmStatic
    fun cancelMyWork() {
      //  WorkManager.getInstance().cancelAllWork()






      WorkManager.getInstance().cancelAllWorkByTag(TAG)

    }

}