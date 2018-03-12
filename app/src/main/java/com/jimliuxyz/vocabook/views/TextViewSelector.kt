package com.jimliuxyz.vocabook.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.Spannable
import android.text.style.LineBackgroundSpan
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.jimliuxyz.vocabook.R


/**
 * Created by jimliu on 2018/3/10.
 */

class TextViewSelector @JvmOverloads constructor(
        context: Context, attr: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attr, defStyleAttr) {

    private var mFontSize = 24

    init {
        //way to read attributes
        val typedArray = context.obtainStyledAttributes(attr, R.styleable.TextAppearance)
        mFontSize = typedArray.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, -1)

//        val builder = SpannableStringBuilder("here some text and more of it\nhere some text and more of it")
//        builder.setSpan(BetterHighlightSpan(Color.CYAN), 4, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        setText(builder)

        val sp = text as Spannable
        var lbs = LBS(this)
        sp.setSpan(lbs, 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)


        setOnClickListener(View.OnClickListener { v: View? ->
            println("click..." + selectionStart)

            val color = resources.getColor(R.color.colorTextHighlight)

            lbs.add(selectionStart, selectionStart + 5)
        })

        onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                //hide the SoftInput when lose focus
                if (hasFocus)
                    (getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(windowToken, 0)
            }
        }

        println("getLineHeight():" + getLineHeight())
        println("mFontSize:" + mFontSize)
    }

    fun setSelectionList() {
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        println("onDetachedFromWindow <<<<")
    }
}


internal class LBS(var tv: TextView) : LineBackgroundSpan {
    var ranges: ArrayList<Pair<Int, Int>> = ArrayList()

    fun add(start: Int, end: Int) {
        ranges.add(Pair(start, end))
    }

    override fun drawBackground(c: Canvas, p: Paint, left: Int, right: Int, top: Int, baseline: Int, bottom: Int,
                                text: CharSequence, start: Int, end: Int, lnum: Int) {

        val layout = tv.layout
//        c.drawText(""+lnum, left.toFloat(), top.toFloat(), p)
        for ((a, b) in ranges) {

            var left_ = left
            var right_ = right

            if (a <= end && b >= start) {
                if (layout.getLineForOffset(a) == lnum)
                    left_ = layout.getPrimaryHorizontal(a).toInt()
                if (b < end && layout.getLineForOffset(b) == lnum)
                    right_ = layout.getPrimaryHorizontal(b).toInt()
                else
                    right_ = layout.getLineRight(lnum).toInt()

//                println("$lnum ($a) : ${text.subSequence(if(a>start) a else start, if(b<end) b else end)}")

                val origColor = p.color
                p.color = Color.RED
                c.drawRect(left_.toFloat(), top.toFloat(), right_.toFloat(), bottom.toFloat() - 24, p)
                p.color = origColor
            }
        }
    }
}