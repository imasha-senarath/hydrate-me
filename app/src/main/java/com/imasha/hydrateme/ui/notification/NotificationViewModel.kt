package com.imasha.hydrateme.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imasha.hydrateme.data.model.Notification
import com.imasha.hydrateme.domain.usecase.GetNotificationsUseCase
import com.imasha.hydrateme.domain.usecase.UserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val userIdUseCase: UserIdUseCase,
    private val getNotificationsUseCase: GetNotificationsUseCase
) : ViewModel() {

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> get() = _userId

    private val _getNotificationStatus = MutableLiveData<Result<List<Notification>>>()
    val getNotificationStatus: LiveData<Result<List<Notification>>> = _getNotificationStatus

    fun getUserId() {
        _userId.value = userIdUseCase.getCurrentUserId()
    }

    fun getNotifications() {
        viewModelScope.launch {
            try {
                val result = getNotificationsUseCase.getNotifications()
                _getNotificationStatus.value = Result.success(result)
            } catch (exception: Exception) {
                _getNotificationStatus.value = Result.failure(exception)
            }
        }
    }
}