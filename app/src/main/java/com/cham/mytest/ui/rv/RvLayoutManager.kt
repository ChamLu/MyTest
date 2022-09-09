package com.cham.mytest.ui.rv

import android.graphics.PointF
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.cham.mytest.utils.logeMsg
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.math.abs

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/11/8 15:17
 * @UpdateUser:
 * @UpdateDate:     2021/11/8 15:17
 * @UpdateRemark:
 */
class RvLayoutManager(var sTime: Int = 0) : RecyclerView.LayoutManager(),
    RecyclerView.SmoothScroller.ScrollVectorProvider, LifecycleEventObserver {

    var hasLayout = false

    var mOrientationHelper: OrientationHelper = OrientationHelper.createVerticalHelper(this)

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    override fun computeScrollVectorForPosition(targetPosition: Int): PointF? = null


    override fun canScrollVertically(): Boolean = itemCount > 1

    override fun canScrollHorizontally(): Boolean = false

    override fun isAutoMeasureEnabled(): Boolean = false

    /**
     * 计算剩余空间->addView()->measureView()->layoutView()
     *  先把所有的View先从RecyclerView中detach掉，然后标记为"Scrap"状态，表示这些View处于可被重用状态(非显示中)。
    实际就是把View放到了Recycler中的一个集合中。
     * */
    var itemheight: Int = 0

    var mLoop = sTime > 0
    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {

        if (state.itemCount == 0 || state.isPreLayout) {
            removeAndRecycleAllViews(recycler)
            return
        }
       if (hasLayout && !state.didStructureChange()) {
            return
        }


        var currentPosition = 0



        //轻量级的将view移除屏幕
        detachAndScrapAttachedViews(recycler)

        //开始填充view

        var offsetY = 0


        //     logeMsg("父布局高度$height itemHeight$itemheight totalSpace ${mOrientationHelper.totalSpace} Measurement${mOrientationHelper.getDecoratedMeasurement(view)} ")
        for (i in 0 until itemCount) {
            if (offsetY >= mOrientationHelper.totalSpace) {
                logeMsg(" offsetY: $offsetY  totalSpace: ${mOrientationHelper.totalSpace}   --$i  ")
                break
            }
            val viewForPosition = recycler.getViewForPosition(i)
            //添加进去，当然里面不一定每次都调用Rv 的addView 方法
            //如果是从缓存区里面找到的，只需要attachView 方法重新接上就行
            addView(viewForPosition)
            //测量item 当然也不是每次都会调用，如果里面是测量过的，而且当前尺寸没有收到更新的通知，就不会重新测量
            measureChildWithMargins(viewForPosition, 0, 0)
            if(i==0){
                itemheight = getDecoratedMeasuredHeight(viewForPosition)
            }
            offsetY += layoutItem(viewForPosition, offsetY)

        }
        hasLayout = true

        logChildCount(recycler)
    }

    private fun layoutItem(viewForPosition: View, offsetX: Int): Int {
        layoutDecoratedWithMargins(
            viewForPosition,
            0,
            offsetX,
            getDecoratedMeasuredWidth(viewForPosition),
            offsetX + itemheight
        )
        return itemheight
    }

    /**
     * 三步走 填充View-移动View-回收View
     * */
    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        var realScroll = dy
        if (childCount == 0 || dy == 0) return 0
        //向上
        if (dy > 0) {
            realScroll = fillUp(dy, recycler)
        }
        // 向下
        if (dy < 0) {
            realScroll = fillDown(dy, recycler)
        }

        //realScroll = fill(dy, recycler)

        recycler(dy, recycler)

        offsetChildrenVertical(-realScroll)

        return realScroll
    }


    private fun fillUp(dy: Int, recycler: RecyclerView.Recycler): Int {
        var fillPosition = RecyclerView.NO_POSITION


        var realScroll = dy
        while (true) {
            val bottomView = getChildAt(childCount - 1)
            val decoratedBottom = getDecoratedBottom(bottomView!!)
            if (decoratedBottom - dy < mOrientationHelper.totalSpace) {
                val position = getPosition(bottomView)
                if (!mLoop && position == itemCount - 1) {
                    break
                }

                fillPosition = if (mLoop) (position + 1) % itemCount else position + 1

                val lastViewAdd = recycler.getViewForPosition(fillPosition)
                addView(lastViewAdd)
                measureChildWithMargins(lastViewAdd, 0, 0)
                var bottomH = decoratedBottom + getDecoratedMeasuredHeight(bottomView!!)
                layoutDecoratedWithMargins(
                    lastViewAdd,
                    0,
                    decoratedBottom,
                    getDecoratedMeasuredWidth(lastViewAdd),
                    bottomH
                )
            } else {
                break
            }
        }

        val lastChild = getChildAt(childCount - 1)
        val bottom = getDecoratedBottom(lastChild!!)
        if (getPosition(lastChild) == itemCount - 1) {
            if (bottom - dy < 0) {
                realScroll = bottom
            }
        }

        return realScroll

    }

    private fun fillDown(dy: Int, recycler: RecyclerView.Recycler): Int {
        var realScroll = dy

        while (true) {
            val topView = getChildAt(0)
            var top = getDecoratedTop(topView!!)
            var bottom = top
            if (top + abs(dy) > paddingTop) {
                val position = getPosition(topView)
                if (!mLoop && position == 0) {
                    break
                }

                val addPosition =
                    if (mLoop) (position - 1 + itemCount) % itemCount else position - 1
                val addView = recycler.getViewForPosition(addPosition)
                addView(addView, 0)
                measureChildWithMargins(addView, 0, 0)

                top -= getDecoratedMeasuredHeight(addView)
                layoutDecoratedWithMargins(
                    addView,
                    0,
                    top,
                    getDecoratedMeasuredWidth(addView),
                    bottom
                )

            } else {
                break
            }
        }

        val firstChild = getChildAt(0)
        var mTop = getDecoratedTop(firstChild!!)
        if (getPosition(firstChild) == 0) {
            if (mTop + abs(dy) > mOrientationHelper.totalSpace) {
                realScroll = -(mOrientationHelper.totalSpace - mTop)
            }
        }

        return realScroll
    }

    private fun fill(dy: Int, recycler: RecyclerView.Recycler): Int {
        //将要填充的position
        var fillPosition = RecyclerView.NO_POSITION
        //可用的空间，和onLayoutChildren中的totalSpace类似
        var availableSpace = abs(dy)
        //增加一个滑动距离的绝对值，方便计算
        val absDelta = abs(dy)


        //将要填充的View的左上右下
        var left = 0
        var top = 0
        var right = 0
        var bottom = 0
        //  logeMsg("Rv可用空间：${mOrientationHelper.totalSpace}  Rv高度 $height  itemHeight $itemheight")
        //  <0为 向右(下) 滚动，>0为向左(上) 滚动
        if (dy > 0) {
            //向上  填充尾部
            //先找到最底部View
            val bottomView = getChildAt(childCount - 1)
            val decoratedBottom = getDecoratedBottom(bottomView!!)
            val position = getPosition(bottomView)
            top = decoratedBottom

            //填充尾部，那么下一个position就应该是+1
            fillPosition = position + 1

            //如果要填充的position超过合理范围并且最后一个View的
            //bottom-移动的距离 < 右边缘(width)那就要修正真实能移动的距离
            //获取锚点View的position，计算新的锚点View的position和位置
            if (fillPosition >= itemCount && decoratedBottom - absDelta < height) {
                val fixScrolled = decoratedBottom - height
                logeMsg("  fixScrolled $fixScrolled")
                return fixScrolled
            }
            //如果尾部的锚点位置减去dx还是在屏幕外，就不填充下一个View
            if (decoratedBottom - absDelta > height) {
                return dy
            }
        } else if (dy < 0) {
            //向下
            val topView = getChildAt(0)
            val decoratedTop = getDecoratedTop(topView!!)
            val position = getPosition(topView)
            bottom = decoratedTop
            //填充头部，那么上一个position就应该是-1
            fillPosition = position - 1

            //如果要填充的position超过合理范围并且第一个View的
            //移动的距离 > 左边缘(0)那就要修正真实能移动的距离
            if (fillPosition < 0 && decoratedTop + absDelta > 0) {
                return decoratedTop
            }
            //如果头部的锚点位置加上dx还是在屏幕外，就不填充上一个View
            if (decoratedTop + absDelta < 0) {
                return dy
            }

        }
        //   logeMsg("start fillPosition == $fillPosition")

        //根据限定条件，不停地填充View进来

        while (availableSpace > 0 && (fillPosition in 0 until itemCount)) {

            var addPosition = fillPosition

            val itemView = recycler.getViewForPosition(addPosition)

            if (dy > 0) {
                addView(itemView)
            } else {
                addView(itemView, 0)
            }
            measureChildWithMargins(itemView, 0, 0)

            if (dy > 0) {
                bottom = top + getDecoratedMeasuredHeight(itemView)
            } else {
                top = bottom - getDecoratedMeasuredHeight(itemView)
            }

            layoutDecorated(itemView, left, top, getDecoratedMeasuredWidth(itemView), bottom)

            if (dy > 0) {
                top += getDecoratedMeasuredHeight(itemView)
                fillPosition++
            } else {
                bottom -= getDecoratedMeasuredHeight(itemView)
                fillPosition--
            }
            if (fillPosition in 0 until itemCount) {
                availableSpace -= getDecoratedMeasuredHeight(itemView)
            }
//
//
//            logeMsg("  end fillPosition $fillPosition   availableSpace == $availableSpace" )
        }

        return dy
    }


    private fun recycler(dy: Int, recycler: RecyclerView.Recycler) {
        //要回收View的集合，暂存
        val recycleViews = hashSetOf<View>()
        //dx>0 上，所以要回收前面的children
        if (dy > 0) {
            for (i in 0 until childCount) {
                val child = getChildAt(i)!!
                val mBottom = getDecoratedBottom(child)
                //要超出屏幕要回收View
                if (mBottom > 0) break
                recycleViews.add(child)
            }
        }
        if (dy < 0) {
            for (i in childCount - 1 downTo 0) {
                val child = getChildAt(i)!!
                val mTop = getDecoratedTop(child)
                //要超出屏幕要回收View
                if (mTop < height) break
                recycleViews.add(child)
            }

        }


        //真正把View移除掉
        for (view in recycleViews) {
            removeAndRecycleView(view, recycler)
        }
        recycleViews.clear()


    }


    var smoothScrollTime = 500

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State?,
        position: Int
    ) {

        var targetPosition = position

        if (itemCount <= 0 || !mLoop && (targetPosition < 0 || targetPosition > itemCount - 1)) {
            return
        }
        if (mLoop || itemCount > 0) {
            targetPosition = (targetPosition % itemCount + itemCount) % itemCount
        }

        recyclerView.requestFocus()
        val currentPosition = getCurrentPosition()
        val offset = if (currentPosition == itemCount - 1 && targetPosition == 0 && mLoop) {
            itemheight
        } else {
            (targetPosition - currentPosition) * itemheight
        }

        recyclerView.smoothScrollBy(0, offset, null)


    }

    private fun getCurrentPosition(): Int {
        for (i in 0 until childCount) {
            val childAt = getChildAt(i) ?: continue
            if (getDecoratedTop(childAt) >= 0 && getDecoratedBottom(childAt) <= mOrientationHelper.totalSpace) {
                return getPosition(childAt)
            }
        }
        return -1
    }
    /**
     * logMessage
     * */
    private fun logChildCount(recycler: RecyclerView.Recycler) {
        logeMsg("childCount = $childCount -- scrapSize = ${recycler.scrapList.size}")
    }







    var s: Job? = null
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                s = MainScope().launch {
                    withContext(IO) {
                        flow {
                            while (mLoop) {
                                delay(1000L)
                                emit(1)
                            }
                        }.flowOn(IO)
                            .collect {
                                //这里线程 是跟随 lifecycleScope.launchWhenResumed 的线程
                                //  logeMsg("ON_RESUME")


                            }
                    }

                }

            }
            Lifecycle.Event.ON_PAUSE -> {
                s?.cancel()
                mLoop = false

                logeMsg("ON_PAUSE")
            }
            else -> {}
        }


    }
}