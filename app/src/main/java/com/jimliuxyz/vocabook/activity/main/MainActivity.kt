package com.jimliuxyz.vocabook.activity.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.jimliuxyz.vocabook.BaseActivity
import com.jimliuxyz.vocabook.R
import com.jimliuxyz.vocabook.activity.editor.EditorActivity
import com.jimliuxyz.vocabook.activity.prefs.MainSettingActivity
import dagger.Lazy
import kotlinx.android.synthetic.main.main_aty.*
import javax.inject.Inject


class MainActivity : BaseActivity() {

    @Inject
    lateinit var fragmentProvider: Lazy<MainFragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_aty)
        setSupportActionBar(toolbar)

        //set up fragment
        if (supportFragmentManager.findFragmentById(R.id.contentFrame) == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.contentFrame, fragmentProvider.get())
                commit()
            }
        }
//        gotoEditor()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                gotoMainSetting(); true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun gotoMainSetting() {
        startActivityForResult(Intent(this@MainActivity, MainSettingActivity::class.java), 0)
    }

    fun gotoEditor() {

        val intent = Intent(this, EditorActivity::class.java).apply {
            putExtra(EditorActivity.EXTRA_BOOK_ID, "HFCof5")
        }
        startActivity(intent)
        startActivity(Intent(this@MainActivity, EditorActivity::class.java))
    }
}


