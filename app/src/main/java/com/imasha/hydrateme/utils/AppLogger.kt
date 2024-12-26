package com.imasha.hydrateme.utils

import android.util.Log
import com.imasha.hydrateme.BuildConfig

object AppLogger {

    private const val GLOBAL_TAG = "HydrateMe"

    fun d(tag: String, message: String) {
        if (BuildConfig.DEBUG) {
            Log.d("$GLOBAL_TAG:$tag", message)
        }
    }
}