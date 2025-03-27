package com.imasha.hydrateme.data.remote.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.imasha.hydrateme.data.model.Notification
import com.imasha.hydrateme.data.repository.AppRepositoryImpl
import com.imasha.hydrateme.notify.NotificationUtils.showNotification
import com.imasha.hydrateme.utils.AppConstants.FCM_TOKEN
import com.imasha.hydrateme.utils.AppLogger
import com.imasha.hydrateme.utils.DateUtils.DD_MM_YYYY
import com.imasha.hydrateme.utils.DateUtils.HH_MM
import com.imasha.hydrateme.utils.DateUtils.getCurrentDate
import com.imasha.hydrateme.utils.DateUtils.getCurrentTime
import com.imasha.hydrateme.utils.SharedPrefManager.savePrefString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MessagingService : FirebaseMessagingService() {

    private val className = this::class.java.simpleName

    @Inject
    lateinit var appRepository: AppRepositoryImpl

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage.notification?.title ?: "Notification"
        val message = remoteMessage.notification?.body ?: "Message content"
        val messageId = remoteMessage.messageId ?: UUID.randomUUID().toString()

        showNotification(this, title, message)

        saveNotification(
            Notification(
                id = messageId,
                title = title,
                message = message,
                time = getCurrentTime(HH_MM),
                date = getCurrentDate(DD_MM_YYYY),
                user = appRepository.getCurrentUserId().orEmpty()
            )
        )
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        savePrefString(FCM_TOKEN, token)
    }

    private fun saveNotification(notification: Notification) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val isSaved = appRepository.saveNotification(notification)
                if (!isSaved) {
                    AppLogger.d(className, "Failed to save notification")
                }
            } catch (e: Exception) {
                AppLogger.d(className, "Error saving notification: ${e.message}")
            }
        }
    }
}
