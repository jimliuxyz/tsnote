package com.jimliuxyz.tsnote

import android.content.Intent
import android.os.Bundle
import com.jimliuxyz.tsnote.utils.getPref
import dagger.android.support.DaggerAppCompatActivity

/**
 * Created by jimliu on 2018/3/9.
 */
abstract class BaseActivity : DaggerAppCompatActivity() {
    var darktheme = false
    override fun onCreate(savedInstanceState: Bundle?) {

        darktheme = this.getPref(R.string.key_theme, false)
        setTheme(if (darktheme) R.style.AppThemeDark else R.style.AppTheme)

        super.onCreate(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()

        if (darktheme != this.getPref(R.string.key_theme, false)) {
            //for replacing recreate() to avoid error
            this.finish()
            this.startActivity(this.getIntent())
        }
    }
}