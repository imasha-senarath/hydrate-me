package com.imasha.hydrateme.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imasha.hydrateme.data.repository.AppRepositoryImpl

class HomeViewModelFactory(private val appRepository: AppRepositoryImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(appRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}