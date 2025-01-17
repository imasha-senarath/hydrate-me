package com.imasha.hydrateme.firebase

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.imasha.hydrateme.notify.NotificationUtils.showNotification
import com.imasha.hydrateme.utils.AppConstants.FCM_TOKEN
import com.imasha.hydrateme.utils.SharedPrefManager.savePrefString

class MessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val title = remoteMessage.notification?.title ?: "Notification"
        val message = remoteMessage.notification?.body ?: "Message content"

        showNotification(this, title, message)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        savePrefString(FCM_TOKEN, token)
    }
}
