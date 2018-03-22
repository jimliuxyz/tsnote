package com.jimliuxyz.vocabook.activity.editor

import com.jimliuxyz.vocabook.data.book.BookContent
import com.jimliuxyz.vocabook.data.book.BookInfo

/**
 * Created by jimliu on 2018/3/17.
 */
interface EditorContract {

    interface View {
        fun showBook(info: BookInfo, content: BookContent)

        fun showNoteInput(note: String)
        fun enNoteInput(en: Boolean)

        fun releaseSelection()

        fun addKeyNote(key: String, note: String)
        fun removeKeyNote(key: String)

        fun setSelectionNote(note: String)
    }

    interface Presenter {

        fun start(view: View, bookId: String?, content: String?)
        fun getBookId(): String?

        fun queryNote(key: String)

        fun onSelectionChanged(selText: String?, selKeyText: String?)
        fun onNoteTyping(note: String)

        fun onBtnClear()
        fun onBtnDone(note: String)

        fun onPasteStory(story: String)

        fun saveBook(title: String, story: String)
    }
}
