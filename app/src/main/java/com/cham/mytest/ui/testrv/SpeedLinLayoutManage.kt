package com.cham.mytest.ui.testrv

import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

/**
 *
 *  @Author       :   Cham
 *  @CreateDate   :   2020/05/14
 *  @Description  :
 *
 */
class SpeedLinLayoutManage(var context: Context) : LinearLayoutManager(context) {
    private var MILLISECONDS_PER_INCH = 0.3f
    override fun smoothScrollToPosition(
        recyclerView: RecyclerView?,
        state: RecyclerView.State?,
        position: Int
    ) {

        val smoothScroller = object : LinearSmoothScroller(recyclerView?.context) {

            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
                setSpeedSlow()
                return MILLISECONDS_PER_INCH / displayMetrics!!.density;
            }
        }

        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)

    }

    fun setSpeedSlow() {
        //自己在这里用density去乘，希望不同分辨率设备上滑动速度相同
        //0.3f是自己估摸的一个值，可以根据不同需求自己修改
        MILLISECONDS_PER_INCH = context.resources.displayMetrics.density * 0.9f
    }

    fun setSpeedFast() {
        MILLISECONDS_PER_INCH = context.resources.displayMetrics.density * 0.03f
    }

}