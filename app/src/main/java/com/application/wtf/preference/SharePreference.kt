package com.application.wtf.preference

import android.content.Context
import android.content.SharedPreferences

class SharePreference(private val context: Context) {

    private val pref: SharedPreferences by lazy {
        context.getSharedPreferences("SHARE", Context.MODE_PRIVATE)
    }

    fun putString(key: String, value: String) =
        pref.edit().putString(key, value).apply()

    fun getString(key: String, default: String?): String {
        if (pref.contains(key)) {
            val value = pref.getString(key, default)
            value?.let {
                return it
            }
        }
        return default ?: ""
    }
}