package com.imasha.hydrateme.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    private val _loginStatus = MutableLiveData<Result<Boolean>>()
    val loginStatus: LiveData<Result<Boolean>> get() = _loginStatus

    fun login(user: User) {
        viewModelScope.launch {
            try {
                val result = appRepository.login(user)
                _loginStatus.value = Result.success(result)
            } catch (exception: Exception) {
                _loginStatus.value = Result.failure(exception)
            }
        }
    }
}