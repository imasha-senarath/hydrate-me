package com.imasha.hydrateme.ui.settings

import android.content.res.Configuration
import android.os.Bundle
import com.imasha.hydrateme.R
import com.imasha.hydrateme.data.enums.Language
import com.imasha.hydrateme.data.enums.Theme
import com.imasha.hydrateme.databinding.ActivitySettingsBinding
import com.imasha.hydrateme.ui.base.BaseActivity
import com.imasha.hydrateme.utils.AppConstants
import com.imasha.hydrateme.utils.AppConstants.DARK_THEME_MODE
import com.imasha.hydrateme.utils.AppConstants.DEFAULT_THEME_MODE
import com.imasha.hydrateme.utils.AppConstants.ENGLISH_LANGUAGE
import com.imasha.hydrateme.utils.AppConstants.LIGHT_THEME_MODE
import com.imasha.hydrateme.utils.AppConstants.SELECTED_LANGUAGE
import com.imasha.hydrateme.utils.AppConstants.SELECTED_THEME
import com.imasha.hydrateme.utils.AppConstants.SINHALA_LANGUAGE
import com.imasha.hydrateme.utils.AppDialog.showSelectionDialog
import com.imasha.hydrateme.utils.LanguageUtils.applyLanguage
import com.imasha.hydrateme.utils.SharedPrefManager.getPrefString
import com.imasha.hydrateme.utils.SharedPrefManager.saveInt
import com.imasha.hydrateme.utils.SharedPrefManager.saveString
import com.imasha.hydrateme.utils.ThemeUtils.applyTheme
import com.imasha.hydrateme.utils.ThemeUtils.getCurrentTheme
import java.util.*

class SettingsActivity : BaseActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_settings)

        setUpToolbar(binding.toolbar, R.string.settings, true)

        setThemeName();
        setLanguageName()

        binding.themeBtn.setOnClickListener {
            showSelectionDialog(this, getCurrentTheme(), Theme::class.java) { selectedTheme ->
                when (selectedTheme) {
                    Theme.LIGHT -> {
                        applyTheme(LIGHT_THEME_MODE)
                        saveInt(SELECTED_THEME, LIGHT_THEME_MODE)
                    }
                    Theme.DARK -> {
                        applyTheme(DARK_THEME_MODE)
                        saveInt(SELECTED_THEME, DARK_THEME_MODE)
                    }
                    else -> {
                        applyTheme(DEFAULT_THEME_MODE)
                        saveInt(SELECTED_THEME, DEFAULT_THEME_MODE)
                    }
                }.also {
                    setThemeName()
                }
            }
        }

        binding.languageBtn.setOnClickListener {
            showSelectionDialog(this, Language.ENGLISH, Language::class.java) { selectedLanguage ->
                when (selectedLanguage) {
                    Language.ENGLISH -> {
                        applyLanguage(ENGLISH_LANGUAGE, this)
                        saveString(SELECTED_LANGUAGE, ENGLISH_LANGUAGE)
                    }
                    Language.SINHALA -> {
                        applyLanguage(SINHALA_LANGUAGE, this)
                        saveString(SELECTED_LANGUAGE, SINHALA_LANGUAGE)
                    }
                    else -> {
                        applyLanguage(ENGLISH_LANGUAGE, this)
                    }
                }.also {

                }
            }
        }
    }

    private fun setThemeName() {
        binding.theme.text = getCurrentTheme().toString()
    }

    private fun setLanguageName() {
        binding.language.text = getPrefString(SELECTED_LANGUAGE)
    }
}