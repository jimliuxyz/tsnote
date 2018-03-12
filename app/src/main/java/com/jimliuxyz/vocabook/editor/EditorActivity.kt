package com.jimliuxyz.vocabook.editor

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.jimliuxyz.vocabook.BaseActivity
import com.jimliuxyz.vocabook.R
import com.jimliuxyz.vocabook.data.book.BookContent
import com.jimliuxyz.vocabook.data.book.BookInfo
import com.jimliuxyz.vocabook.data.book.Word
import com.jimliuxyz.vocabook.data.book.src.local.BookDao
import com.jimliuxyz.vocabook.utils.MemLeakDetector
import kotlinx.android.synthetic.main.activity_editor.*
import javax.inject.Inject
import kotlin.concurrent.thread


class EditorActivity : BaseActivity() {

    @Inject
    lateinit var dao: BookDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        setSupportActionBar(toolbar)


        println("dao : $dao")

        thread(start = true) {
            dao.clear()
            println("---test database")

            var book = BookInfo(title = "my book")
            var content = BookContent(book.id)
            content.words.add(Word("aaa", "bbb"))

            dao.insertBookInfo(book)
            dao.insertBookContent(content)

            var list = dao.getBookInfos()
            println(list)

            var bset = dao.getBookSet(book.id)
            println(bset)
        }

        //track whether this activity will be recycled
        MemLeakDetector.add(this, "activity")

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_editor, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
