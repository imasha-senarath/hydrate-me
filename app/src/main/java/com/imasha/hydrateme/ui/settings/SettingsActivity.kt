package com.imasha.hydrateme.ui.settings

import android.os.Bundle
import com.imasha.hydrateme.R
import com.imasha.hydrateme.databinding.ActivityProfileBinding
import com.imasha.hydrateme.databinding.ActivitySettingsBinding
import com.imasha.hydrateme.ui.base.BaseActivity
import com.imasha.hydrateme.utils.AppConstants
import com.imasha.hydrateme.utils.AppDialog.showThemeSelectionDialog
import com.imasha.hydrateme.utils.SharedPrefManager
import com.imasha.hydrateme.utils.SharedPrefManager.getInt
import com.imasha.hydrateme.utils.ThemeUtils.getCurrentTheme

class SettingsActivity : BaseActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_settings)

        setUpToolbar(binding.toolbar, R.string.settings, true)

        binding.theme.text = getCurrentTheme()

        binding.themeBtn.setOnClickListener {
            showThemeSelectionDialog(this)
        }
    }
}