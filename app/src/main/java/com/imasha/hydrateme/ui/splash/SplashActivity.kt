package com.imasha.hydrateme.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.imasha.hydrateme.R
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.imasha.hydrateme.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashViewModel.navigateToLogin.observe(this) { shouldNavigate ->
            if (shouldNavigate) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}