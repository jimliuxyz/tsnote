package com.jimliuxyz.vocabook.utils

import android.content.Context
import android.preference.PreferenceManager

/**
 * Created by jimliu on 2018/3/8.
 */

/**
 * get sharedPreferences value by key
 */
inline fun <T : Context, reified R> T.getPref(key: String, def: R): R {

    var preference = PreferenceManager.getDefaultSharedPreferences(this)

    var value = when (def) {
        is Boolean -> preference.getBoolean(key, def) as R
        is String -> preference.getString(key, def) as R
        is Float -> preference.getFloat(key, def) as R
        is Int -> preference.getInt(key, def) as R
        is Long -> preference.getLong(key, def) as R
        else -> null
    }
    return value!!
}

/**
 * get sharedPreferences value by strResId of key
 */
inline fun <T : Context, reified R> T.getPref(strResId: Int, def: R): R {

    var key = resources.getString(strResId)
    var preference = PreferenceManager.getDefaultSharedPreferences(this)

    var value = when (def) {
        is Boolean -> preference.getBoolean(key, def) as R
        is String -> preference.getString(key, def) as R
        is Float -> preference.getFloat(key, def) as R
        is Int -> preference.getInt(key, def) as R
        is Long -> preference.getLong(key, def) as R
        else -> null
    }
    return value!!
}

inline fun <T : Context, reified R> T.setPref(strResId: Int, value: R) {

    var key = resources.getString(strResId)
    var preference = PreferenceManager.getDefaultSharedPreferences(this).edit()

    when (value) {
        is Boolean -> preference.putBoolean(key, value)
        is String -> preference.putString(key, value)
        is Float -> preference.putFloat(key, value)
        is Int -> preference.putInt(key, value)
        is Long -> preference.putLong(key, value)
        else -> null
    }
    preference.commit()
}

