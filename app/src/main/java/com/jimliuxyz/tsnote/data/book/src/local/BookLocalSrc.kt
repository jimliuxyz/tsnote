package com.jimliuxyz.tsnote.data.book.src.local

import android.content.Context
import com.jimliuxyz.tsnote.R
import com.jimliuxyz.tsnote.data.book.BookContent
import com.jimliuxyz.tsnote.data.book.BookInfo
import com.jimliuxyz.tsnote.data.book.src.IBookDataSrc
import com.jimliuxyz.tsnote.utils.doIO
import com.jimliuxyz.tsnote.utils.doUI
import com.jimliuxyz.tsnote.utils.getPref
import com.jimliuxyz.tsnote.utils.setPref
import javax.inject.Inject

/**
 * Created by jimliu on 2018/3/15.
 */
class BookLocalSrc @Inject constructor(
        val context: Context,
        val dao: BookDao) : IBookDataSrc {

    override fun getBookInfoList(ready: (List<BookInfo>) -> Unit, error: () -> Unit) {
        doIO {
            context.setPref(R.string.key_content_inited, false)
            if (context.getPref(R.string.key_content_inited, false) == false) {
                context.setPref(R.string.key_content_inited, true)
                BookInitContent.init(dao)
            }
            var data = dao.getBookList()

            doUI {

                if (data != null)
                    ready(data)
                else
                    error()
            }
        }
    }

    override fun getBookInfo(bookId: String, ready: (BookInfo) -> Unit, error: () -> Unit) {
        doIO {
            var data = dao.getBookInfo(bookId)
            doUI {
                if (data != null)
                    ready(data)
                else
                    error()
            }
        }
    }

    override fun getBookContent(bookId: String, ready: (BookContent) -> Unit, error: () -> Unit) {
        doIO {
            var data = dao.getBookContent(bookId)
            doUI {
                ready(data)
            }
        }
    }

    override fun getBook(bookId: String, ready: (BookInfo, BookContent) -> Unit, error: () -> Unit) {
        doIO {
            var info = dao.getBookInfo(bookId)
            var content = dao.getBookContent(bookId)
            doUI {
                if (info != null && content != null)
                    ready(info, content)
                else
                    error()
            }
        }
    }

    override fun saveBook(info: BookInfo, content: BookContent) {
        doIO {
            dao.insertBookInfo(info)
            dao.insertBookContent(content)
        }
    }

    override fun delBook(bookId: String) {
        doIO {
            dao.delBookInfo(bookId)
        }
    }

    override fun refresh() {
    }

    fun updateBookList(booklist: List<BookInfo>) {
        doIO {
            dao.insertBookList(booklist)
        }
    }
}
