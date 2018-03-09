package com.jimliuxyz.vocabook.utils

import android.content.Context
import android.preference.PreferenceManager

/**
 * Created by jimliu on 2018/3/8.
 */

/**
 * get sharedPreferences value by key
 */
public inline fun <T : Context, reified R> T.getPref(key: String, def: R): R {

    var value = when (def) {
        is Boolean -> PreferenceManager.getDefaultSharedPreferences(this).getBoolean(key, def) as R
        is String -> PreferenceManager.getDefaultSharedPreferences(this).getString(key, def) as R
        is Float -> PreferenceManager.getDefaultSharedPreferences(this).getFloat(key, def) as R
        is Int -> PreferenceManager.getDefaultSharedPreferences(this).getInt(key, def) as R
        is Long -> PreferenceManager.getDefaultSharedPreferences(this).getLong(key, def) as R
        else -> null
    }
    return value!!
}

/**
 * get sharedPreferences value by strResId of key
 */
public inline fun <T : Context, reified R> T.getPref(strResId: Int, def: R): R {

    var key = resources.getString(strResId)
    var value = when (def) {
        is Boolean -> PreferenceManager.getDefaultSharedPreferences(this).getBoolean(key, def) as R
        is String -> PreferenceManager.getDefaultSharedPreferences(this).getString(key, def) as R
        is Float -> PreferenceManager.getDefaultSharedPreferences(this).getFloat(key, def) as R
        is Int -> PreferenceManager.getDefaultSharedPreferences(this).getInt(key, def) as R
        is Long -> PreferenceManager.getDefaultSharedPreferences(this).getLong(key, def) as R
        else -> null
    }
    return value!!
}

