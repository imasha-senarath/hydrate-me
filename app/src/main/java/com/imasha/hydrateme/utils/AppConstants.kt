package com.imasha.hydrateme.utils

import androidx.appcompat.app.AppCompatDelegate

object AppConstants {

    // Shared Pref Keys
    const val THEME_MODE = "theme_mode"

    // Theme Values
    const val DEFAULT_THEME_MODE = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    const val LIGHT_THEME_MODE = AppCompatDelegate.MODE_NIGHT_NO
    const val DARK_THEME_MODE = AppCompatDelegate.MODE_NIGHT_YES

    // Firebase Keys
    const val DRINKS_DOC = "Drinks"
    const val USERS_DOC = "Users"

    // Dialog Types
    const val NAME_DIALOG = "Name"
    const val WEIGHT_DIALOG = "Weight"
}