package com.cham.glidebigpicture

import android.content.Context
import android.graphics.Point
import android.graphics.Typeface
import android.os.Build
import android.text.*
import android.text.style.AlignmentSpan
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import com.cham.app.MApp

/**
 * @Author:         Cham
 * @Description:
 * @CreateDate:     2021/6/24 17:01
 * @UpdateUser:
 * @UpdateDate:     2021/6/24 17:01
 * @UpdateRemark:
 */
class ExpandTextVeiw @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    val ellipsis_string = String(charArrayOf('\u2026'))
    private val default_max_line = 2
    private val default_open_suffix = " 展开  "
    private val default_close_suffix = " 收起 "

    private lateinit var mOpenSuffixSpan: SpannableStringBuilder
    private lateinit var mCloseSuffixSpan: SpannableStringBuilder

    private lateinit var mOpenSpannableStr: SpannableStringBuilder
    private lateinit var mCloseSpannableStr: SpannableStringBuilder

    private var initWidth = 0

    private var mCloseInNewLine = false

    /**
     * 是否展开
     * */
    private var mExpandable = false

    init {
        includeFontPadding = false
    }

    private lateinit var mOriginalText: CharSequence


    fun setOriginalText(originalText: CharSequence) {
        mOriginalText = originalText
      //  text = mOriginalText

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        println(" measure 阶段： width: $width    measureWidth: $measuredWidth measureHeight: $measuredHeight  ScreenWidth:${getScreenWidth()}  ")
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        setLayout()
        println("layout 阶段： width: $width   height:$height   measureWidth: $measuredWidth    measureHeight: $measuredHeight   ScreenWidth:${getScreenWidth()}   ")

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        println("在视图及其所有子级都已从 XML 扩充之后调用。")

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        println("在此视图的大小发生变化时调用。 ")
    }



    private fun setLayout() {
        initWidth = measuredWidth
        if (initWidth == 0) {
            initWidth = getScreenWidth() - marginStart - paddingStart - marginEnd - paddingEnd
        }
        mCloseSpannableStr = SpannableStringBuilder()
        val maxLines: Int = default_max_line
        val tempText: SpannableStringBuilder = SpannableStringBuilder(mOriginalText)

        mOpenSpannableStr = tempText
        val layout = createStaticLayout(tempText)
        println("行数 ：${layout.lineCount}")
        mExpandable = layout.lineCount > maxLines
        if (mExpandable) {
            //拼接展开内容
            if (mCloseInNewLine) {
                mOpenSpannableStr.append("\n")
            }
            updateCloseSuffixSpan()
            mOpenSpannableStr.append(mCloseSuffixSpan)
            //计算原文截取位置
            val endPos = layout.getLineEnd(maxLines - 1)

            mCloseSpannableStr = if (mOriginalText.length <= endPos) {
                SpannableStringBuilder(mOriginalText)
            } else {
                SpannableStringBuilder(
                    mOriginalText.subSequence(
                        0,
                        endPos - default_open_suffix.length - ellipsis_string.length
                    )
                )
            }


            val tempText2: SpannableStringBuilder =
                SpannableStringBuilder(mCloseSpannableStr).append(ellipsis_string)
                    .append(default_open_suffix)

            //循环判断，收起内容添加展开后缀后的内容
            val tempLayout = createStaticLayout(tempText2)
            text = tempText2

            println("计算原文截取位置 ：${endPos}   height：${tempLayout.height}     ")


            val widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST)
            val heightMeasureSpec= MeasureSpec.makeMeasureSpec(tempLayout.height,MeasureSpec.AT_MOST)
            measure(widthMeasureSpec,heightMeasureSpec)




        }

    }


    /**
     * 收起后缀是否另起一行
     *
     * @param closeInNewLine
     */
    fun setCloseInNewLine(closeInNewLine: Boolean) {
        mCloseInNewLine = closeInNewLine
    }

    /**
     * 更新收起后缀Spannable
     */
    private fun updateCloseSuffixSpan() {

        mCloseSuffixSpan = SpannableStringBuilder(default_close_suffix)
        mCloseSuffixSpan.setSpan(
            StyleSpan(Typeface.NORMAL),
            0,
            default_close_suffix.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        if (mCloseInNewLine) {
            val alignmentSpan: AlignmentSpan = AlignmentSpan.Standard(
                Layout.Alignment.ALIGN_OPPOSITE
            )
            mCloseSuffixSpan.setSpan(alignmentSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        mCloseSuffixSpan.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                //     ds.color = mCloseSuffixColor
                ds.isUnderlineText = false
            }
        }, 1, default_close_suffix.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    /**
     * @param spannable
     * 说白了这玩意就是自动处理换行
     * width 是文字区域的宽度，文字到达这个宽度后就会自动换行
     * @return
     */

    private fun createStaticLayout(spannable: SpannableStringBuilder): Layout {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val builder = StaticLayout.Builder.obtain(
                spannable, 0, spannable.length,
                paint, initWidth
            )
            builder.setAlignment(Layout.Alignment.ALIGN_NORMAL)
            builder.setIncludePad(includeFontPadding)
            builder.setLineSpacing(lineSpacingExtra, lineSpacingMultiplier)
            return builder.build()

        } else {
            return StaticLayout(
                spannable, paint, initWidth, Layout.Alignment.ALIGN_NORMAL,
                lineSpacingMultiplier, lineSpacingExtra, includeFontPadding
            )
        }

    }


    private fun getScreenWidth(): Int {
        val wm = MApp.get().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getRealSize(point)
        return point.x
    }
}