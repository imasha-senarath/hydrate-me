package com.imasha.hydrateme.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.imasha.hydrateme.ui.home.HomeActivity
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.data.repository.AppRepository
import com.imasha.hydrateme.data.source.FirebaseSource
import com.imasha.hydrateme.databinding.ActivityLoginBinding
import com.imasha.hydrateme.ui.signup.SignUpActivity
import com.imasha.hydrateme.utils.AppDialog

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    private lateinit var appDialog: AppDialog;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_login)

        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseSource = FirebaseSource(firebaseAuth)
        val appRepository = AppRepository(firebaseSource)
        val factory = LoginViewModelFactory(appRepository)

        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        initViewModels();

        appDialog = AppDialog(this);

        binding.signUp.setOnClickListener {
            navigateToSignUpActivity()
        }

        binding.btnLogin.setOnClickListener {
            validation()
        }
    }

    private fun initViewModels() {
        loginViewModel.loginStatus.observe(this) { result ->

            //appDialog.hideLoadingDialog()

            result.onSuccess {
                navigateToMainActivity()
            }.onFailure { exception ->
                appDialog.showErrorDialog(exception.message.toString());
            }
        }
    }

    private fun validation() {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Fields cannot be empty.", Toast.LENGTH_SHORT).show()
        } else {
            //appDialog.showLoadingDialog()
            val user = User("", "", email, password)
            loginViewModel.login(user)
        }
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