package com.imasha.hydrateme.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.data.repository.AppRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val appRepository: AppRepository) : ViewModel() {

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> get() = _userId

    private val _getProfileStatus = MutableLiveData<Result<User>>()
    val getProfileStatus: LiveData<Result<User>> = _getProfileStatus

    private val _addDrinkStatus = MutableLiveData<Result<Boolean>>()
    val addDrinkStatus: LiveData<Result<Boolean>> get() = _addDrinkStatus

    fun getUserId() {
        _userId.value = appRepository.getCurrentUserId()
    }

    fun getProfile() {
        viewModelScope.launch {
            try {
                val user = appRepository.getProfile()
                _getProfileStatus.value = Result.success(user)
            } catch (exception: Exception) {
                _getProfileStatus.value = Result.failure(exception)
            }
        }
    }

    fun saveProfile(dataMap: Map<String, Any>) {
        viewModelScope.launch {
            try {
                val result = appRepository.saveDrink(dataMap)
                _addDrinkStatus.value = Result.success(result)
            } catch (exception: Exception) {
                _addDrinkStatus.value = Result.failure(exception)
            }
        }
    }
}