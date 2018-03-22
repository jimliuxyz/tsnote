package com.jimliuxyz.vocabook.activity.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jimliuxyz.vocabook.R
import com.jimliuxyz.vocabook.data.book.BookInfo
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jimliu on 2018/3/15.
 */
class BookAdapter(var books: List<BookInfo>, val itemListener: ItemListener) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    interface ItemListener {
        fun onItemClick(info: BookInfo)
        fun onItemLongClick(info: BookInfo)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var text1: TextView
        var text2: TextView
        val fmt = SimpleDateFormat("yyyy-MM-dd EEEE HH:mm", Locale.getDefault())
        lateinit var info: BookInfo

        init {
            text1 = view.findViewById<TextView>(R.id.text1)
            text2 = view.findViewById<TextView>(R.id.text2)
            view.setOnClickListener {
                itemListener.onItemClick(info)
            }
            view.setOnLongClickListener {
                itemListener.onItemLongClick(info)
                true
            }
        }

        fun bindData(pos: Int) {
            info = books[pos]
            text1.setText(books[pos].title)

            text2.setText(fmt.format(books[pos].attentionDate))


//            public static String getDateStr(long milliseconds) {
//                return new SimpleDateFormat("yyyy年MM月dd日 EEEE HH点mm分", Locale.CHINA).format(milliseconds);
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent?.context).inflate(android.R.layout.simple_list_item_2, parent, false)
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.main_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindData(position)
    }

    fun setData(books_: List<BookInfo>) {
        books = books_
        notifyDataSetChanged()
    }
}
