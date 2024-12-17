package com.imasha.hydrateme.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean> get() = _navigateToLogin

    init {
        startSplashTimer()
    }

    private fun startSplashTimer() {
        viewModelScope.launch {
            delay(3000) // 3 seconds delay
            _navigateToLogin.postValue(true)
        }
    }
}