package com.cham.mytest.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.widget.NestedScrollView

/**
 * @Author:         Cham
 * @Description:    用于子类防止父类拦截子类的事件
 * @CreateDate:     2020/8/31 20:09
 * @UpdateUser:
 * @UpdateDate:     2020/8/31 20:09
 * @UpdateRemark:
 */
class DisInterceptNestedScrollView : NestedScrollView {



    constructor(context: Context):super(context){
        requestDisallowInterceptTouchEvent(true)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        requestDisallowInterceptTouchEvent(true)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        requestDisallowInterceptTouchEvent(true)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        return super.dispatchTouchEvent(ev)

    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        when(ev?.action){
            MotionEvent.ACTION_MOVE -> {
                requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                requestDisallowInterceptTouchEvent(false)
            }


        }
        return super.onTouchEvent(ev)

    }

}