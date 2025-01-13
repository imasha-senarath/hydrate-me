package com.imasha.hydrateme.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.imasha.hydrateme.R

object NotificationUtils {

    private const val CHANNEL_ID = "default_channel"
    private const val CHANNEL_NAME = "Default Channel"
    private const val CHANNEL_DESCRIPTION = "Channel for app notifications"

    fun showNotification(context: Context, title: String, message: String, onNotificationClicked: () -> Unit ) {
        createNotificationChannel(context)

        val broadcastIntent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("callback", true) // Passing callback signal to receiver
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            broadcastIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.app_logo)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(1, notification)

        NotificationReceiver.onNotificationClicked = onNotificationClicked
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESCRIPTION
            }

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}
