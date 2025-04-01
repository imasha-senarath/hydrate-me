package com.imasha.hydrateme.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imasha.hydrateme.data.model.Record
import com.imasha.hydrateme.domain.usecase.GetRecordsUseCase
import com.imasha.hydrateme.domain.usecase.UserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val userIdUseCase: UserIdUseCase,
    private val getRecordsUseCase: GetRecordsUseCase
) : ViewModel() {

    private val _userId = MutableLiveData<String>()
    val userId: LiveData<String> get() = _userId

    private val _getRecordStatus = MutableLiveData<Result<List<Record>>>()
    val getRecordStatus: LiveData<Result<List<Record>>> = _getRecordStatus

    fun getUserId() {
        _userId.value = userIdUseCase.getCurrentUserId()
    }

    fun getRecords() {
        viewModelScope.launch {
            try {
                val result = getRecordsUseCase.getRecords()
                _getRecordStatus.value = Result.success(result)
            } catch (exception: Exception) {
                _getRecordStatus.value = Result.failure(exception)
            }
        }
    }
}