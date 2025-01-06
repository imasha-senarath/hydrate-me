package com.imasha.hydrateme

import android.app.Application
import com.imasha.hydrateme.utils.AppConstants.THEME_MODE
import com.imasha.hydrateme.utils.SharedPrefManager
import com.imasha.hydrateme.utils.SharedPrefManager.getInt
import com.imasha.hydrateme.utils.ThemeUtils.applyTheme

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        SharedPrefManager.init(this)

        applyTheme(getInt(THEME_MODE), this)
    }
}