package com.jimliuxyz.tsnote.services.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.*

/**
 * Created by jimliu on 2018/3/20.
 */
class SpeakService private constructor(val context: Context) {

    companion object {
        var instance: SpeakService? = null
        fun getInstance(context: Context): SpeakService {
            return instance ?: SpeakService(context)
        }
    }

    var tts: TextToSpeech? = null

    init {
        var tts_: TextToSpeech? = null
        tts_ = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts = tts_
            }
        }
    }

    fun stop() {
        tts?.stop()
    }

    fun speak(text: String, locale: Locale, queueMode: Int = TextToSpeech.QUEUE_FLUSH) {

        var locale = if (locale.country != "HK") locale else Locale("yue", "HKG")

        tts?.setLanguage(locale)?.let { result ->
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                println("TextToSpeech.LANG_MISSING_DATA || TextToSpeech.LANG_NOT_SUPPORTED")
        }

//        tts?.setLanguage(Locale("yue","HKG"))

        tts?.speak(text, queueMode, null)
    }

}