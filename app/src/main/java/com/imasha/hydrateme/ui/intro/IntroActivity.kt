package com.imasha.hydrateme.ui.intro

import android.content.Intent
import android.os.Bundle
import com.imasha.hydrateme.databinding.ActivityIntroBinding
import com.imasha.hydrateme.ui.base.BaseActivity
import com.imasha.hydrateme.ui.login.LoginActivity
import com.imasha.hydrateme.utils.AppConstants.IS_FIRST_TIME
import com.imasha.hydrateme.utils.SharedPrefManager.savePrefBoolean

class IntroActivity : BaseActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getStartedBtn.setOnClickListener {
            savePrefBoolean(IS_FIRST_TIME, false)
            navigateToLoginActivity()
        }
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}