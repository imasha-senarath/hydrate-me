package com.imasha.hydrateme.presentation.init

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.imasha.hydrateme.R
import com.imasha.hydrateme.databinding.ActivityInitBinding
import com.imasha.hydrateme.presentation.home.HomeActivity
import com.imasha.hydrateme.presentation.base.BaseActivity
import com.imasha.hydrateme.presentation.intro.IntroActivity
import com.imasha.hydrateme.presentation.login.LoginActivity
import com.imasha.hydrateme.utils.AppConstants.IS_FIRST_TIME
import com.imasha.hydrateme.utils.AppLogger
import com.imasha.hydrateme.utils.SharedPrefManager.getPrefBoolean
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitActivity : BaseActivity() {

    private val className = this::class.java.simpleName

    private lateinit var binding: ActivityInitBinding
    private val initViewModel: InitViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appVersion.text = getString(R.string.app_version, getAppVersion())

        initViewModels();

        initViewModel.userAuthentication(500);
    }

    private fun initViewModels() {
        initViewModel.splashState.observe(this) { result ->
            result.onSuccess { isAuthenticated ->
                if (isAuthenticated) {
                    navigateToMainActivity()
                } else {
                    navigateToIntroActivity().takeIf { getPrefBoolean(IS_FIRST_TIME, true) } ?: navigateToLoginActivity()
                }
            }.onFailure { exception ->
                AppLogger.d(className, exception.message.toString())
                navigateToLoginActivity()
            }
        }
    }

    private fun navigateToIntroActivity() {
        val intent = Intent(this, IntroActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}