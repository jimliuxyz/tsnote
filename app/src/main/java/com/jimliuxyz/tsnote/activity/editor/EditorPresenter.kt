package com.jimliuxyz.tsnote.activity.editor

import android.content.Context
import android.speech.tts.TextToSpeech
import com.jimliuxyz.tsnote.R
import com.jimliuxyz.tsnote.constants.SupportLang
import com.jimliuxyz.tsnote.data.book.BookContent
import com.jimliuxyz.tsnote.data.book.BookInfo
import com.jimliuxyz.tsnote.data.book.KeyNote
import com.jimliuxyz.tsnote.data.book.src.BookRepo
import com.jimliuxyz.tsnote.services.translation.TranslateService
import com.jimliuxyz.tsnote.services.tts.SpeakService
import com.jimliuxyz.tsnote.utils.getPref
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by jimliu on 2018/3/17.
 */
class EditorPresenter @Inject constructor(
        @Named(EditorActivity.EXTRA_BOOK_ID) val mBookId: String?,
        val context: Context,
        val mRepo: BookRepo,
        val mView: EditorContract.View,
        val mTranslator: TranslateService,
        val mSpeaker: SpeakService
) : EditorContract.Presenter {

    var info: BookInfo? = null
    var content: BookContent? = null
    var keymap: HashMap<String, KeyNote>? = null

    var selText: String? = null
    var selKeyText: String? = null

    val MAX_TITLE_CUTLEN = 20

    private fun guestTitle(content: String?): String {
        val title = content
                ?.trim()
//                    ?.replace(Regex("[\\s\\p{Punct}].*"), "")
                ?.replace(Regex("[\\s.,?!。，？！].*"), "")
                ?.let {
                    var max = MAX_TITLE_CUTLEN * if (it.toByteArray().size / it.length == 1) 2 else 1
                    it.substring(0, it.length.let { if (it >= max) max else it })
                }
        return title ?: ""
    }

    override fun start(view: EditorContract.View, bookId: String?, content: String?) {

        println("start bookId:$bookId")
        if (bookId.isNullOrBlank()) {
            val defnote = context.getString(R.string.def_editor_note).split('|')
            val info = BookInfo(title = SimpleDateFormat("yyyy-MM-dd ", Locale.getDefault()).format(Date()) + guestTitle(content))
            val content = BookContent(info.id, content
                    ?: context.getString(R.string.def_editor_text), arrayListOf(KeyNote(defnote[0], defnote[1])))
            gotData(info, content)
            return
        }

        mRepo.getBook(bookId!!, { info, content ->
            gotData(info, content)
        }, {

        })
    }

    override fun getBookId(): String? {
        return info?.id
    }


    private fun gotData(info: BookInfo, content: BookContent) {
        this.info = info
        this.content = content

        keymap = HashMap(content.keyNotes.associateBy({ it.key }, { it }))

        mView.showBook(info, content)
    }

    override fun onSelectionChanged(selText: String?, selKeyText: String?) {
        this.selText = selText
        this.selKeyText = selKeyText

        mView.enNoteInput(selText != null || selKeyText != null)

        queryNote(selText ?: selKeyText ?: "")

        (selText ?: selKeyText)?.let {

            var speak1 = context.getPref(R.string.key_speak_src, true)
            var speak2 = context.getPref(R.string.key_speak_tgt, true)

            if (speak1) {
                var langCode = context.getPref(R.string.key_translate_lang_src, SupportLang.defCodeSrc)
                mSpeaker.speak(it, SupportLang.getLocaleByCode(langCode), TextToSpeech.QUEUE_FLUSH)
            }
            if (speak2) {
                var langCode = context.getPref(R.string.key_translate_lang_tgt, SupportLang.defCodeTgt)
                keymap?.get(it)?.note?.let {
                    mSpeaker.speak(it, SupportLang.getLocaleByCode(langCode), if (speak1) TextToSpeech.QUEUE_ADD else TextToSpeech.QUEUE_FLUSH)
                }
            }

        }
    }

    override fun onNoteTyping(note: String) {
        mView.setSelectionNote(note)
    }

    override fun onBtnClear() {

        selText?.also {
            mView.releaseSelection()
        }

        selKeyText?.also {
            mView.removeKeyNote(it)
            mView.releaseSelection()
            removeKeyNote(it)
        }
        mView.showNoteInput("")
        mView.enNoteInput(false)
    }

    override fun onBtnDone(note: String) {
        var key = selKeyText ?: selText

        key?.also { key ->
            addKeyNote(key, note)
            mView.addKeyNote(key, note)
            mView.releaseSelection()
        }
        mView.showNoteInput("")
        mView.enNoteInput(false)
    }

    private fun addKeyNote(key: String, note: String) {
        keymap?.put(key, KeyNote(key, note))
        content?.keyNotes = ArrayList(keymap?.map { it.value })
    }

    private fun removeKeyNote(key: String) {
        keymap?.remove(key)
        content?.keyNotes = ArrayList(keymap?.map { it.value })
    }

    override fun onPasteStory(story: String) {
        if (story.isNotBlank() && content != null) {
            content?.story = story
            mView.showBook(info!!, content!!)
        }
    }

    override fun saveBook(title: String, story: String) {
        val info = info ?: return
        val content = content ?: return
        val keymap = keymap ?: return

        info.title = title
        content.story = story
        mRepo.saveBook(info, content)
    }

    override fun queryNote(key: String) {
        mView.showNoteInput("")
        if (key.isBlank()) return

        val note = keymap?.let { it[key]?.note }
        note?.let {
            mView.showNoteInput(it)
        }

        note ?: let {
            if (key.toByteArray().size > 200)
                return

            var langSrc = context.getPref(R.string.key_translate_lang_src, SupportLang.defCodeSrc)
            var langTgt = context.getPref(R.string.key_translate_lang_tgt, SupportLang.defCodeTgt)

            mTranslator.translate(langSrc, langTgt, key) {
                it?.takeIf { key.equals(selText) }?.let {

                    var speak1 = context.getPref(R.string.key_speak_src, true)
                    var speak2 = context.getPref(R.string.key_speak_tgt, true)

                    if (key.toByteArray().size <= 200 && speak2) {
                        var langCode = context.getPref(R.string.key_translate_lang_tgt, SupportLang.defCodeTgt)
                        mSpeaker.speak(it, SupportLang.getLocaleByCode(langCode), if (speak1) TextToSpeech.QUEUE_ADD else TextToSpeech.QUEUE_FLUSH)
                    }
                    println("'$key' ==\t '$it'")
                    mView.showNoteInput(it)
                }
            }
        }
    }
}