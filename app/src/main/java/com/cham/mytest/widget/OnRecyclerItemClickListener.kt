package com.cham.mytest.widget

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView


/**
 *
 *  @Author       :   Cham
 *  @CreateDate   :   2020/05/16
 *  @Description  :    RecyclerView item手势检查
 *
 */
class OnRecyclerItemClickListener(val  recyclerView: RecyclerView) : RecyclerView.OnItemTouchListener {
    private val gestureDetector by lazy {
        GestureDetectorCompat(recyclerView.context, ItemTouchHelperGestureListener())
    }


    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        gestureDetector.onTouchEvent(e)
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(e);
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

    }

    private inner class ItemTouchHelperGestureListener : GestureDetector.SimpleOnGestureListener() {
        /**
         * 点击事件
         * */
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            val child: View? = recyclerView.findChildViewUnder(e!!.x, e.y)
            child?.let {
                val vh: RecyclerView.ViewHolder = recyclerView.getChildViewHolder(it)
            }

            return true
        }

        /**
         * 长按触摸屏，超过一定时长，就会触发这个事件
         * */
        override fun onLongPress(e: MotionEvent) {
            val child: View? = recyclerView.findChildViewUnder(e!!.x, e.y)
            child?.let {
                val vh: RecyclerView.ViewHolder = recyclerView.getChildViewHolder(it)

            }
            super.onLongPress(e)
        }


       override fun onScroll(
           e1: MotionEvent,
           e2: MotionEvent,
           distanceX: Float,
           distanceY: Float
       ): Boolean {





           return false
       }
    }
}