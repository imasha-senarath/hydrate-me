package com.imasha.hydrateme

import android.app.Application
import com.imasha.hydrateme.utils.AppConstants
import com.imasha.hydrateme.utils.AppConstants.SELECTED_LANGUAGE
import com.imasha.hydrateme.utils.AppConstants.SELECTED_THEME
import com.imasha.hydrateme.utils.LanguageUtils
import com.imasha.hydrateme.utils.LanguageUtils.applyLanguage
import com.imasha.hydrateme.utils.SharedPrefManager
import com.imasha.hydrateme.utils.SharedPrefManager.getInt
import com.imasha.hydrateme.utils.SharedPrefManager.getPrefString
import com.imasha.hydrateme.utils.ThemeUtils.applyTheme

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        SharedPrefManager.init(this)

        applyTheme(getInt(SELECTED_THEME))
        applyLanguage(getPrefString(SELECTED_LANGUAGE), this)
    }
}