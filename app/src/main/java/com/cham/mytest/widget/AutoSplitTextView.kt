package com.cham.mytest.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 *
 *  @Author       :   Cham
 *  @CreateDate   :   2020/04/21
 *  @Description  :
 *
 */
class AutoSplitTextView  :AppCompatTextView {
    constructor(context: Context?) : super(context!!)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    //解决首次渲染，没有补全的bug。
    var mWidth = -1
    private val autoText: String? = null
    private var textWidth = 0f
    private var textHeight = 0f
    private var textPaint: Paint? = null


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }




    override fun onDraw(canvas: Canvas?) {
        if(mWidth != width || !autoText.equals(text.toString()) ){
         val   autoText=autoSplitText(this)
            text = autoText
            mWidth = width;
        }
        super.onDraw(canvas)
    }

    private fun autoSplitText(textView: AutoSplitTextView): String? {
        val rawCharSequence = textView.text
        val originText = rawCharSequence.toString() //获取原始文本
        textPaint = textView.paint
        textWidth = (textView.width - textView.paddingLeft - textView.paddingRight).toFloat()
        textHeight = textView.height.toFloat()
        val allTextLines = originText.replace("\n".toRegex(), "")
        val stringBuilder = StringBuilder()
        if ((textPaint as TextPaint?)!!.measureText(allTextLines) > textWidth) {
            //如果整行宽度超过控件所用宽度，则按字符测量，在超过可用宽度的最后一个字符添加换行符
            var lineWidth = 0f
            var i = 0
            while (i < allTextLines.length) {
                val textChar = allTextLines[i]
                lineWidth += textPaint!!.measureText(textChar.toString())
                if (lineWidth <= textWidth) {
                    stringBuilder.append(textChar)
                } else {
                    stringBuilder.append("\n")
                    i--
                    lineWidth = 0f
                }
                i++
            }
        } else {
            stringBuilder.append(allTextLines)
        }
        return stringBuilder.toString()
    }
}