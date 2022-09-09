package com.cham.mytest.ui.nestedscroll

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.NestedScrollingParent3
import androidx.core.view.NestedScrollingParentHelper
import androidx.core.view.ViewCompat
import androidx.core.view.children
import com.cham.mytest.utils.logeMsg
import kotlinx.android.synthetic.main.activity_nestedscroll.view.*

class ParentNestedScrollView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr), NestedScrollingParent3 {

    companion object {
        val TAG = "ParentNestedScrollView"
    }

    // 第一个View
    private val firstView by lazy {
        children.first()
    }

    // 现在加入第二个View
    private val twoView by lazy {
        children.toList()[1]
    }

    private var mChildHeight = 0


    private val parentHelper by lazy {
        NestedScrollingParentHelper(this)
    }

    /**
     * 子view调用 startNestedScroll()时候执行
     * */
    override fun onStartNestedScroll(child: View, target: View, axes: Int, type: Int): Boolean {
        logeMsg(
            "parent onStartNestedScroll child:" +
                    child::class.java.simpleName +
                    "\ttarget:${target::class.java.simpleName}" +
                    "\taxes:${axes == ViewCompat.SCROLL_AXIS_VERTICAL}" +
                    "\ttype:${type}", TAG
        )

        return axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    /**
     * 如果onStartNestedScroll()返回true的话,就会紧接着调用该方法 常用来做一些初始化工作
     * */
    override fun onNestedScrollAccepted(child: View, target: View, axes: Int, type: Int) {

        logeMsg(
            "parent onNestedScrollAccepted child:${child::class.java.simpleName}" +
                    "\ttarget:${target::class.java.simpleName}" +
                    "taxes:${axes}\ttype:${type}", TAG
        )
        parentHelper.onNestedScrollAccepted(child, target, axes, type)

    }

    /**
     * 当子view调用 stopNestedScroll() 时候调用
     * */
    override fun onStopNestedScroll(target: View, type: Int) {

        logeMsg(
            "parent onStopNestedScroll target:${target::class.java.simpleName}\ttype:${type}",
            TAG
        )

        parentHelper.onStopNestedScroll(target, type)
    }

    /**
     * NestedScrollingChildHelper.dispatchNestedScroll()时候调用
     * @param target:childNestedScrollView
     * @param dxConsumed: 已经消费的x距离
     *@param dyConsumed: 已经消费的y距离
     * @param dxUnconsumed: 未消费的x距离
     * @param dyUnconsumed:	未消费的y距离
     * */
    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        logeMsg(
            "NestedScrollingParent3 target:${target::class.java.simpleName}" +
                    "\tdxConsumed:${dxConsumed}" +
                    "\tdyConsumed:${dyConsumed}" +
                    "\tdxUnconsumed:${dxUnconsumed}" +
                    "\tdyUnconsumed:${dyUnconsumed}" +
                    "\ttype:${type}" +
                    "consumed:${consumed}", TAG
        )

    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {

        logeMsg(
            "NestedScrollingParent2 target:${target::class.java.simpleName}" +
                    "\tdxConsumed:${dxConsumed}" +
                    "\tdyConsumed:${dyConsumed}" +
                    "\tdxUnconsumed:${dxUnconsumed}" +
                    "\tdyUnconsumed:${dyUnconsumed}" +
                    "\ttype:${type}", TAG
        )
    }

    /**
     * 当子view调用 dispatchNestedPreScroll() 时候调用
     * */
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {

        logeMsg(
            "onNestedPreScroll 消费" + "target:${target::class.java.simpleName}" +
                    "\tdx:${dx}" +
                    "\tdy:${dy}" +
                    "\tconsumedX:${consumed[0]}" +
                    "\tconsumedY:${consumed[1]}" +
                    "\ttype:${type}" +
                    "\tfirstViewHeight:${firstView.height}" +
                    "\tscrollY:${scrollY}", TAG
        )

        /**
         * dy > 0 表示向⬆️滑动
         * dy < 0 表示向⬇️滑动
         * scrollY > 0 向上滑动 表示还有滑动空间
         * scrollY < 0 向下滑动
         */

//        logeMsg("firstView id ${firstView.id}  firstViewHeight:${firstView.height}",TAG)
//
//        logeMsg("twoView id ${twoView.id} twoViewHeight:${twoView.height}\"", TAG)
        // (dy > 0 &&  scrollY < firstView.height) 如果 向上滑动 并且 当前滑动的距离 < 第一个View的高 说明还有滑动空间
        // (dy < 0 && scrollY > 0) 如果当前向下滑动 并且还有滑动空间
        if ((dy > 0 && scrollY <= firstView.height) || (dy < 0 && scrollY > 0)) {
            // 父容器消费了多少通知子view
            //细分 fistView Height
            if (dy + scrollY > firstView.height && scrollY == firstView.height) {
                val s1 = dy + scrollY - firstView.height

                val s2 = firstView.height - scrollY
                consumed[1] = s1  //告诉子View 该滑动了
                scrollBy(0, s2)
            } else {
                consumed[1] = dy // 关键代码!!parentView正在消费事件,并且通知 childView
                scrollBy(0, dy)
            }


        }
    }

    override fun scrollTo(x: Int, y: Int) {
        //  scrollBy(0, dy) 调起滑动的
        var tempY = y

        if (tempY < 0) tempY = 0

        Log.i(TAG, "parent正在滑动:${tempY}")
        super.scrollTo(x, tempY)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var tempHeightMeasureSpec = heightMeasureSpec
        mChildHeight = 0
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(tempHeightMeasureSpec)

        children.forEach {
            //无限高度
            tempHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.UNSPECIFIED)

            // 测量子view
            measureChild(it, widthMeasureSpec, tempHeightMeasureSpec)





            mChildHeight += it.measuredHeight
        }

        logeMsg("测量 高度：$heightSize")
        setMeasuredDimension(widthSize, heightSize)
    }
}