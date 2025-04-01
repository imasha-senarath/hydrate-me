package com.imasha.hydrateme.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.imasha.hydrateme.R
import com.imasha.hydrateme.ui.home.HomeActivity
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.databinding.ActivityLoginBinding
import com.imasha.hydrateme.ui.base.BaseActivity
import com.imasha.hydrateme.ui.signup.SignUpActivity
import com.imasha.hydrateme.utils.AppDialog.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModels();

        binding.signUp.setOnClickListener {
            navigateToSignUpActivity()
        }

        binding.btnLogin.setOnClickListener {
            validation()
        }
    }

    private fun initViewModels() {
        loginViewModel.loginStatus.observe(this) { result ->
            hideLoading()
            result.onSuccess {
                navigateToMainActivity()
            }.onFailure { exception ->
                clearFields()
                showErrorDialog(exception.message.toString(), this);
            }
        }
    }

    private fun validation() {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this, getString(R.string.field_cant_empty_msg), Toast.LENGTH_SHORT).show()
        } else {
            hideKeyboard(this, currentFocus ?: View(this))
            showLoading()
            val user = User(email = email, password = password)
            loginViewModel.login(user)
        }
    }

    private fun clearFields() {
        binding.editTextEmail.text.clear()
        binding.editTextPassword.text.clear()
    }

    private fun navigateToSignUpActivity() {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}