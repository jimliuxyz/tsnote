package com.jimliuxyz.vocabook.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.jimliuxyz.vocabook.BaseActivity
import com.jimliuxyz.vocabook.R
import com.jimliuxyz.vocabook.editor.EditorActivity
import com.jimliuxyz.vocabook.prefs.MainSettingActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            gotoEditor()
        }
        gotoEditor()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
        startActivity(Intent(this@MainActivity, EditorActivity::class.java))
    }
}
