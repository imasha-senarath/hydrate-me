package com.imasha.hydrateme.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel(){

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _loginStatus = MutableLiveData<Result<Boolean>>()
    val loginStatus: LiveData<Result<Boolean>> get() = _loginStatus

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginStatus.value = Result.failure(Exception("Fields cannot be empty"))
            return
        }

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginStatus.value = Result.success(true)
                } else {
                    _loginStatus.value = Result.failure(task.exception ?: Exception("Login failed"))
                }
            }
    }

}