package com.jimliuxyz.tsnote.activity.prefs

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.support.v7.app.AlertDialog
import com.jimliuxyz.tsnote.BaseActivity
import com.jimliuxyz.tsnote.R


/**
 * Created by jimliu on 2018/3/8.
 */
class MainSettingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentManager.findFragmentById(android.R.id.content) ?: apply {
            fragmentManager.beginTransaction().apply {
                replace(android.R.id.content, PrefsFragment())
            }.commit()
        }
    }

    class PrefsFragment : PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.preferences)
        }

        var onClickListener = Preference.OnPreferenceClickListener { preference ->
            showAbout()
            true
        }

        var onChangedListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            when (key) {
                resources.getString(R.string.key_theme) -> {
                    var intent = Intent(activity, MainSettingActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
        }

        fun showAbout() {
            var dialog = AlertDialog.Builder(activity)
                    .setView(R.layout.about_layout)
                    .setPositiveButton("ok", { dialog, witch ->
                    })
                    .create()

            dialog.getWindow().setWindowAnimations(android.R.style.Animation_Translucent)
            dialog.show()
        }

        override fun onResume() {
            super.onResume()
            preferenceManager.findPreference(resources.getString(R.string.key_about)).setOnPreferenceClickListener(onClickListener)
            preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(onChangedListener)
        }

        override fun onPause() {
            super.onPause()
            preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(onChangedListener)
        }

    }
}
