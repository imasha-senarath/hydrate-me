package com.imasha.hydrateme.utils

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(
    context: Context, workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {

        NotificationUtils.showNotification(
            applicationContext, "Time to Hydrate", "It's time to refresh! Grab a glass of water."
        )

        return Result.success()
    }
}
