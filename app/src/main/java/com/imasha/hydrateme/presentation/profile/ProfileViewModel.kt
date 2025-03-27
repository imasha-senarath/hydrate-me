package com.imasha.hydrateme.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.data.repository.AppRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val appRepository: AppRepositoryImpl) : ViewModel() {

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> get() = _userId

    private val _getProfileStatus = MutableLiveData<Result<User>>()
    val getProfileStatus: LiveData<Result<User>> = _getProfileStatus

    private val _saveProfileStatus = MutableLiveData<Result<Boolean>>()
    val saveProfileStatus: LiveData<Result<Boolean>> get() = _saveProfileStatus

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

    fun saveProfile(user: User) {
        viewModelScope.launch {
            try {
                val result = appRepository.saveProfile(user)
                _saveProfileStatus.value = Result.success(result)
            } catch (exception: Exception) {
                _saveProfileStatus.value = Result.failure(exception)
            }
        }
    }
}