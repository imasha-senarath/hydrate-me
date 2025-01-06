package com.imasha.hydrateme.utils

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.imasha.hydrateme.utils.AppConstants.DARK_THEME_MODE
import com.imasha.hydrateme.utils.AppConstants.DEFAULT_THEME_MODE
import com.imasha.hydrateme.utils.AppConstants.LIGHT_THEME_MODE
import com.imasha.hydrateme.utils.AppConstants.THEME_MODE
import com.imasha.hydrateme.utils.SharedPrefManager.getInt

object ThemeUtils {
    fun applyTheme(theme: Int,context: Context) {
        AppCompatDelegate.setDefaultNightMode(theme)

        if (context is Activity) {
            context.recreate()
        }
    }

    fun getCurrentTheme(): String {
        return when (getInt(THEME_MODE)) {
            DEFAULT_THEME_MODE -> "System Theme"
            LIGHT_THEME_MODE -> "Light Theme"
            DARK_THEME_MODE -> "Dark Theme"
            else -> "Unknown"
        }
    }
}
