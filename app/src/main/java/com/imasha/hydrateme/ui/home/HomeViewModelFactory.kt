package com.imasha.hydrateme.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imasha.hydrateme.data.repository.AppRepositoryImpl
import com.imasha.hydrateme.domain.usecase.*

class HomeViewModelFactory(
    private val userIdUseCase: UserIdUseCase,
    private val fcmTokenUseCase: FcmTokenUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val saveDrinkUseCase: SaveDrinkUseCase,
    private val todayRecordsUseCase: TodayRecordsUseCase,
    private val deleteRecordUseCase: DeleteRecordUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getAdvicesUseCase: GetAdvicesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                userIdUseCase,
                fcmTokenUseCase,
                getProfileUseCase,
                saveDrinkUseCase,
                todayRecordsUseCase,
                deleteRecordUseCase,
                logoutUseCase,
                getAdvicesUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}