package com.cham.mytest.ui.viewpager2.behavior

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.cham.mytest.R
import com.cham.mytest.utils.logeMsg
import com.google.android.material.appbar.AppBarLayout

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2020/9/1 20:22
 * @UpdateUser:
 * @UpdateDate:     2020/9/1 20:22
 * @UpdateRemark:
 */
class AppLayoutBehavior(val context: Context, attrs: AttributeSet) :
    AppBarLayout.Behavior(context, attrs) {
    companion object {

        const val TARGET_HEIGHT = 1500f
        const val MAX_REFRESH_LIMIT = 0.3f //达到这个下拉临界值就开始刷新动画
    }

    private var mIv: AppCompatImageView? = null


    //是否开启动画
    private var isAnimate = false

    private var isRecovering = false //是否正在自动回弹中


    private var mParentHeight = 0

    private var mImgHeight = 0


    private var mTotalDy = 0f
    private var mLastScale = 0f
    private var mLastBottom = 0

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        abl: AppBarLayout,
        layoutDirection: Int
    ): Boolean {
        //设置这个就看到 折叠的IM了
        //   abl.clipChildren = false
        mIv = parent.findViewById(R.id.iv_img)
        mParentHeight = abl.height
        mIv?.let {
            mImgHeight = it.height
        }
        logeMsg( message = " 图片高度 ：$mImgHeight","AppLayoutBehavior")
        return super.onLayoutChild(parent, abl, layoutDirection)
    }


    /**
     * 嵌套滑动开始
     * */
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        directTargetChild: View,
        target: View,
        axes: Int
    ): Boolean {
        isAnimate = true
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes)
    }

    /**
     * 嵌套滑动进行中，要监听的子 View将要滑动，滑动事件即将被消费
     * */
    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: AppBarLayout, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (!isRecovering) {
            if (mIv != null && (dy < 0 && child.bottom >= mParentHeight
                        || dy > 0 && child.bottom > mParentHeight)
            ) {

                scale(child, target, dy)
                return
            }
        }

        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }


    private fun scale(abl: AppBarLayout, target: View, dy: Int) {
        mTotalDy += -dy.toFloat()

        mTotalDy = Math.min(mTotalDy, TARGET_HEIGHT)

        mLastScale = Math.max(1f, 1f + mTotalDy / TARGET_HEIGHT)


        mIv?.scaleX = mLastScale
        mIv?.scaleY = mLastScale

        mLastBottom = (mParentHeight + (mImgHeight / 2 * (mLastScale - 1))).toInt()
        abl.bottom = mLastBottom
        target.scrollY = 0


        //  val progress = Math.min((mLastScale - 1) / MAX_REFRESH_LIMIT, 1f) //计算0~1的进度

    }


    /**
     * 要监听的子View即将快速滑动
     * */
    override fun onNestedPreFling(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if (velocityY > 100) { //当y速度>100,就秒弹回
            isAnimate = false
        }
        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)
    }

    override fun onStartNestedScroll(
        parent: CoordinatorLayout,
        child: AppBarLayout,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int,
        type: Int
    ): Boolean {
       // isAnimate = true
        return super.onStartNestedScroll(
            parent,
            child,
            directTargetChild,
            target,
            nestedScrollAxes,
            type
        )
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        abl: AppBarLayout,
        target: View,
        type: Int
    ) {
        recovery(abl)
        super.onStopNestedScroll(coordinatorLayout, abl, target, type)
    }

    private fun recovery(abl: AppBarLayout) {
        if (isRecovering) return

        if (mTotalDy > 0) {
            isRecovering = true
            mTotalDy = 0f
            if (isAnimate) {
                val anim = ValueAnimator.ofFloat(mLastScale, 1f).setDuration(200)
                anim.addUpdateListener { animation ->
                    val value = animation.animatedValue as Float
//                    ViewCompat.setScaleX(mTargetView, value)
                    //               ViewCompat.setScaleY(mTargetView, value)
                    mIv?.scaleX = value
                    mIv?.scaleY = value
                    abl.bottom =
                        (mLastBottom - (mLastBottom - mParentHeight) * animation.animatedFraction).toInt()

                    val progress = Math.min((value - 1) / MAX_REFRESH_LIMIT, 1f) //计算0~1的进度

                    anim.addListener(object : Animator.AnimatorListener {
                        override fun onAnimationStart(animation: Animator) {}
                        override fun onAnimationEnd(animation: Animator) {
                            isRecovering = false
                        }

                        override fun onAnimationCancel(animation: Animator) {}
                        override fun onAnimationRepeat(animation: Animator) {}
                    })
                    anim.start()

                }

            } else{

                isRecovering = false
                mIv?.scaleX = 1f
                mIv?.scaleY = 1f
                abl.bottom = mParentHeight
            }
        }
    }

}

