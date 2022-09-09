package com.cham.mytest.ui.eventDispatch.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import com.cham.mytest.utils.logeMsg

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/10/29 14:42
 * @UpdateUser:
 * @UpdateDate:     2021/10/29 14:42
 * @UpdateRemark:
 */
class 最外层View @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    val TAG = "最外层View"
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> logeMsg("dispatchTouchEvent ACTION_DOWN",TAG)
            MotionEvent.ACTION_MOVE -> logeMsg("dispatchTouchEvent ACTION_MOVE",TAG)
            MotionEvent.ACTION_UP -> logeMsg("dispatchTouchEvent ACTION_UP",TAG)
        }
        val flag = super.dispatchTouchEvent(event)
        logeMsg("dispatchTouchEvent return: $flag",TAG)

        return flag
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> logeMsg("onInterceptTouchEvent ACTION_DOWN",TAG)
            MotionEvent.ACTION_MOVE -> logeMsg("onInterceptTouchEvent ACTION_MOVE",TAG)
            MotionEvent.ACTION_UP -> logeMsg("onInterceptTouchEvent ACTION_UP",TAG)
        }
        val flag = super.onInterceptTouchEvent(event)
        logeMsg("onInterceptTouchEvent return: $flag",TAG)

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

        return flag
    }
}