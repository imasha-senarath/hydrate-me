package com.imasha.hydrateme.notify

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.imasha.hydrateme.notify.NotificationUtils.showNotification
import com.imasha.hydrateme.utils.AppConstants.IS_INIT_REMINDER
import com.imasha.hydrateme.utils.SharedPrefManager.getPrefBoolean

class NotificationWorker(
    context: Context, workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {

        if(!getPrefBoolean(IS_INIT_REMINDER)) {
            return Result.success()
        }

        showNotification(
            applicationContext, "Time to Hydrate", "It's time to refresh! Grab a glass of water."
        )

        return Result.success()
    }
}
