package com.imasha.hydrateme.presentation.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imasha.hydrateme.data.model.Notification
import com.imasha.hydrateme.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> get() = _userId

    private val _getNotificationStatus = MutableLiveData<Result<List<Notification>>>()
    val getNotificationStatus: LiveData<Result<List<Notification>>> = _getNotificationStatus

    fun getUserId() {
        _userId.value = appRepository.getCurrentUserId()
    }

    fun getNotifications() {
        viewModelScope.launch {
            try {
                val result = appRepository.getNotifications()
                _getNotificationStatus.value = Result.success(result)
            } catch (exception: Exception) {
                _getNotificationStatus.value = Result.failure(exception)
            }
        }
    }
}