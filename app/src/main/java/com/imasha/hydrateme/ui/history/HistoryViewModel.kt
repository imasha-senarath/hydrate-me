package com.imasha.hydrateme.ui.history

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imasha.hydrateme.data.model.Record
import com.imasha.hydrateme.data.repository.AppRepository
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class HistoryViewModel(private val appRepository: AppRepository) : ViewModel() {

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> get() = _userId

    private val _getRecordStatus = MutableLiveData<Result<List<Record>>>()
    val getRecordStatus: LiveData<Result<List<Record>>> = _getRecordStatus

    fun getUserId() {
        _userId.value = appRepository.getCurrentUserId()
    }

    fun getRecords() {
        viewModelScope.launch {
            try {
                val result = appRepository.getRecords()
                _getRecordStatus.value = Result.success(result)
            } catch (exception: Exception) {
                _getRecordStatus.value = Result.failure(exception)
            }
        }
    }
}