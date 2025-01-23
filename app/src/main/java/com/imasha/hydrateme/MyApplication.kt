package com.imasha.hydrateme

import android.app.Application
import com.imasha.hydrateme.utils.SharedPrefManager
import com.imasha.hydrateme.utils.ThemeUtils.applyTheme
import com.imasha.hydrateme.utils.ThemeUtils.getCurrentTheme
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        SharedPrefManager.init(this)

        applyTheme(getCurrentTheme())
    }
}