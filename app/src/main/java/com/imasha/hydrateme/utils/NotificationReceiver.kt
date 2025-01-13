package com.imasha.hydrateme.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.imasha.hydrateme.ui.splash.SplashActivity

class NotificationReceiver : BroadcastReceiver() {

    companion object {
        var onNotificationClicked: (() -> Unit)? = null
    }

    override fun onReceive(context: Context, intent: Intent) {
        onNotificationClicked?.invoke()


        val homeIntent = Intent(context, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(homeIntent)
    }
}
