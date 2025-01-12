package com.imasha.hydrateme.utils

import android.content.Context
import android.content.res.Configuration
import java.util.*

object LanguageUtils {

    fun applyLanguage(languageCode: String, context: Context) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}
