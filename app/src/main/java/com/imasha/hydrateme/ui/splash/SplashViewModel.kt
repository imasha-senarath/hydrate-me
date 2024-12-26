package com.imasha.hydrateme.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imasha.hydrateme.data.repository.AppRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(private val appRepository: AppRepository) : ViewModel() {

    private val _splashState = MutableLiveData<Result<Boolean>>()
    val splashState: LiveData<Result<Boolean>> get() = _splashState

    fun userAuthentication(duration: Long) {
        viewModelScope.launch {
            try {
                delay(duration)
                val isAuthenticated = appRepository.userAuthentication()
                _splashState.value = Result.success(isAuthenticated)
            } catch (exception: Exception) {
                _splashState.value = Result.failure(exception)
            }
        }
    }
}