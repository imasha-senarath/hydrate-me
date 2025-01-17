package com.imasha.hydrateme.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.imasha.hydrateme.ui.home.HomeActivity
import com.imasha.hydrateme.databinding.ActivitySplashBinding
import com.imasha.hydrateme.ui.login.LoginActivity
import com.imasha.hydrateme.utils.AppLogger
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val className = this::class.java.simpleName

    private lateinit var binding: ActivitySplashBinding
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModels();

        splashViewModel.userAuthentication(500);
    }

    private fun initViewModels() {
        splashViewModel.splashState.observe(this) { result ->
            result.onSuccess { isAuthenticated ->
                if (isAuthenticated) {
                    navigateToMainActivity()
                } else {
                    navigateToLoginActivity()
                }
            }.onFailure { exception ->
                AppLogger.d(className, exception.message.toString())
                navigateToLoginActivity()
            }
        }
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}