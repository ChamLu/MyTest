package com.cham.mytest.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.cham.mytest.R


/**
 *
 *  @Author       :   Cham
 *  @CreateDate   :   2020/04/07
 *  @Description  :   最大高度RecyclerView
 *
 */
class MaxHeightRecyclerView : RecyclerView {
    private var mMaxHeight = 0
    constructor(context: Context?) : super(context!!)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet) {
        val arr: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightRecyclerView)
        mMaxHeight = arr.getDimensionPixelSize(R.styleable.MaxHeightRecyclerView_maxRvHeight, mMaxHeight)
        arr.recycle()
    }

     override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var tempHeightMeasureSpec = heightMeasureSpec
        if (mMaxHeight > 0) {
            tempHeightMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST)
        }
        super.onMeasure(widthMeasureSpec, tempHeightMeasureSpec)
    }

    fun setMaxheight(height:Int ){
        mMaxHeight=height
        invalidate()
    }
}
