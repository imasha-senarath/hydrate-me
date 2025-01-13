package com.imasha.hydrateme

import android.app.Application
import com.imasha.hydrateme.utils.LanguageUtils.applyLanguage
import com.imasha.hydrateme.utils.LanguageUtils.getCurrentLanguage
import com.imasha.hydrateme.utils.SharedPrefManager
import com.imasha.hydrateme.utils.ThemeUtils.applyTheme
import com.imasha.hydrateme.utils.ThemeUtils.getCurrentTheme

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        SharedPrefManager.init(this)

        applyTheme(getCurrentTheme())
        applyLanguage(getCurrentLanguage(), this)
    }
}