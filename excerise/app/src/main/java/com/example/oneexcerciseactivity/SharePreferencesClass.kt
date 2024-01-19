package com.example.oneexcerciseactivity

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.getString

class SharePreferencesClass {
    val mySharePreferences = "mySharePreferences"
    lateinit var context: Context

    constructor(context: Context) {
        this.context = context
    }

    fun putUserValues(key: String, value: String) {
        var sharePreferences = context.getSharedPreferences(mySharePreferences, Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor = sharePreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getUserValues(key: String): String? {
        var getSharedPreferences: SharedPreferences? = context.getSharedPreferences(mySharePreferences, Context.MODE_PRIVATE)
        return getSharedPreferences?.getString(key, "@#")
    }
}