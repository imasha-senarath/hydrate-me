package com.imasha.hydrateme.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.imasha.hydrateme.R
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.imasha.hydrateme.MainActivity
import com.imasha.hydrateme.data.repository.AppRepository
import com.imasha.hydrateme.data.source.FirebaseSource
import com.imasha.hydrateme.databinding.ActivityLoginBinding
import com.imasha.hydrateme.databinding.ActivitySplashBinding
import com.imasha.hydrateme.ui.login.LoginActivity
import com.imasha.hydrateme.ui.login.LoginViewModel
import com.imasha.hydrateme.ui.login.LoginViewModelFactory
import com.imasha.hydrateme.ui.signup.SignUpActivity
import com.imasha.hydrateme.utils.AppLogger

class SplashActivity : AppCompatActivity() {

    private val className = this::class.java.simpleName

    private lateinit var binding: ActivitySplashBinding
    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_splash)

        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseSource = FirebaseSource(firebaseAuth)
        val appRepository = AppRepository(firebaseSource)
        val factory = SplashViewModelFactory(appRepository)

        splashViewModel = ViewModelProvider(this, factory)[SplashViewModel::class.java]

        initViewModels();

        splashViewModel.userAuthentication(1000);
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
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}