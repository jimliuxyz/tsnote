package com.jimliuxyz.vocabook.activity.main

import com.jimliuxyz.vocabook.data.book.BookInfo

/**
 * Created by jimliu on 2018/3/15.
 */
interface MainContract {

    interface View {
        fun showBooks(books: List<BookInfo>)
        fun showRefresher(en: Boolean)

        fun showAddBookActivity()
        fun showEditBookActivity(info: BookInfo)
    }

    interface Presenter {
        fun attachView(v: View)
        fun detachView()

        fun loadBooks()
        fun refresh()

        fun delBook(bookId: String)
    }
}
