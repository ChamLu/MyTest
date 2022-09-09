package com.cham.mytest.utils

import android.util.Log

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2020/8/7 17:35
 * @UpdateUser:
 * @UpdateDate:     2020/8/7 17:35
 * @UpdateRemark:
 */
fun logeMsg(message: String, tag: String = "ErrorLog") {
    Log.e(tag, message)
}