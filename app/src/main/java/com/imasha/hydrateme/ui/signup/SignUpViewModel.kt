package com.imasha.hydrateme.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.data.repository.AppRepository
import kotlinx.coroutines.launch

class SignUpViewModel (private val appRepository: AppRepository) : ViewModel() {

    private val _signUpStatus = MutableLiveData<Result<Boolean>>()
    val signUpStatus: LiveData<Result<Boolean>> get() = _signUpStatus

    fun signUp(user: User) {
        viewModelScope.launch {
            try {
                val result = appRepository.signUp(user)
                _signUpStatus.value = Result.success(result)
            } catch (exception: Exception) {
                _signUpStatus.value = Result.failure(exception)
            }
        }
    }

}