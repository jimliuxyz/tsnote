package com.jimliuxyz.tsnote.activity.editor

import android.content.ClipboardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.jimliuxyz.tsnote.BaseActivity
import com.jimliuxyz.tsnote.R
import com.jimliuxyz.tsnote.data.book.BookContent
import com.jimliuxyz.tsnote.data.book.BookInfo
import com.jimliuxyz.tsnote.dialog.SelectLangFragment
import com.jimliuxyz.tsnote.utils.MemLeakDetector
import com.jimliuxyz.tsnote.views.TextViewSelector
import kotlinx.android.synthetic.main.editor_aty.*
import javax.inject.Inject
import javax.inject.Named

class EditorActivity : BaseActivity(), EditorContract.View {

    companion object {
        const val EXTRA_BOOK_ID = "BOOK_ID"
    }

    @Inject
    lateinit var mPresenter: EditorContract.Presenter

    @Inject
    @JvmField   //for keeping java field public
    @field:Named(EXTRA_BOOK_ID) //specify the annotation for field
    var mBookId: String? = null

    lateinit var tvSelector: TextViewSelector
    lateinit var tvTitle: TextView
    lateinit var etNote: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editor_aty)
        setSupportActionBar(toolbar)

        //track whether this activity will be recycled
        MemLeakDetector.add(this, "activity")

        tvSelector = findViewById<TextViewSelector>(R.id.textViewSelector).apply {
            setSelectionChangedListener(mPresenter::onSelectionChanged)
        }
        tvTitle = findViewById<TextView>(R.id.tvTitle)
        etNote = findViewById<EditText>(R.id.etNote)

        etNote.apply {
            addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    mPresenter.onNoteTyping(text.toString())
                }
            })
        }


        val processText = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)
        val bookId = intent.getStringExtra(EXTRA_BOOK_ID)

        mPresenter.start(this, bookId, processText?.toString() ?: null)
    }

//    override fun onStart() {
//        super.onStart()
//
//        val processText = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)
//        val bookId = intent.getStringExtra(EXTRA_BOOK_ID)
//
//        mPresenter.start(this, bookId, processText?.toString() ?: null)
//    }

    override fun onPause() {
        super.onPause()

        if (mBookId == null && mPresenter.getBookId() != null) {
            //store current bookId to intent to avoid recreate new book while recreate activity
            intent.putExtra(EXTRA_BOOK_ID, mPresenter.getBookId()!!)
        }

        mPresenter.saveBook(tvTitle.text.toString(), tvSelector.text.toString())
    }

    fun onBtnClear(view: View) {
        mPresenter.onBtnClear()
    }

    fun onBtnDone(view: View) {
        var hint = etNote.text.toString()
        mPresenter.onBtnDone(hint)
    }

    fun onBtnSelectLangPair(view: View) {
        SelectLangFragment().show(supportFragmentManager, "SelectLangFragment")
    }

    override fun showNoteInput(note: String) {
        etNote.setText(note)
    }

    override fun releaseSelection() {
        tvSelector.releaseSelection()
    }

    override fun addKeyNote(key: String, note: String) {
        tvSelector.addKeyNote(key, note)
    }

    override fun removeKeyNote(key: String) {
        tvSelector.removeKeyNote(key)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.editor_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_paste -> {
                val cb = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

                cb.primaryClip?.takeIf { it.itemCount > 0 }?.getItemAt(0)?.text?.toString()?.takeIf { it.isNotBlank() }?.also { newStory ->

                    AlertDialog.Builder(this).setMessage(getString(R.string.alert_paste_new_story))
                            .setPositiveButton(getString(R.string.ans_yes), DialogInterface.OnClickListener { dialog, which ->
                                mPresenter.onPasteStory(newStory)
                            })
                            .setNegativeButton(getString(R.string.ans_no), DialogInterface.OnClickListener { dialog, which ->
                            })
                            .create().show()

                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showBook(info: BookInfo, content: BookContent) {
        tvTitle.setText(info.title, TextView.BufferType.EDITABLE)

        tvSelector.setText(content.story)

        for (note in content.keyNotes) {
            tvSelector.addKeyNote(note.key, note.note)
        }
    }

    override fun setSelectionNote(note: String) {
        tvSelector.setSelNote(note)
    }

    override fun enNoteInput(en: Boolean) {
        etNote.apply {
            isEnabled = en
            hint = if (en) getString(R.string.input_note) else getString(R.string.select_a_word)
        }
    }

    override fun onBackPressed() {
        if (!mPresenter.onBackPressed())
            super.onBackPressed()
    }

    override fun showExitHint() {
        Toast.makeText(this, getString(R.string.press_back_exit), Toast.LENGTH_SHORT).show()
    }
}
