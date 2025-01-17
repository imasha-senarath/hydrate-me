package com.imasha.hydrateme.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.databinding.ActivitySignUpBinding
import com.imasha.hydrateme.utils.AppDialog.showErrorDialog
import com.imasha.hydrateme.utils.AppDialog.showInfoDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val signUpViewModel: SignUpViewModel by viewModels()

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
                //navigateToMainActivity()
                showInfoDialog("Success","", this)
            }.onFailure { exception ->
                showErrorDialog(exception.message.toString(), this);
            }
        }
    }

    private fun validation() {
        val name = binding.editTextName.text.toString()
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Fields cannot be empty.", Toast.LENGTH_SHORT).show()
        } else {
            val user = User(email, password)
            signUpViewModel.signUp(user)
        }
    }
}