package com.imasha.hydrateme.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.data.repository.AppRepository
import kotlinx.coroutines.launch

class HomeViewModel (private val appRepository: AppRepository) : ViewModel() {

    private val _logoutStatus = MutableLiveData<Result<Boolean>>()
    val logoutStatus: LiveData<Result<Boolean>> get() = _logoutStatus

    fun logout() {
        viewModelScope.launch {
            try {
                val result = appRepository.logout()
                if (result) {
                    _logoutStatus.value = Result.success(true)
                } else {
                    _logoutStatus.value = Result.failure(Exception("Logout failed"))
                }
            } catch (exception: Exception) {
                _logoutStatus.value = Result.failure(exception)
            }
        }
    }
}