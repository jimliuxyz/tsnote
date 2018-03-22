package com.jimliuxyz.tsnote.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.NumberPicker
import com.jimliuxyz.tsnote.R
import com.jimliuxyz.tsnote.constants.SupportLang
import com.jimliuxyz.tsnote.utils.getPref
import com.jimliuxyz.tsnote.utils.setPref

/**
 * Created by jimliu on 2018/3/19.
 */
class SelectLangFragment : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var root = inflater?.inflate(R.layout.lang_selection, container, false)

        var langSrc = activity.getPref(R.string.key_translate_lang_src, SupportLang.defCodeSrc)
        var langTgt = activity.getPref(R.string.key_translate_lang_tgt, SupportLang.defCodeTgt)

        val items = SupportLang.list.map { it.displayLanguage + "(" + it.country + ")" }.toTypedArray()

        root?.findViewById<NumberPicker>(R.id.naLangPicker)?.apply {
            maxValue = items.size - 1
            setDisplayedValues(items)
            value = SupportLang.getIdxByCode(langSrc) ?: 0
            setOnValueChangedListener { picker, oldVal, newVal ->
                activity.setPref(R.string.key_translate_lang_src, SupportLang.getLocaleByIdx(newVal).toString())
            }
        }

        root?.findViewById<NumberPicker>(R.id.taLangPicker)?.apply {
            maxValue = items.size - 1
            setDisplayedValues(items)
            value = SupportLang.getIdxByCode(langTgt) ?: 0
            setOnValueChangedListener { picker, oldVal, newVal ->
                activity.setPref(R.string.key_translate_lang_tgt, SupportLang.getLocaleByIdx(newVal).toString())
            }
        }

        root?.findViewById<ImageView>(R.id.swapBtn)?.setOnClickListener {
            var value1 = view?.findViewById<NumberPicker>(R.id.naLangPicker)?.value!!
            var value2 = view?.findViewById<NumberPicker>(R.id.taLangPicker)?.value!!

            view?.findViewById<NumberPicker>(R.id.naLangPicker)?.value = value2
            view?.findViewById<NumberPicker>(R.id.taLangPicker)?.value = value1

            activity.setPref(R.string.key_translate_lang_src, SupportLang.getLocaleByIdx(value2).toString())
            activity.setPref(R.string.key_translate_lang_tgt, SupportLang.getLocaleByIdx(value1).toString())
        }

        root?.findViewById<CheckBox>(R.id.naLangSpeaker)?.apply {
            isChecked = activity.getPref(R.string.key_speak_src, true)
            setOnCheckedChangeListener { buttonView, isChecked ->
                activity.setPref(R.string.key_speak_src, isChecked)
            }
        }

        root?.findViewById<CheckBox>(R.id.taLangSpeaker)?.apply {
            isChecked = activity.getPref(R.string.key_speak_tgt, true)
            setOnCheckedChangeListener { buttonView, isChecked ->
                activity.setPref(R.string.key_speak_tgt, isChecked)
            }
        }

        return root
    }

}
