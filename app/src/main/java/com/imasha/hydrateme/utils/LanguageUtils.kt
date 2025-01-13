package com.imasha.hydrateme.utils

import android.content.Context
import android.content.res.Configuration
import com.imasha.hydrateme.data.enums.Language
import com.imasha.hydrateme.utils.AppConstants.ENGLISH_LANGUAGE
import com.imasha.hydrateme.utils.AppConstants.SELECTED_LANGUAGE
import com.imasha.hydrateme.utils.AppConstants.SINHALA_LANGUAGE
import com.imasha.hydrateme.utils.SharedPrefManager.getPrefString
import java.util.*

object LanguageUtils {

    fun applyLanguage(language: Language, context: Context) {
        var locale = Locale(ENGLISH_LANGUAGE)

        when (language) {
            Language.ENGLISH -> locale = Locale(ENGLISH_LANGUAGE)
            Language.SINHALA -> locale = Locale(SINHALA_LANGUAGE)
        }.also {
            Locale.setDefault(locale)
            val config = Configuration()
            config.setLocale(locale)
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }
    }

    fun getCurrentLanguage(): Language {
        return when (getPrefString(SELECTED_LANGUAGE)) {
            Language.ENGLISH.toString() -> Language.ENGLISH
            Language.SINHALA.toString() -> Language.SINHALA
            else -> Language.ENGLISH
        }
    }
}
