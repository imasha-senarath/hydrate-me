package com.imasha.hydrateme.ui.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imasha.hydrateme.domain.usecase.SaveAppEntryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val saveAppEntryUseCase: SaveAppEntryUseCase
) : ViewModel() {

    fun saveAppEntry() {
        viewModelScope.launch {
            saveAppEntryUseCase()
        }
    }
}