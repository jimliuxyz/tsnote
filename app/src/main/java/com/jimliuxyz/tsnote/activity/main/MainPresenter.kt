package com.jimliuxyz.tsnote.activity.main

import com.jimliuxyz.tsnote.data.book.BookInfo
import com.jimliuxyz.tsnote.data.book.src.BookRepo
import javax.inject.Inject

/**
 * Created by jimliu on 2018/3/15.
 */
class MainPresenter @Inject constructor(val repo: BookRepo) : MainContract.Presenter {

    private var mView: MainContract.View? = null

    private var mCache: List<BookInfo>? = null

    override fun attachView(v: MainContract.View) {
        mView = v
        loadBooks()
    }

    override fun detachView() {
        mView = null
    }

    override fun loadBooks() {
        val mView = mView ?: return

        var done = false
        repo.getBookInfoList({
            done = true
            mView.showBooks(sort(it))
            mView.showRefresher(false)
        }, {
            done = true
            mView.showRefresher(false)
        })
        if (!done)
            mView.showRefresher(true)
    }

    override fun delBook(bookId: String) {
        val mView = mView ?: return

        repo.delBook(bookId)
        repo.getBookInfoList({
            mView.showBooks(sort(it))
        }, {
        })
    }

    override fun refresh() {
        repo.refresh()
    }

    private fun sort(books: List<BookInfo>): List<BookInfo> {
        return books.sortedByDescending { it.attentionDate }
    }
}
