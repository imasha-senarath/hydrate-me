package com.imasha.hydrateme.ui.settings

import android.os.Bundle
import com.imasha.hydrateme.R
import com.imasha.hydrateme.data.enums.Language
import com.imasha.hydrateme.data.enums.Theme
import com.imasha.hydrateme.databinding.ActivitySettingsBinding
import com.imasha.hydrateme.ui.base.BaseActivity
import com.imasha.hydrateme.utils.AppConstants.SELECTED_LANGUAGE
import com.imasha.hydrateme.utils.AppConstants.SELECTED_THEME
import com.imasha.hydrateme.utils.AppDialog.showSelectionDialog
import com.imasha.hydrateme.utils.LanguageUtils.applyLanguage
import com.imasha.hydrateme.utils.LanguageUtils.getCurrentLanguage
import com.imasha.hydrateme.utils.SharedPrefManager.saveString
import com.imasha.hydrateme.utils.ThemeUtils.applyTheme
import com.imasha.hydrateme.utils.ThemeUtils.getCurrentTheme

class SettingsActivity : BaseActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_settings)

        setUpToolbar(binding.toolbar, R.string.settings, true)

        setThemeName(getCurrentTheme());
        setLanguageName(getCurrentLanguage())

        binding.themeBtn.setOnClickListener {
            showSelectionDialog(this, getCurrentTheme(), Theme::class.java) { selectedTheme ->
                applyTheme(selectedTheme)
                saveString(SELECTED_THEME, selectedTheme.toString())
                setThemeName(selectedTheme)
            }
        }

        binding.languageBtn.setOnClickListener {
            showSelectionDialog(this, getCurrentLanguage(), Language::class.java) { selectedLanguage ->
                applyLanguage(selectedLanguage, this)
                saveString(SELECTED_LANGUAGE, selectedLanguage.toString())
                setLanguageName(selectedLanguage)
            }
        }
    }

    private fun setThemeName(theme: Theme) {
        binding.theme.text = theme.toString()
    }

    private fun setLanguageName(language: Language) {
        binding.language.text = language.toString()
    }
}