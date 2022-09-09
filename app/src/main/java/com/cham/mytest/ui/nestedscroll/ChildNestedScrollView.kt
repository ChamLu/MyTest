package com.cham.mytest.ui.nestedscroll

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.*
import com.cham.mytest.utils.logeMsg

/**
 * 内嵌子View 实现 NestedScrollingChild3 配合
 *  NestedScrollParent3 使用
 *
 * */
class ChildNestedScrollView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr),
    NestedScrollingChild3 {

    //偏移量
    private val mScrollOffset = IntArray(2)

    // 滚动消耗
    private val mScrollConsumed = IntArray(2)

    companion object {
        val TAG = "ChildNestedScrollView"
    }

    /**
     * 系统辅助类
     * */
    private val childHelper by lazy {
        NestedScrollingChildHelper(this).apply {
            isNestedScrollingEnabled = true
        }
    }

    /**
     * 开启/关闭 滚动视图
     *
     * 如果  isNestedScrollingEnabled = true 开启滑动，找父布局，
     * 父布局 就调用 onStartNestedScroll
     *
     * */
    override fun startNestedScroll(axes: Int, type: Int): Boolean {


        val hasParentView = childHelper.hasNestedScrollingParent(type)

        val startNestedScroll = childHelper.startNestedScroll(axes)

        val parentView = this.parent.javaClass.simpleName

        logeMsg(
            "child startNestedScroll axes:$axes type:$type   hasParentView:$hasParentView  parentView:$parentView",
            TAG
        )
        return startNestedScroll

    }


    /**
     * 停止滚动时候调用,用来通知parentView停止滚动,常在TouchEvent.ACTION_UP / ACTION_CANCLE 中调用
    tips: 代理给 NestedScrollingChildHelper.stopNestedScroll()即可

     * */
    override fun stopNestedScroll(type: Int) {
        logeMsg("child stopNestedScroll $type", TAG)
        childHelper.stopNestedScroll(type)
    }

    /**
    判断当前view是否有嵌套滑动的parentView正在接受事件
    tips:代理给 NestedScrollingChildHelper.hasNestedScrollingParent()即可

    return true:有嵌套滑动的parentView
     */

    override fun hasNestedScrollingParent(type: Int): Boolean {

        val hasNestedScrollingParent = childHelper.hasNestedScrollingParent(type)
        logeMsg(
            "child hasNestedScrollingParent type:$type  Boolean:$hasNestedScrollingParent ",
            TAG
        )

        return hasNestedScrollingParent

    }

    /**
    当前view消费滚动距离后调用该方法,把剩下的滚动距离传递给parentView,
    如果当前没有发生嵌套滚动,或者不支持嵌套滚动,那么该方法就没啥用.. 常在TouchEvent.ACTION_MOVE中调用
    tips:代理给NestedScrollingChildHelper.dispatchNestedScroll()即可

    @param dxConsumed: 已经消费的水平(x)方向距离
    @param dyConsumed: 已经消费的垂直方(y)向距离
    @param dxUnconsumed: 未消费过的水平(x)方向距离
    @param dyUnconsumed: 未消费过的垂直(y)方向距离
    @param offsetInWindow:  滑动之前和滑动之后的偏移量
    if(offsetInWindow != null){
    x = offsetInWindow[0]
    y = offsetInWindow[1]
    }
    return true: 有嵌套滚动(parentView extents NestedScrollingParent)
     */


    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int,
        consumed: IntArray
    ) {


        logeMsg(
            "child dispatchNestedScroll1 dxConsumed:$dxConsumed\tdyConsumed:$dyConsumed\tdxUnconsumed:$dxUnconsumed" +
                    "\tdyUnconsumed:$dyUnconsumed\toffsetInWindow:$offsetInWindow\ttype:$type", TAG
        )

        childHelper.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow,
            type,
            consumed
        )
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {

        return childHelper.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow,
            type
        )
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?,
        type: Int
    ): Boolean {
        Log.i(
            TAG,
            "child dispatchNestedPreScroll dx:$dx" +
                    "\tdy:$dy" +
                    "\tconsumed:${consumed?.get(0)}" +
                    "\tconsumed:${consumed?.get(1)}" +
                    "\toffsetInWindow:${offsetInWindow?.get(0)}" +
                    "\toffsetInWindow:${offsetInWindow?.get(1)}" +
                    "\ttype:$type"
        )
        return childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type)
    }

    private var lastTy = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        val tX = event.x.toInt()
        val downY = event.y.toInt()

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.i(TAG, "down touchX:$tX \t touchY:$downY")
                lastTy = downY
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, ViewCompat.TYPE_TOUCH)
            }

            MotionEvent.ACTION_MOVE -> {

                var tempY = lastTy - downY

                Log.i(
                    TAG,
                    "move tempY:${tempY}\tscrollY:$scrollY"
                )
                //分发事件给 parent 询问parent是否执行
                //true表示夫View 消费了事件
                if (dispatchNestedPreScroll(
                        0,
                        tempY,
                        mScrollConsumed,
                        mScrollOffset,
                        ViewCompat.TYPE_TOUCH
                    )
                ) {
                    logeMsg(
                        "parentView消费mScrollConsumed:${mScrollConsumed[1]} mScrollOffsetY:${mScrollOffset[1]}",
                        TAG
                    )
//                    tempY -= mScrollConsumed[1]
//
//                    if (tempY == 0) return true

                    return true
                } else {

                    logeMsg(
                        "子滑动  mScrollConsumed:${mScrollConsumed[0]}\t${mScrollConsumed[1]} tempY:$tempY  ${
                            childHelper.hasNestedScrollingParent(
                                0
                            )
                        }  ", TAG
                    )

                    scrollBy(0, tempY)
                }
                lastTy = downY
                // true 支持嵌套滚动
                if (dispatchNestedScroll(
                        0,
                        tempY,
                        0,
                        scrollY - measuredHeight,
                        mScrollOffset,
                        ViewCompat.TYPE_TOUCH
                    )
                ) {
                    Log.i("szj分发事件", "dispatchNestedScroll lastTouchY:${lastTy}")
                }
            }

            // 抬起/取消
            MotionEvent.ACTION_CANCEL,
            MotionEvent.ACTION_UP -> {
                stopNestedScroll(ViewCompat.TYPE_TOUCH)
                logeMsg("up touchX:$tX \t touchY:$downY", TAG)
            }
        }

        return true
    }

    /*
 * 作者:android 超级兵
 * 创建时间: 4/9/22 3:47 PM
 * TODO  最终xml会调用到这里..添加
 */
    override fun addView(child: View, params: ViewGroup.LayoutParams?) {
        Log.i(
            "szjCurrentViewSize1",
            "childView:${child::class.java.simpleName}\t" +
                    "childCount:$childCount"
        )

        super.addView(child, params)

        Log.e(
            "szjCurrentViewSize2",
            "childView:${child::class.java.simpleName}\t" +
                    "childCount:$childCount"
        )
    }

    @SuppressLint("LongLogTag")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var tempHeightMeasureSpec = heightMeasureSpec

        val widthSize = MeasureSpec.getSize(widthMeasureSpec)


        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        if (heightMode == MeasureSpec.UNSPECIFIED) {
            return
        }
        // 遍历所有的view 用来测量高度
        children.forEach {
            tempHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(tempHeightMeasureSpec),
                MeasureSpec.UNSPECIFIED
            )

            // 测量子view
            measureChild(it, widthMeasureSpec, tempHeightMeasureSpec)
        }
        setMeasuredDimension(widthSize, children.first().measuredHeight)
    }


    /*
     * 作者:android 超级兵
     * 创建时间: 4/7/22 5:38 PM
     * TODO 未了防止滑过头
     */
    override fun scrollTo(x: Int, y: Int) {
        var tempY = y

        if (tempY < 0) tempY = 0

        Log.i(TAG, "子滑动距离 y:$y , scrollY:$scrollY")

        super.scrollTo(x, tempY)
    }

}