package com.jimliuxyz.tsnote.constants

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**
 * Created by jimliu on 2018/3/20.
 */
object SupportLang {
    private val SupportLangCode = arrayOf(
            Locale.getDefault().toString(),
            "zh_CN_#Hans",
            "zh_TW_#Hant",
            "zh_HK_#Hant",
//            "yue_HKG", // this is not in list of Locale.getAvailableLocales()
            "en_US",
            "fr_FR",
            "nl_NL",
            "de_DE",
            "it_IT",
            "ja_JP",
            "ko_KR",
            "ru_RU",
            "es_ES",
            "fil_PH",
            "th_TH",
            "pt_PT",
            "in_ID",
            "vi_VN",
            "ar_AE",
            "el_GR",
            "zu_ZA"
    ).distinct()

    init {
//        listAllLocale()
    }

    val defCodeSrc by lazy {
        if (defCodeTgt != "en_US") "en_US" else "zh_TW_#Hant"
    }

    val defCodeTgt by lazy {
        Locale.getDefault().toString()
    }

    private val codeMap by lazy {
        HashMap<String, Locale>(Locale.getAvailableLocales().associateBy({ it.toString() }, { it }))
    }

    val list by lazy {
        var list = ArrayList<Locale>()

        for (code in SupportLangCode) {
            val locale = codeMap[code]
            locale?.let {
                list.add(it)
//                showLocale(locale)
            }
        }

        list.sortedBy { it.displayLanguage }
    }

    private fun listAllLocale() {
        for (locale in Locale.getAvailableLocales().sortedBy { it.language }) {
            if (locale.country.isNotBlank())
                showLocale(locale)
        }
    }

    fun showLocale(locale: Locale) {
        //zh_TW_#Hant : Chinese(TW) : Taiwan 		 Chinese (Traditional Han,Taiwan)
        //zh_TW_#Hant : 中文(TW) : 台灣 		 中文 (繁體中文,台灣)
        println("${locale.toString()} : ${locale.displayLanguage}(${locale.country}) : ${locale.displayCountry} \t\t ${locale.displayName} \t\t ${locale.displayVariant}")
    }

    fun getLocaleByCode(code: String): Locale {
        return codeMap[code] ?: Locale.getDefault()
    }

    fun getLocaleByIdx(idx: Int): Locale {
        return list[idx] ?: Locale.getDefault()
    }

    fun getIdxByCode(code: String): Int? {
        return list.find { it.toString().equals(code, true) }?.let { list.indexOf(it) }
    }

    fun toGoogleTranslate(code: String): String {
        var str = code.lastIndexOf('_').takeIf { it >= 0 }?.let { code.substring(0, it) }
        return str?.replace('-', '-') ?: code
    }

}