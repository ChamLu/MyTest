package com.cham.mytest.utils

import android.view.View
import androidx.core.view.ViewCompat

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/1/8 11:37
 * @UpdateUser:
 * @UpdateDate:     2021/1/8 11:37
 * @UpdateRemark:
 */
 class ViewOffsetHelper(val view: View) {
    private var layoutTop = 0
    private var layoutLeft = 0
    private var offsetTop = 0
    private var offsetLeft = 0
    private var verticalOffsetEnabled = true
    private var horizontalOffsetEnabled = true
    fun onViewLayout() {
        // Grab the original top and left
        layoutTop = view.top
        layoutLeft = view.left
    }

    fun applyOffsets() {
        ViewCompat.offsetTopAndBottom(view, offsetTop - (view.top - layoutTop))
        ViewCompat.offsetLeftAndRight(view, offsetLeft - (view.left - layoutLeft))
    }

    /**
     * Set the top and bottom offset for this [ViewOffsetHelper]'s view.
     *
     * @param offset the offset in px.
     * @return true if the offset has changed
     */
    fun setTopAndBottomOffset(offset: Int): Boolean {
        if (verticalOffsetEnabled && offsetTop != offset) {
            offsetTop = offset
            applyOffsets()
            return true
        }
        return false
    }

    /**
     * Set the left and right offset for this [ViewOffsetHelper]'s view.
     *
     * @param offset the offset in px.
     * @return true if the offset has changed
     */
    fun setLeftAndRightOffset(offset: Int): Boolean {
        if (horizontalOffsetEnabled && offsetLeft != offset) {
            offsetLeft = offset
            applyOffsets()
            return true
        }
        return false
    }

    fun getTopAndBottomOffset(): Int {
        return offsetTop
    }

    fun getLeftAndRightOffset(): Int {
        return offsetLeft
    }

    fun getLayoutTop(): Int {
        return layoutTop
    }

    fun getLayoutLeft(): Int {
        return layoutLeft
    }

    fun setVerticalOffsetEnabled(verticalOffsetEnabled: Boolean) {
        this.verticalOffsetEnabled = verticalOffsetEnabled
    }

    fun isVerticalOffsetEnabled(): Boolean {
        return verticalOffsetEnabled
    }

    fun setHorizontalOffsetEnabled(horizontalOffsetEnabled: Boolean) {
        this.horizontalOffsetEnabled = horizontalOffsetEnabled
    }

    fun isHorizontalOffsetEnabled(): Boolean {
        return horizontalOffsetEnabled
    }


}
