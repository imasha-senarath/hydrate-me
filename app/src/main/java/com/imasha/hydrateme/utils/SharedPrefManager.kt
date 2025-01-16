package com.imasha.hydrateme.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPrefManager {
    private const val PREF_NAME = "AppPreferences"
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun savePrefString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun getPrefString(key: String, defaultValue: String = ""): String {
        return preferences.getString(key, defaultValue) ?: defaultValue
    }

    fun savePrefBoolean(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    fun getPrefBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    fun savePrefInt(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    fun getPrefInt(key: String, defaultValue: Int = 0): Int {
        return preferences.getInt(key, defaultValue)
    }

    fun removePref(key: String) {
        preferences.edit().remove(key).apply()
    }

    fun clearPref() {
        preferences.edit().clear().apply()
    }
}
