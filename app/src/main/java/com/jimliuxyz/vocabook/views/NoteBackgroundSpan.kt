package com.jimliuxyz.vocabook.views

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.text.style.LineBackgroundSpan
import android.widget.TextView

/**
 * Created by jimliu on 2018/3/21.
 */
internal class NoteBackgroundSpan(var tv: TextView) : LineBackgroundSpan {
    enum class TYPE {
        SELECTION,
        FIXED
    }

    class SpanRange(start: Int = -1,
                    end: Int = -1,
                    var type: TYPE = TYPE.FIXED,
                    var note: String? = null
    ) : TextRange(start, end)

    var clBgSelText = Color.parseColor("#00ff00")
    var clBgKeyText = Color.parseColor("#aa0000")

    var clTxtSelNote = Color.BLUE
    var clTxtKeyNote = Color.GRAY

    val selRange = SpanRange(type = TYPE.SELECTION)
    var selNoteText = ""
    private val ranges = ArrayList<SpanRange>().apply { add(selRange) }

    fun addRange(start: Int, end: Int, note: String? = null): SpanRange {
        var sp = SpanRange(start, end, note = note)
        ranges.add(sp)
        return sp
    }

    fun removeRange(sp: SpanRange) {
        ranges.remove(sp)
    }

    fun getRangeByPos(start: Int): SpanRange? {
        for (rng in ranges) {
            if (rng.type == TYPE.FIXED && start in rng) {
                return rng
            }
        }
        return null
    }

    fun clearRange() {
        ranges.clear()
        ranges.add(selRange)
    }

    override fun drawBackground(c: Canvas, p: Paint, left: Int, right: Int, top: Int, baseline: Int, bottom: Int,
                                text: CharSequence, start: Int, end: Int, lnum: Int) {

        val layout = tv.layout
        val origColor = p.color
        var fontSize = p.textSize

        var top_ = baseline.toFloat() + p.fontMetrics.ascent - 0
        var bottom_ = baseline.toFloat() + p.fontMetrics.descent + p.fontMetrics.leading + 1

        //move selRange to the last
        ranges.remove(selRange)
        ranges.add(selRange)

        var border = 1
        for (rng in ranges) {
            var left_ = left
            var right_ = right

            if (rng.start < end && rng.end > start) {
                if (layout.getLineForOffset(rng.start) == lnum)
                    left_ = layout.getPrimaryHorizontal(rng.start).toInt()
                if (rng.end < end && layout.getLineForOffset(rng.end) == lnum)
                    right_ = layout.getPrimaryHorizontal(rng.end).toInt()
                else
                    right_ = layout.getLineRight(lnum).toInt()

                var note: String? = null
                var noteColor = clTxtKeyNote

                //paint background highlight
                if (rng.type == TYPE.SELECTION) {
                    p.color = clBgSelText
                    noteColor = clTxtSelNote
                    note = selNoteText

                    p.setStyle(Paint.Style.STROKE);
                    c.drawRect(left_.toFloat() - border, top_ - border, right_.toFloat() + border, bottom_ + border, p)
                    p.setStyle(Paint.Style.FILL);
                } else {
                    p.color = clBgKeyText
                    note = rng.note.takeIf { !(rng sameAs selRange) }

                    c.drawRect(left_.toFloat(), top_, right_.toFloat(), bottom_, p)
                }

                //paint note story
                note?.takeIf { rng.start >= start && it.isNotBlank() }?.let {
                    p.setTextSize(p.textSize * 0.8.toFloat())

                    var rect = Rect()
                    p.getTextBounds(it, 0, it.length, rect)

                    var start = left_ + (right_ - left_) / 2 - rect.width() / 2   //align center
                    if (start + rect.width() > right)
                        start = right - rect.width()
                    if (start < left)
                        start = left
                    var offset = ((bottom_ - top_) - (rect.bottom - rect.top)) / 2

                    p.color = (tv.background as?ColorDrawable)?.color ?: 0
                    c.drawRect(start.toFloat(), top.toFloat() - offset - rect.height(), start.toFloat() + rect.width().toFloat(), top.toFloat() - border - 1, p)

                    p.color = noteColor
                    c.drawText(it, start.toFloat(), top.toFloat() - offset, p)
                }

                p.setTextSize(fontSize)
                p.color = origColor
            }
        }
    }
}