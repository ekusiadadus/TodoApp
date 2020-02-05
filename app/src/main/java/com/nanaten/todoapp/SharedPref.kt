package com.nanaten.todoapp

import android.content.Context
import android.content.SharedPreferences

/**
 * SharedPreferencesのキーの取得、設定を行う
 */
class SharedPref(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("SHARED_PREFERENCE_KEY", Context.MODE_PRIVATE)

    fun getBoolean(keyName: String): Boolean {
        return sharedPreferences.getBoolean(keyName, false)
    }

    fun putBoolean(keyName: String, keyValue: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(keyName, keyValue)
        editor.apply()
    }
}