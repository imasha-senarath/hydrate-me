package com.imasha.hydrateme.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imasha.hydrateme.data.repository.AppRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val appRepository: AppRepository) : ViewModel() {

    private val _loginStatus = MutableLiveData<Result<Boolean>>()
    val loginStatus: LiveData<Result<Boolean>> get() = _loginStatus

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val result = appRepository.login(email, password)
                _loginStatus.value = Result.success(result)
            } catch (exception: Exception) {
                _loginStatus.value = Result.failure(exception)
            }
        }

        /*appRepository.login(email, password) { result ->
            _loginStatus.value = result
        }*/
    }
}