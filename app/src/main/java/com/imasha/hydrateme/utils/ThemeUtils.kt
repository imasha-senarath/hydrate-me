package com.imasha.hydrateme.utils

import androidx.appcompat.app.AppCompatDelegate
import com.imasha.hydrateme.data.enums.Theme
import com.imasha.hydrateme.utils.AppConstants.DARK_THEME_MODE
import com.imasha.hydrateme.utils.AppConstants.LIGHT_THEME_MODE
import com.imasha.hydrateme.utils.AppConstants.SELECTED_THEME
import com.imasha.hydrateme.utils.SharedPrefManager.getInt

object ThemeUtils {

    fun applyTheme(theme: Int) {
        AppCompatDelegate.setDefaultNightMode(theme)
    }

    fun getCurrentTheme(): Theme {
        return when (getInt(SELECTED_THEME)) {
            LIGHT_THEME_MODE -> Theme.LIGHT
            DARK_THEME_MODE -> Theme.DARK
            else -> Theme.SYSTEM
        }
    }
}
