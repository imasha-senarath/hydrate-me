package com.imasha.hydrateme.presentation.signup

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
class SignUpViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    private val _signUpStatus = MutableLiveData<Result<Boolean>>()
    val signUpStatus: LiveData<Result<Boolean>> get() = _signUpStatus

    private val _saveProfileStatus = MutableLiveData<Result<Boolean>>()
    val saveProfileStatus: LiveData<Result<Boolean>> get() = _saveProfileStatus

    fun signUp(user: User) {
        viewModelScope.launch {
            try {
                val result = appRepository.signUp(user)
                _signUpStatus.value = Result.success(result)
            } catch (exception: Exception) {
                _signUpStatus.value = Result.failure(exception)
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