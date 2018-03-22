package com.jimliuxyz.vocabook.data.src

import android.content.Context
import android.os.Handler
import com.jimliuxyz.vocabook.data.book.BookContent
import com.jimliuxyz.vocabook.data.book.BookInfo
import com.jimliuxyz.vocabook.data.book.KeyNote
import com.jimliuxyz.vocabook.data.book.src.IBookDataSrc
import com.jimliuxyz.vocabook.utils.UUID62
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by jimliu on 2018/3/13.
 */

class FakeBookSrc : IBookDataSrc {

    private val mInfos = LinkedHashMap<String, BookInfo>()
    private val mContent = LinkedHashMap<String, BookContent>()

    data class Mock(var title: String, var content: String, var keyNotes: ArrayList<KeyNote>)

    private val mocklist = ArrayList<Mock>()

    init {
        mocklist.add(Mock("測試",
                "running man",
                arrayListOf(KeyNote("running", "跑!"))))
    }

    constructor(context: Context) {
        for (mock in mocklist) {
            var info = BookInfo(UUID62.randomUUID(), mock.title)
            var content = BookContent(info.id, mock.content, mock.keyNotes)

            mInfos.put(info.id, info)
            mContent.put(info.id, content)
        }
    }

    override fun getBookInfoList(ready: (List<BookInfo>) -> Unit, error: () -> Unit) {
        Handler().postDelayed({
            ready(mInfos.values.toList())
        }, 1000)
    }

    override fun getBookInfo(bookId: String, ready: (BookInfo) -> Unit, error: () -> Unit) {
        mInfos[bookId]?.let {
            ready(it)
        }
    }

    override fun getBookContent(bookId: String, ready: (BookContent) -> Unit, error: () -> Unit) {
        mContent[bookId]?.let {
            ready(it)
        }
    }

    override fun getBook(bookId: String, ready: (BookInfo, BookContent) -> Unit, error: () -> Unit) {
        ready(mInfos[bookId]!!, mContent[bookId]!!)
    }

    override fun saveBook(info: BookInfo, content: BookContent) {
        mInfos[info.id] = info
        mContent[info.id] = content
    }

    override fun delBook(bookId: String) {
        mInfos.remove(bookId)
        mContent.remove(bookId)
    }

    override fun refresh() {
    }

}