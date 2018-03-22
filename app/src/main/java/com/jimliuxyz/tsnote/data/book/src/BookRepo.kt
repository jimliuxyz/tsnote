package com.jimliuxyz.tsnote.data.book.src

import com.jimliuxyz.tsnote.data.book.BookContent
import com.jimliuxyz.tsnote.data.book.BookInfo
import com.jimliuxyz.tsnote.data.book.src.local.BookLocalSrc
import com.jimliuxyz.tsnote.di.annotations.Local
import com.jimliuxyz.tsnote.di.annotations.Remote
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by jimliu on 2018/3/16.
 */
@Singleton
class BookRepo @Inject constructor(
        @Local private val localSrc: IBookDataSrc,
        @Remote private val remoteSrc: IBookDataSrc
) : IBookDataSrc {

    private val cacheInfo = LinkedHashMap<String, BookInfo>()
    private val cacheContent = LinkedHashMap<String, BookContent>()
    private var reloadQuest = true
    private val DISABLE_REMOTE = true

    override fun getBookInfoList(ready: (List<BookInfo>) -> Unit, error: () -> Unit) {
        if (!reloadQuest && cacheInfo.isNotEmpty()) {
            //use cache
            ready(cacheInfo.values.toList())
        } else {
            if (reloadQuest && !DISABLE_REMOTE) {
                //use remote
                getRemoteBookList(ready, error)
            } else {
                //use local
                localSrc.getBookInfoList({
                    renewCache(it)
                    ready(it)
                }, {
                    getRemoteBookList(ready, error)
                })
            }
        }
    }

    override fun getBookInfo(bookId: String, ready: (BookInfo) -> Unit, error: () -> Unit) {
    }

    override fun getBookContent(bookId: String, ready: (BookContent) -> Unit, error: () -> Unit) {
        if (!reloadQuest && cacheContent[bookId] != null) {
            //use cache
            ready(cacheContent[bookId]!!)
        } else {
            if (reloadQuest && !DISABLE_REMOTE) {
                //use remote
                getRemoteBookContent(bookId, ready, error)
            } else {
                //use local
                localSrc.getBookContent(bookId, {
                    cacheContent[bookId] = it
                    ready(it)
                }, {
                    getRemoteBookContent(bookId, ready, error)
                })
            }
        }
    }

    override fun getBook(bookId: String, ready: (BookInfo, BookContent) -> Unit, error: () -> Unit) {
        var info = cacheInfo[bookId]

        if (info == null) {
            error()
            return
        }

        getBookContent(bookId, {
            ready(info, it)
        }, {
            error()
        })
    }

    override fun saveBook(info: BookInfo, content: BookContent) {
        val info = BookInfo.clone(info)
        val content = BookContent.clone(content)

        cacheInfo[info.id] = info
        cacheContent[info.id] = content
        localSrc.saveBook(info, content)
        remoteSrc.saveBook(info, content)
    }

    override fun delBook(bookId: String) {
        cacheInfo.remove(bookId)
        cacheContent.remove(bookId)
        localSrc.delBook(bookId)
        remoteSrc.delBook(bookId)
    }

    override fun refresh() {
        cacheInfo.clear()
        cacheContent.clear()
        reloadQuest = false
    }

    private fun getRemoteBookList(ready: (List<BookInfo>) -> Unit, error: () -> Unit) {

        remoteSrc.getBookInfoList({
            renewBookList(it)
            ready(it)
        }, {
            error()
        })

    }

    private fun getRemoteBookContent(bookId: String, ready: (BookContent) -> Unit, error: () -> Unit) {

        remoteSrc.getBookContent(bookId, {
            cacheContent[bookId] = it
            ready(it)
        }, {
            error()
        })

    }

    private fun renewBookList(books: List<BookInfo>) {
        renewCache(books)

        (localSrc as BookLocalSrc)?.let {
            it.updateBookList(books)
        }
    }

    private fun renewCache(books: List<BookInfo>) {
        cacheInfo.clear()
        books.forEach { info ->
            cacheInfo[info.id] = info
        }
    }


}