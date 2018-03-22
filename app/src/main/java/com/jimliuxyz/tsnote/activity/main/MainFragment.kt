package com.jimliuxyz.tsnote.activity.main

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jimliuxyz.tsnote.R
import com.jimliuxyz.tsnote.activity.editor.EditorActivity
import com.jimliuxyz.tsnote.data.book.BookInfo
import dagger.android.support.DaggerFragment
import javax.inject.Inject


/**
 * Created by jimliu on 2018/3/15.
 */

//@ActivityScoped
class MainFragment @Inject constructor() : DaggerFragment(), MainContract.View {

    @Inject
    lateinit var mPresenter: MainContract.Presenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var root = inflater?.inflate(R.layout.main_frag, container, false)

        //set up RecyclerView
        root?.findViewById<RecyclerView>(R.id.recyclerView)?.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            adapter = BookAdapter(arrayListOf(), object : BookAdapter.ItemListener {
                override fun onItemClick(info: BookInfo) {
                    showEditBookActivity(info)
                }

                override fun onItemLongClick(info: BookInfo) {
                    AlertDialog.Builder(context).setMessage(context.getString(R.string.alert_delete_book, info.title))
                            .setPositiveButton(context.getString(R.string.ans_yes), DialogInterface.OnClickListener { dialog, which ->
                                mPresenter.delBook(info.id)
                            })
                            .setNegativeButton(context.getString(R.string.ans_no), DialogInterface.OnClickListener { dialog, which ->
                            })
                            .create().show()
                }
            })
        }
        registerForContextMenu(root);

        //set up refresher
        root?.findViewById<SwipeRefreshLayout>(R.id.refresh_layout)?.apply {
            setColorSchemeColors(
                    ContextCompat.getColor(activity, R.color.colorPrimary),
                    ContextCompat.getColor(activity, R.color.colorAccent),
                    ContextCompat.getColor(activity, R.color.colorPrimaryDark)
            )
            setOnRefreshListener {
                mPresenter.refresh()
                mPresenter.loadBooks()
            }
        }

        activity.findViewById<FloatingActionButton>(R.id.fab)?.apply {
            setOnClickListener { showAddBookActivity() }
        }

        return root
    }

    override fun showAddBookActivity() {
        startActivity(Intent(context, EditorActivity::class.java))
    }

    override fun showEditBookActivity(info: BookInfo) {
        val intent = Intent(context, EditorActivity::class.java).apply {
            putExtra(EditorActivity.EXTRA_BOOK_ID, info.id)
        }
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.attachView(this)
    }

    override fun onPause() {
        super.onPause()
        mPresenter.detachView()
    }

    override fun showRefresher(en: Boolean) {
        view?.findViewById<SwipeRefreshLayout>(R.id.refresh_layout)?.apply {
            post {
                isRefreshing = en
            }
        }
    }

    override fun showBooks(books: List<BookInfo>) {
        (view?.findViewById<RecyclerView>(R.id.recyclerView)?.adapter as BookAdapter?)?.also {
            Handler().post {
                it.setData(books)
            }
        }
    }


}