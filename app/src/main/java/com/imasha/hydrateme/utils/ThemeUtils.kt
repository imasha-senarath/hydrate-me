package com.imasha.hydrateme.utils

import androidx.appcompat.app.AppCompatDelegate
import com.imasha.hydrateme.data.enums.Theme
import com.imasha.hydrateme.utils.AppConstants.DARK_THEME_MODE
import com.imasha.hydrateme.utils.AppConstants.DEFAULT_THEME_MODE
import com.imasha.hydrateme.utils.AppConstants.LIGHT_THEME_MODE
import com.imasha.hydrateme.utils.AppConstants.SELECTED_THEME
import com.imasha.hydrateme.utils.SharedPrefManager.getPrefString

object ThemeUtils {

    fun applyTheme(theme: Theme) {
        when (theme) {
            Theme.LIGHT -> AppCompatDelegate.setDefaultNightMode(LIGHT_THEME_MODE)
            Theme.DARK -> AppCompatDelegate.setDefaultNightMode(DARK_THEME_MODE)
            else -> AppCompatDelegate.setDefaultNightMode(DEFAULT_THEME_MODE)
        }
    }

    fun getCurrentTheme(): Theme {
        return when (getPrefString(SELECTED_THEME)) {
            Theme.LIGHT.toString() -> Theme.LIGHT
            Theme.DARK.toString() -> Theme.DARK
            else -> Theme.SYSTEM
        }
    }
}
