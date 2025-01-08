package com.imasha.hydrateme.utils

import androidx.appcompat.app.AppCompatDelegate

object AppConstants {

    // Shared Pref Keys
    const val THEME_MODE = "theme_mode"

    // Theme Values
    const val DEFAULT_THEME_MODE = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    const val LIGHT_THEME_MODE = AppCompatDelegate.MODE_NIGHT_NO
    const val DARK_THEME_MODE = AppCompatDelegate.MODE_NIGHT_YES

    // Firebase
    const val DRINKS = "Drinks"
    const val USERS = "Users"
}