package com.cham.mytest.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.math.MathUtils
import androidx.core.view.ViewCompat
import com.cham.mytest.R
import com.cham.mytest.utils.ViewOffsetHelper
import com.google.android.material.appbar.AppBarLayout


/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/1/7 17:17
 * @UpdateUser:
 * @UpdateDate:     2021/1/7 17:17
 * @UpdateRemark:
 */
class FloatLinearLayout : LinearLayout {
    var currentOffset = 0

    private var onOffsetChangedListener: AppBarLayout.OnOffsetChangedListener? = null


    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        isChildrenDrawingOrderEnabled = true
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    /**
     * 测量
     * */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        for (i in 0 until childCount) {
            val mView = getChildAt(i)
            if ((mView.layoutParams as LayoutParams).pin) {
                minimumHeight = mView.measuredHeight
                break
            }
        }
    }

    override fun onAttachedToWindow() {

        val parent = parent
        if (parent is AppBarLayout) {
          //  ViewCompat.setFitsSystemWindows(this,ViewCompat.getFitsSystemWindows(parent))
            this.fitsSystemWindows = ViewCompat.getFitsSystemWindows(parent)

        }
        if (onOffsetChangedListener == null) {
            onOffsetChangedListener = OffsetUpdateListener()

        }
        (parent as AppBarLayout).addOnOffsetChangedListener(onOffsetChangedListener)

        ViewCompat.requestApplyInsets(this)
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {

        val parent = parent
        if (onOffsetChangedListener != null && parent is AppBarLayout) {
            parent.removeOnOffsetChangedListener(onOffsetChangedListener)
        }
        super.onDetachedFromWindow()
    }

    override fun getChildDrawingOrder(childCount: Int, drawingPosition: Int): Int {
        return childCount - drawingPosition - 1
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams?): Boolean {
        return p is LayoutParams
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return LayoutParams(context, attrs)
    }

    override fun generateLayoutParams(lp: ViewGroup.LayoutParams?): LayoutParams {
        return LayoutParams(lp)
    }


    inner class LayoutParams :
        LinearLayout.LayoutParams {
        var pin = false

        constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
            val arr: TypedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.FloatLinearLayout
            )
            pin = arr.getBoolean(R.styleable.FloatLinearLayout_layout_pin, false)
            arr.recycle()
        }

        constructor(width: Int, height: Int) : super(width, height)
        constructor(width: Int, height: Int, weight: Float) : super(width, height, weight)
        constructor(p: ViewGroup.LayoutParams?) : super(p)
        constructor(source: MarginLayoutParams) : super(source)
        constructor(source: LinearLayout.LayoutParams) : super(source)


    }


    inner class OffsetUpdateListener : AppBarLayout.OnOffsetChangedListener {

        override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {

            currentOffset = verticalOffset

            for (i in 0 until childCount) {
                val child = getChildAt(i)
                val lp: LayoutParams =
                    child.layoutParams as LayoutParams
                val offsetHelper = ViewOffsetHelper(child)
                if (lp.pin) {
                    val offset = currentOffset - top
                    offsetHelper.setTopAndBottomOffset(MathUtils.clamp(offset, 0, offset))
                }

            }


        }


    }


}