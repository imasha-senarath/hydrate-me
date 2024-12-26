package com.imasha.hydrateme.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imasha.hydrateme.data.repository.AppRepository

class SplashViewModelFactory(private val appRepository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(appRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}