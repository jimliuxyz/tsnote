package com.jimliuxyz.tsnote.views

import android.content.Context
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.jimliuxyz.tsnote.R
import kotlin.math.min


/**
 * Created by jimliu on 2018/3/10.
 */

class TextViewSelector @JvmOverloads constructor(
        context: Context, attr: AttributeSet? = null, defStyleAttr: Int = 0
) : TextView(context, attr, defStyleAttr) {

    private var fixedRange: TextRange? = null
    private var keySpanMap = LinkedHashMap<String, ArrayList<NoteBackgroundSpan.SpanRange>>()

    private lateinit var wordSpan: NoteBackgroundSpan
//    var clBgSelText = 0
//        get() = wordSpan.clBgSelText
//        set(value) {
//            field = value; wordSpan.clBgSelText = value; invalidate()
//        }
//    var clBgKeyText = 0
//        get() = wordSpan.clBgKeyText
//        set(value) {
//            field = value; wordSpan.clBgKeyText = value; invalidate()
//        }

    private var selChangeListener: ((selectedText: String?, selectedKeyword: String?) -> Unit)? = null

    private var clickListener = object : OnClickListener {
        override fun onClick(v: View?) {
            if (selectionStart >= text.length) return
//            println("click ${selectionStart} : '" + story.substring(selectionStart, selectionStart+1) + "' / ${story.length}")

            var fixedClicked = wordSpan.getRangeByPos(selectionStart)
            var selRange = wordSpan.selRange

            //click on a fixed range
            if (fixedClicked != null) {
                fixedRange = fixedClicked
                selRange.set(fixedClicked)
            }
            //already clicked on a fixed range
            else {
                if (fixedRange != null) {
                    fixedRange = null
                    selRange.empty()
                }

                var clickRng = guestSelectionRange(selectionStart)
                if (selRange.isEmpty())
                    selRange.set(clickRng)
                else if (clickRng in selRange) {
                    selRange.set(selRange - clickRng)
                } else {
                    selRange.set(selRange + clickRng)
                }

                trimRange(selRange)

                if (selRange.isPoint())
                    selRange.empty()
            }
            invalidate()

            selChangeListener?.let {
                it(getSelectedText(), getSelectedKeyText())
            }
        }
    }

    init {
        wordSpan = NoteBackgroundSpan(this)
        wordSpan.clBgSelText = getColorByAttributeId(R.attr.colorBgSelText)
        wordSpan.clBgKeyText = getColorByAttributeId(R.attr.colorBgKeyText)
        wordSpan.clTxtSelNote = getColorByAttributeId(R.attr.colorTxtSelNote)
        wordSpan.clTxtKeyNote = getColorByAttributeId(R.attr.colorTxtKeyNote)

        setOnClickListener(clickListener)

        onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                //hide the SoftInput when lose focus
                if (hasFocus)
                    (getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(windowToken, 0)
            }
        }


    }

    @ColorInt
    private fun getColorByAttributeId(@AttrRes attrId: Int): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attrId, typedValue, true)
        return typedValue.data
    }

//    override fun onSaveInstanceState(): Parcelable {
//        (super.getText() as? Spannable)?.removeSpan(wordSpan)
//        val state = super.onSaveInstanceState()
//        (super.getText() as? Spannable)
//                ?.setSpan(wordSpan, 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//        return state
//    }

    fun setText(text: String) {
        keySpanMap.clear()
        wordSpan.clearRange()
        wordSpan.selRange.empty()

        val builder = SpannableStringBuilder(text)

        (super.getText() as Spannable)
                .removeSpan(wordSpan)

        super.setText(builder)
        (super.getText() as Spannable)
                .setSpan(wordSpan, 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        invalidate()
    }

    fun setSelNote(text: String) {
        wordSpan.selNoteText = text
        invalidate()
    }

    private fun trimRange(rng: TextRange) {

        for (pos in rng.start..rng.end - 1) {
            var c = text.get(pos)
            if (!c.isWhitespace()) {
                rng.set(pos, rng.end)
                break
            }
        }
        for (pos in rng.end - 1 downTo rng.start) {
            var c = text.get(pos)
            if (!c.isWhitespace()) {
                break
            }
            rng.set(rng.start, pos)
        }
    }

    private fun guestSelectionRange(selectionStart: Int): TextRange {
        if (selectionStart < 0) return TextRange(-1, -1)

        if (selectionStart in 0..text.length - 1) {
            var c = text.get(selectionStart)

            if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.BASIC_LATIN) {

                var r1 = CharRange('A', 'Z')
                var r2 = CharRange('a', 'z')

                var start = selectionStart
                var end = selectionStart
                for (pos in selectionStart downTo 0) {
                    var c = text.get(pos)
                    if (c !in r1 && c !in r2 && c != '\'')
                        break
                    start = pos
                }
                for (pos in selectionStart + 1..text.length) {
                    var c = text.get(pos - 1)
                    if (c !in r1 && c !in r2 && c != '\'')
                        break
                    end = pos
                }
                return TextRange(start, end)
            }
        }

        return TextRange(selectionStart, min(selectionStart + 1, text.length))
    }


    fun setSelectionChangedListener(listener: ((selectedText: String?, selectedKeyword: String?) -> Unit)) {
        selChangeListener = listener
    }

    fun releaseSelection() {
        wordSpan.selRange.empty()
        fixedRange = null
        selChangeListener?.let {
            it(null, null)
        }
        invalidate()
    }

    fun getSelectedKeyText(): String? {
        return fixedRange?.let {
            text.substring(it.start, it.end)
        } ?: null
    }

    fun getSelectedText(): String? {
        if (fixedRange != null || wordSpan.selRange.isEmpty())
            return null
        return text.substring(wordSpan.selRange.start, wordSpan.selRange.end)
    }

    fun addKeyNote(key: String, note: String) {
        var list = keySpanMap.get(key) ?: ArrayList<NoteBackgroundSpan.SpanRange>().also {
            keySpanMap.put(key, it)
        }
        for (span in list) {
            wordSpan.removeRange(span)
        }

        var start = 0
        while (true) {
            start = text.indexOf(key, start, false)
            if (start == -1)
                break

            var span = wordSpan.addRange(start, start + key.length, note)
            list.add(span)
            start += key.length
        }
    }

    fun removeKeyNote(key: String) {
        keySpanMap.get(key)?.also {
            for (span in it) {
                wordSpan.removeRange(span)
            }
            keySpanMap.remove(key)
        }
    }
}
