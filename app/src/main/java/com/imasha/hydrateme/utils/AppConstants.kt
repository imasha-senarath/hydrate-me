package com.imasha.hydrateme.utils

import androidx.appcompat.app.AppCompatDelegate

object AppConstants {

    // Shared Pref Keys
    const val SELECTED_THEME = "selected_theme"
    const val SELECTED_LANGUAGE = "selected_language"
    const val IS_INIT_REMINDER = "is_init_reminder"

    // Theme Values
    const val DEFAULT_THEME_MODE = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    const val LIGHT_THEME_MODE = AppCompatDelegate.MODE_NIGHT_NO
    const val DARK_THEME_MODE = AppCompatDelegate.MODE_NIGHT_YES

    // Languages Values
    const val ENGLISH_LANGUAGE = "en"
    const val SINHALA_LANGUAGE = "si"

    // Firebase Keys
    const val DRINKS_DOC = "Drinks"
    const val USERS_DOC = "Users"

    // Dialog Types
    const val NAME_DIALOG = "Name"
    const val WEIGHT_DIALOG = "Weight"

    // Notify Types
    const val REMINDER_NOTIFY = "reminder_notification"
}