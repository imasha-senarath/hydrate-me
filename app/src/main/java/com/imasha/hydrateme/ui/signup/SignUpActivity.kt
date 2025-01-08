package com.imasha.hydrateme.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.imasha.hydrateme.R
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.data.repository.AppRepository
import com.imasha.hydrateme.data.source.FirebaseSource
import com.imasha.hydrateme.databinding.ActivityLoginBinding
import com.imasha.hydrateme.databinding.ActivitySignUpBinding
import com.imasha.hydrateme.ui.login.LoginViewModel
import com.imasha.hydrateme.ui.login.LoginViewModelFactory
import com.imasha.hydrateme.utils.AppDialog
import com.imasha.hydrateme.utils.AppDialog.showErrorDialog
import com.imasha.hydrateme.utils.AppDialog.showInfoDialog

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_sign_up)

        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseSource = FirebaseSource(firebaseAuth)
        val appRepository = AppRepository(firebaseSource)
        val factory = SignUpViewModelFactory(appRepository)

        signUpViewModel = ViewModelProvider(this, factory)[SignUpViewModel::class.java]

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
                showInfoDialog("Success", this);
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