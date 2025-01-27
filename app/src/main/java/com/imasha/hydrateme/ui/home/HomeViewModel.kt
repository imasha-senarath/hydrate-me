package com.imasha.hydrateme.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.imasha.hydrateme.data.model.Cup
import com.imasha.hydrateme.data.model.Record
import com.imasha.hydrateme.data.model.User
import com.imasha.hydrateme.data.repository.AppRepository
import com.imasha.hydrateme.notify.NotificationWorker
import com.imasha.hydrateme.utils.AppConstants.IS_INIT_REMINDER
import com.imasha.hydrateme.utils.AppConstants.REMINDER_NOTIFY
import com.imasha.hydrateme.utils.SharedPrefManager.getPrefBoolean
import com.imasha.hydrateme.utils.SharedPrefManager.savePrefBoolean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> get() = _userId

    private val _getFcmTokenStatus = MutableLiveData<Result<String>>()
    val getFcmTokenStatus: LiveData<Result<String>> = _getFcmTokenStatus

    private val _getProfileStatus = MutableLiveData<Result<User>>()
    val getProfileStatus: LiveData<Result<User>> = _getProfileStatus

    private val _cupSize = MutableLiveData<List<Cup>>()
    val cupSize: LiveData<List<Cup>> get() = _cupSize

    private val _addDrinkStatus = MutableLiveData<Result<Boolean>>()
    val addDrinkStatus: LiveData<Result<Boolean>> get() = _addDrinkStatus

    private val _getRecordStatus = MutableLiveData<Result<List<Record>>>()
    val getRecordStatus: LiveData<Result<List<Record>>> = _getRecordStatus

    private val _deleteRecordStatus = MutableLiveData<Result<Boolean>>()
    val deleteRecordStatus: LiveData<Result<Boolean>> get() = _deleteRecordStatus

    private val _logoutStatus = MutableLiveData<Result<Boolean>>()
    val logoutStatus: LiveData<Result<Boolean>> get() = _logoutStatus

    private val _getAdvicesStatus = MutableLiveData<Result<List<String>>>()
    val getAdvicesStatus: LiveData<Result<List<String>>> = _getAdvicesStatus

    fun getUserId() {
        _userId.value = appRepository.getCurrentUserId()
    }

    fun getFcmToken() {
        viewModelScope.launch {
            try {
                val token = appRepository.getFcmToken()
                _getFcmTokenStatus.value = Result.success(token)
            } catch (exception: Exception) {
                _getFcmTokenStatus.value = Result.failure(exception)
            }
        }
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

    fun initCupSizes() {
        _cupSize.value = listOf(
            Cup(50),
            Cup(150),
            Cup(200),
            Cup(250),
            Cup(300),
            Cup(350),
        )
    }

    fun addRecord(dataMap: Map<String, Any>) {
        viewModelScope.launch {
            try {
                val result = appRepository.saveDrink(dataMap)
                _addDrinkStatus.value = Result.success(result)
            } catch (exception: Exception) {
                _addDrinkStatus.value = Result.failure(exception)
            }
        }
    }

    fun getTodayRecords() {
        viewModelScope.launch {
            try {
                val result = appRepository.getTodayRecords()
                _getRecordStatus.value = Result.success(result)
            } catch (exception: Exception) {
                _getRecordStatus.value = Result.failure(exception)
            }
        }
    }

    fun deleteRecord(id: String) {
        viewModelScope.launch {
            try {
                val result = appRepository.deleteRecord(id)
                _deleteRecordStatus.value = Result.success(result)
            } catch (exception: Exception) {
                _deleteRecordStatus.value = Result.failure(exception)
            }
        }
    }

    fun scheduleNotification(context: Context) {
        if(!getPrefBoolean(IS_INIT_REMINDER)) {

            val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(30, TimeUnit.MINUTES)
                .addTag(REMINDER_NOTIFY)
                .build()

            WorkManager.getInstance(context).enqueue(workRequest)

            savePrefBoolean(IS_INIT_REMINDER, true)
        }
    }

    fun cancelNotification(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(REMINDER_NOTIFY)
        savePrefBoolean(IS_INIT_REMINDER, false)
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

    fun getAdvices() {
        viewModelScope.launch {
            try {
                val result = appRepository.getAdvices()
                _getAdvicesStatus.value = Result.success(result)
            } catch (exception: Exception) {
                _getAdvicesStatus.value = Result.failure(exception)
            }
        }
    }
}