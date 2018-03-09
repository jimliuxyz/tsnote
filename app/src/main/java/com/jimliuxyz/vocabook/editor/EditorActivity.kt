package com.jimliuxyz.vocabook.editor

import android.content.Context
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.jimliuxyz.vocabook.BaseActivity
import com.jimliuxyz.vocabook.R
import com.jimliuxyz.vocabook.utils.MemLeakDetector
import kotlinx.android.synthetic.main.activity_editor.*


class EditorActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        setSupportActionBar(toolbar)

        //track whether this activity will be recycled
        MemLeakDetector.add(this, "activity")

        val inp = findViewById<TextView>(R.id.editText)
        inp.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                //hide the SoftInput when lose focus
                if (!hasFocus)
                    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(inp.windowToken, 0)
            }
        }

        val edt = findViewById<TextView>(R.id.textView)
        edt.setTextIsSelectable(true)
        edt.setText(SpannableString(edt.text), TextView.BufferType.SPANNABLE);

        //override and disable selection action
        edt.customSelectionActionModeCallback = object : ActionMode.Callback {
            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                return false
            }

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
            }
        }

        edt.setOnClickListener(View.OnClickListener { v: View? ->
            println("click..." + edt.selectionStart)

            val color = resources.getColor(R.color.colorTextHighlight)
            val sp = edt.text as Spannable

            sp.setSpan(BackgroundColorSpan(color), edt.selectionStart, edt.selectionStart + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        })
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
