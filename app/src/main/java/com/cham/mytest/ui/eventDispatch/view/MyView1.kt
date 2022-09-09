package com.cham.mytest.ui.eventDispatch.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.cham.mytest.utils.logeMsg

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/10/29 14:54
 * @UpdateUser:
 * @UpdateDate:     2021/10/29 14:54
 * @UpdateRemark:
 */
class MyView1 @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {


    val TAG ="子View1"



    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> logeMsg("dispatchTouchEvent ACTION_DOWN",TAG)
            MotionEvent.ACTION_MOVE -> logeMsg("dispatchTouchEvent ACTION_MOVE",TAG)
            MotionEvent.ACTION_UP -> logeMsg("dispatchTouchEvent ACTION_UP",TAG)
        }
        val flag = super.dispatchTouchEvent(event)
        logeMsg("dispatchTouchEvent return: ${flag}",TAG)

        return flag
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> logeMsg("onTouchEvent ACTION_DOWN",TAG)
            MotionEvent.ACTION_MOVE -> logeMsg("onTouchEvent ACTION_MOVE",TAG)
            MotionEvent.ACTION_UP -> logeMsg("onTouchEvent ACTION_UP",TAG)
        }
        val flag = super.onTouchEvent(event)
        logeMsg("onTouchEvent return: $flag",TAG)


        logeMsg("获取按下的时间  "+event.eventTime)
        return flag
    }
}