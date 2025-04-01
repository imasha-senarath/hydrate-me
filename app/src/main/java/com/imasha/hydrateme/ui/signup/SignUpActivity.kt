package com.imasha.hydrateme.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.imasha.hydrateme.R
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.databinding.ActivitySignUpBinding
import com.imasha.hydrateme.ui.base.BaseActivity
import com.imasha.hydrateme.ui.home.HomeActivity
import com.imasha.hydrateme.utils.AppDialog.showErrorDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val signUpViewModel: SignUpViewModel by viewModels()

    private var user: User = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModels();

        binding.login.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnSignup.setOnClickListener {
            validation()
        }
    }

    private fun initViewModels() {
        signUpViewModel.signUpStatus.observe(this) { result ->
            result.onSuccess {
                signUpViewModel.saveProfile(user)
            }.onFailure { exception ->
                hideLoading()
                showErrorDialog(exception.message.toString(), this);
            }
        }

        signUpViewModel.saveProfileStatus.observe(this) { result ->
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
        val name = binding.editTextName.text.toString()
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            Toast.makeText(this, getString(R.string.field_cant_empty_msg), Toast.LENGTH_SHORT).show()
        } else {
            hideKeyboard(this, currentFocus ?: View(this))
            showLoading()
            user = User(name = name, email = email, password = password)
            signUpViewModel.signUp(user)
        }
    }

    private fun clearFields() {
        binding.editTextName.text.clear()
        binding.editTextEmail.text.clear()
        binding.editTextPassword.text.clear()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}