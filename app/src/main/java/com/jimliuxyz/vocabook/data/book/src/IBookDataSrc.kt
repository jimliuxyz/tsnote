package com.jimliuxyz.vocabook.data.book.src

import com.jimliuxyz.vocabook.data.book.BookContent
import com.jimliuxyz.vocabook.data.book.BookInfo

/**
 * Created by jimliu on 2018/3/13.
 */
interface IBookDataSrc {

    fun getBookInfoList(ready: (List<BookInfo>) -> Unit, error: () -> Unit)
//    fun getBookInfo(ready: (BookInfo) -> Unit, error: () -> Unit)

    fun getBookInfo(bookId: String, ready: (BookInfo) -> Unit, error: () -> Unit)

    fun getBookContent(bookId: String, ready: (BookContent) -> Unit, error: () -> Unit)

    fun getBook(bookId: String, ready: (BookInfo, BookContent) -> Unit, error: () -> Unit)

    fun saveBook(info: BookInfo, content: BookContent)

    fun delBook(bookId: String)

    /**
     * reset cache
     */
    fun refresh()
}
