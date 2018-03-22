package com.jimliuxyz.vocabook.data.book.src.remote

import com.jimliuxyz.vocabook.data.book.BookContent
import com.jimliuxyz.vocabook.data.book.BookInfo
import com.jimliuxyz.vocabook.data.book.src.IBookDataSrc
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by jimliu on 2018/3/16.
 */
@Singleton
class BookRemoteSrc @Inject constructor() : IBookDataSrc {
    override fun getBookInfoList(ready: (List<BookInfo>) -> Unit, error: () -> Unit) {
    }

    override fun getBookInfo(bookId: String, ready: (BookInfo) -> Unit, error: () -> Unit) {
    }

    override fun saveBook(info: BookInfo, content: BookContent) {
    }

    override fun getBookContent(bookId: String, ready: (BookContent) -> Unit, error: () -> Unit) {
    }

    override fun getBook(bookId: String, ready: (BookInfo, BookContent) -> Unit, error: () -> Unit) {
    }

    override fun delBook(bookId: String) {
    }

    override fun refresh() {
    }
}
