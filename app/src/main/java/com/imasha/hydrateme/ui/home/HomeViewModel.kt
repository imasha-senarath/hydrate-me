package com.imasha.hydrateme.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imasha.hydrateme.data.model.Cup
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.data.repository.AppRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val appRepository: AppRepository) : ViewModel() {

    private val _cupSize = MutableLiveData<List<Cup>>()
    val cupSize: LiveData<List<Cup>> get() = _cupSize

    private val _saveDataStatus = MutableLiveData<Result<Boolean>>()
    val saveDataStatus: LiveData<Result<Boolean>> get() = _saveDataStatus

    private val _logoutStatus = MutableLiveData<Result<Boolean>>()
    val logoutStatus: LiveData<Result<Boolean>> get() = _logoutStatus

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> get() = _userId

    fun getUserId() {
        _userId.value = appRepository.getCurrentUserId()
    }

    fun initCupSizes() {
        _cupSize.value = listOf(
            Cup(50),
            Cup(150),
            Cup(200),
            Cup(250),
            Cup(300),
        )
    }

    fun saveData(
        collection: String,
        document: String,
        dataMap: Map<String, Any>,
    ) {
        viewModelScope.launch {
            try {
                val result = appRepository.saveData(collection, document, dataMap)
                _saveDataStatus.value = Result.success(result)
            } catch (exception: Exception) {
                _saveDataStatus.value = Result.failure(exception)
            }
        }
    }

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