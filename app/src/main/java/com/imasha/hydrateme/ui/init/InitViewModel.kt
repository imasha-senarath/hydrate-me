package com.imasha.hydrateme.ui.init

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imasha.hydrateme.domain.usecase.AuthenticationUseCase
import com.imasha.hydrateme.domain.usecase.ReadAppEntryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitViewModel @Inject constructor(
    private val authenticationUseCase: AuthenticationUseCase,
    private val readAppEntryUseCase: ReadAppEntryUseCase
) : ViewModel() {

    private val _splashState = MutableLiveData<Result<Boolean>>()
    val splashState: LiveData<Result<Boolean>> get() = _splashState

    private val _appEntryState = MutableLiveData<Result<Boolean>>()
    val appEntryState: LiveData<Result<Boolean>> get() = _appEntryState

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

    fun appEntry() {
        viewModelScope.launch {
            try {
                val appEntry = readAppEntryUseCase()
                _appEntryState.value = Result.success(appEntry)
            } catch (exception: Exception) {
                _appEntryState.value = Result.failure(exception)
            }
        }
    }
}