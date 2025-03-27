package com.imasha.hydrateme.presentation.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.imasha.hydrateme.R
import com.imasha.hydrateme.data.enums.Language
import com.imasha.hydrateme.data.enums.Theme
import com.imasha.hydrateme.databinding.ActivitySettingsBinding
import com.imasha.hydrateme.presentation.base.BaseActivity
import com.imasha.hydrateme.utils.AppConstants.IS_NOTIFICATION_ON
import com.imasha.hydrateme.utils.AppConstants.SETTING_CHANGE_REQUEST
import com.imasha.hydrateme.utils.AppConstants.SELECTED_LANGUAGE
import com.imasha.hydrateme.utils.AppConstants.SELECTED_THEME
import com.imasha.hydrateme.utils.AppDialog.showSelectionDialog
import com.imasha.hydrateme.utils.LanguageUtils.applyLanguage
import com.imasha.hydrateme.utils.LanguageUtils.getCurrentLanguage
import com.imasha.hydrateme.utils.SharedPrefManager.getPrefBoolean
import com.imasha.hydrateme.utils.SharedPrefManager.savePrefBoolean
import com.imasha.hydrateme.utils.SharedPrefManager.savePrefString
import com.imasha.hydrateme.utils.ThemeUtils.applyTheme
import com.imasha.hydrateme.utils.ThemeUtils.getCurrentTheme

class SettingsActivity : BaseActivity() {

    private lateinit var binding: ActivitySettingsBinding

    private var isLanguageChanged: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpToolbar(binding.toolbar, R.string.settings_title, true)

        setThemeName(getCurrentTheme());
        setLanguageName(getCurrentLanguage())
        setNotificationSwitch()

        binding.appVersion.text = getString(R.string.app_version, getAppVersion())

        binding.themeBtn.setOnClickListener {
            showSelectionDialog(this, getCurrentTheme(), Theme::class.java) { selectedTheme ->
                applyTheme(selectedTheme)
                savePrefString(SELECTED_THEME, selectedTheme.toString())
                setThemeName(selectedTheme)
            }
        }

        binding.languageBtn.setOnClickListener {
            showSelectionDialog(this, getCurrentLanguage(), Language::class.java) { selectedLanguage ->
                applyLanguage(selectedLanguage, this)
                savePrefString(SELECTED_LANGUAGE, selectedLanguage.toString())
                setLanguageName(selectedLanguage)

                recreate()
                isLanguageChanged = true
            }
        }

        binding.notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            savePrefBoolean(IS_NOTIFICATION_ON , isChecked)
            setNotificationSwitch()
        }

        binding.appOwner.setOnClickListener {
            val url = "https://www.imashasenarath.com/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    private fun setThemeName(theme: Theme) {
        binding.theme.text = theme.toString()
    }

    private fun setLanguageName(language: Language) {
        binding.language.text = language.toString()
    }

    private fun setNotificationSwitch() {
        binding.notificationSwitch.isChecked = getPrefBoolean(IS_NOTIFICATION_ON, true)
    }

    override fun onBackPressed() {
        setResult(SETTING_CHANGE_REQUEST)
        super.onBackPressed()
    }
}