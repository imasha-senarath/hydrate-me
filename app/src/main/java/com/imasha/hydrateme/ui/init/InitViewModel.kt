package com.imasha.hydrateme.ui.init

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imasha.hydrateme.domain.usecase.AuthenticationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitViewModel @Inject constructor(private val authenticationUseCase: AuthenticationUseCase) : ViewModel() {

    private val _splashState = MutableLiveData<Result<Boolean>>()
    val splashState: LiveData<Result<Boolean>> get() = _splashState

    fun userAuthentication(duration: Long) {
        viewModelScope.launch {
            try {
                delay(duration)
                val isAuthenticated = authenticationUseCase.userAuthentication()
                _splashState.value = Result.success(isAuthenticated)
            } catch (exception: Exception) {
                _splashState.value = Result.failure(exception)
            }
        }
    }
}